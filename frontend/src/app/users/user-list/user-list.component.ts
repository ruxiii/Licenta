import { Component, OnDestroy } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnDestroy {
  users: UsersComponent[];
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  POSTS: any;
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: number[] = [5, 10, 15, 20];

  constructor(  private usersService: UsersService, 
                private router: Router, 
                private themeService: ThemeService,
                private axiosService: AxiosService) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit() {
    this.usersService.getUsers().subscribe(data => {
      this.users = data;
    });
    this.postList();
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
    // console.log('isDarkMode', this.isDarkMode);
    this.isDarkMode = !this.isDarkMode;
    this.themeService.setDarkMode(this.isDarkMode);
  }

  postList(): void {
    this.usersService.getUsers().subscribe(response => {
      this.POSTS = response;
      // console.log(this.POSTS);
    });
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.postList();
  }

  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.postList();
  }

  deleteUser(user: UsersComponent) {
    if (confirm('Are you sure you want to delete this user?')) {
      const deleteUrl = '/users/' + user.userId +'/delete'; // Adjust the URL as needed
      this.axiosService.request('DELETE', deleteUrl, null)
        .then(() => {
          this.users = this.users.filter(u => u !== user);
        })
        .catch(error => {
          console.error('Error deleting department:', error);
        });
    }
  }
}
