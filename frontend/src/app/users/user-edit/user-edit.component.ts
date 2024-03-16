import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserRolesService } from '../user-roles.service';
import { TeamsService } from '../../teams/teams.service';
import { TeamsComponent } from '../../teams/teams.component';
import { ThemeService } from '../../theme-toggle/theme.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent implements OnInit, OnDestroy{
  user: UsersComponent;
  @ViewChild('userForm') userForm: NgForm;
  subscription: Subscription;
  userRoles: string[] = [];
  teams: TeamsComponent[] = [];
  teamsArray: string[] = [];
  userPassword = '';
  confirmPassword = '';
  passwordVisible = false;
  confirmPasswordVisible: boolean = false;
  passwordsMatch: boolean = true;
  isDarkMode: boolean;
  private themeSubscription: Subscription;

  constructor(private usersService: UsersService, 
              private route: ActivatedRoute, 
              private router: Router,
              private userRolesService: UserRolesService,
              private teamsService: TeamsService, private themeService: ThemeService) { 
    this.user = new UsersComponent();
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit(): void {
    this.userRolesService.getUserRoles().subscribe(userRoles => {
      this.userRoles = userRoles;
      console.log(this.userRoles);
    });

    this.teamsService.getTeamsIds().subscribe(teams => {
      this.teams = teams;
      for (var i = 0; i < this.teams.length; i++) {
        this.teamsArray.push(this.teams[i].teamId);
      }
      console.log(this.teamsArray);
    });
    
  
  }

  onSubmit(userForm: NgForm) {
    const value = userForm.value; 
    console.log(value);
    this.usersService.createUser(value.userId, value.userName, value.userFirstName, value.userEmail, value.userPassword, value.userRole, value.teamId)
    .subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  ngOnDestroy() {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
  }

  toggleDarkTheme(): void {
    console.log('isDarkMode', this.isDarkMode);
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
    this.passwordsMatch = this.userPassword === this.confirmPassword;
  }

}