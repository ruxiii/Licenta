import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ThemeService } from '../theme-toggle/theme.service';
import { AuthenticationService } from '../authentication.service'; // Import AuthenticationService

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnDestroy {
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  loggedInUserName: string; // Add property for logged-in user's name
  showSubMenu: boolean = false;

  constructor(
    private themeService: ThemeService,
    private authService: AuthenticationService // Inject AuthenticationService
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });

    // Subscribe to changes in logged-in user's name
    this.authService.getUserName().subscribe(userName => {
      this.loggedInUserName = userName;
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

  onLogout() {
    this.authService.logout(); 
  }
}
