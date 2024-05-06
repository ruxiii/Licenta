import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { UsersComponent } from '../users.component';
import { TeamsComponent } from '../../teams/teams.component';
import { NgForm } from '@angular/forms';
import { UsersService } from '../users.service';
import { ActivatedRoute } from '@angular/router';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';
import { TeamsService } from '../../teams/teams.service';
import { DepartmentsService } from '../../departments/departments.service';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrl: './user-update.component.css'
})
export class UserUpdateComponent implements OnInit{
  user: UsersComponent;
  users: UsersComponent[] = [];
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
  userId: string;


  constructor(private userService: UsersService, 
              private route: ActivatedRoute, 
              private router: Router,
              private teamsService: TeamsService, 
              private themeService: ThemeService,
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
    this.route.params.subscribe(params => {
      this.userId = params['id']; 
    });
    this.loadUsers();
  }

  onSubmit(userForm: NgForm) {
    const value = userForm.value;

    if (confirm('Are you sure you want to update this user?')) {
      const updateUrl = `/users/${this.userId}/update`; 
      const requestData = {
        userId: this.userId,
        userName: value.userName, 
        userFirstName: value.userFirstName, 
        teamId: value.teamId.teamId,
        userEmail: value.userEmail, 
        userPassword: value.userPassword, 
        userRole: value.userRole
      };

      this.axiosService.request(
        'PUT', 
        updateUrl, 
        requestData  
      ).then(() => {
          this.users = this.users.map(u => {
            if (u.userId === value.userId) {
              return {...u, ...value}; 
            }
            return u;
          });
          this.router.navigate(['/users']);
        })
    }
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

  loadUsers() {
    this.userService.getUsers()
      .subscribe(users => {
        this.users = users;
      }, error => {
        console.error('Error fetching users:', error);
      });
    }
    validator(userForm: NgForm): boolean {
      return userForm.form.valid && this.passwordsMatch && this.passwordStrength1;
    }
}
