import { Component, OnDestroy } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnDestroy {
  users: UsersComponent[];
  isDarkMode: boolean;
  private themeSubscription: Subscription;

  constructor(private usersService: UsersService, private router: Router, private themeService: ThemeService) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit() {
    this.usersService.getUsers().subscribe(data => {
      this.users = data;
    });
  }

  onNewUser() {
    this.router.navigate(['/users/create']);
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
}
