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
  loggedInUserName: string; // Add property for logged-in user's name
  showSubMenu: boolean = false;

  constructor(
    private themeService: ThemeService,
    private userService: UsersService // Inject UsersService
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
