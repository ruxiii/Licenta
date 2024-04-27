import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ThemeService } from '../theme-toggle/theme.service';
import { Router } from '@angular/router'; // Import Router
import { LoginService } from '../login/login.service';
import { UsersService } from '../users/users.service'; // Import UsersService

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnDestroy, OnInit {
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  loggedInUserName: string; 
  showSubMenu: boolean = false;
  userRole: string; 

  constructor(
    private themeService: ThemeService,
    private userService: UsersService 
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit(): void {
    this.userService.username$.subscribe(username => {
      this.loggedInUserName = username;
    });
    this.userService.userRole$.subscribe(userRole => {
      this.userRole = userRole;
    });
  }

  ngOnDestroy() {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
  }

  toggleDarkTheme(): void {
    this.isDarkMode = !this.isDarkMode;
    this.themeService.setDarkMode(this.isDarkMode);
  }

  toggleSubMenu() {
    this.showSubMenu = !this.showSubMenu; 
  }

  onLogout(event: any) {
    this.userService.logout();
  }
}
