import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TeamsService } from '../../teams/teams.service';
import { TeamsComponent } from '../../teams/teams.component';
import { ThemeService } from '../../theme-toggle/theme.service';
import { MatDialog } from '@angular/material/dialog';
import { UserDetailPopupComponent } from '../user-detail-popup/user-detail-popup.component';
import { AxiosService } from '../../axios.service';
import { DepartmentsService } from '../../departments/departments.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent implements OnInit, OnDestroy{
  user: UsersComponent;
  users: UsersComponent[] = [];
  @ViewChild('userForm') userForm: NgForm;
  subscription: Subscription;
  userRoles: string[] = [];
  teams: TeamsComponent[] = [];
  teamsArray: TeamsComponent[] = [];
  userPassword = '';
  confirmPassword = '';
  passwordVisible = false;
  confirmPasswordVisible: boolean = false;
  passwordsMatch: boolean = false;
  passwordStrength: boolean = false;
  passwordStrength1: boolean = false;
  passwordStrength2: boolean = false;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  letter: string;
  token: string;
  data: string[] = [];
  departments: any[] = [];


  constructor(private userService: UsersService, 
              private route: ActivatedRoute, 
              private router: Router,
              private teamsService: TeamsService, private themeService: ThemeService,
              private dialog: MatDialog,
              private axiosService: AxiosService,
              private departmentsService: DepartmentsService) { 
    this.user = new UsersComponent();
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit(): void {
    this.teamsService.getTeamsIds().subscribe(teams => {
      this.teams = teams;
      for (var i = 1; i < this.teams.length; i++) {
        this.teamsArray.push(this.teams[i]);
      }
    });
    this.departmentsService.getDepartmentsIds().subscribe(departments => {
      this.departments = departments;
    });
  }

  onSubmit(userForm: NgForm) {
    const value = userForm.value; 
    console.log('value', value.teamId.teamId);
    this.axiosService.request(
      "POST",
      "/users/create",
      {
        userName: value.userName, 
        userFirstName: value.userFirstName, 
        teamId: value.teamId.teamId,
        userEmail: value.userEmail, 
        userPassword: value.userPassword, 
      }).then(
      response => {
          this.axiosService.setAuthToken(response.data.token);
          const userId = value.userId; 
          this.gotoUserList(userId);
      }).catch(
      error => {
          console.error('Error creating user:', error);
      }
  );
  }  

  gotoUserList(userId: string) {
    this.userService.getUsers().subscribe(users => {
      this.users = users;

      let maxNumericValue = 0;

      this.letter = this.users[0].userId.charAt(0);

      for (let i = 0; i < this.users.length; i++) {
        const userId = this.users[i].userId;

        const numericValue = parseInt(userId.substring(1));

        if (!isNaN(numericValue) && numericValue > maxNumericValue) {
          maxNumericValue = numericValue;
        }
      }

      const lastUserId = this.letter + maxNumericValue;

      // console.log(lastUserId);
      // console.log('userId', userId);

      this.showUserDetailPopup(lastUserId);
    });
    this.router.navigate(['/login']);
  }

  showUserDetailPopup(userId: string) {
    this.dialog.open(UserDetailPopupComponent, {
      data: { userId: userId }
    });
  }
  

  ngOnDestroy() {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
  }

  toggleDarkTheme(): void {
    // console.log('isDarkMode', this.isDarkMode);
    this.isDarkMode = !this.isDarkMode;
    this.themeService.setDarkMode(this.isDarkMode);
  }

  
  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

  toggleConfirmPasswordVisibility(): void {
    this.confirmPasswordVisible = !this.confirmPasswordVisible;
  }

  validatePasswords(): void {
    if (this.userPassword === '' || this.confirmPassword === '') {
      this.passwordsMatch = false;
      return;
    }
    this.passwordsMatch = this.userPassword === this.confirmPassword;
  }

  patternValidator1(password1: string): void {
    const regex = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*()_+\\-=\\[\\]{};:\'"<>,./?]).{6,}$');   
    this.passwordStrength1 = regex.test(password1);
  }
}
