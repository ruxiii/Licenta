import { Component } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  users: UsersComponent[];

  constructor(private usersService: UsersService) {
  }

  ngOnInit() {
    this.usersService.getUsers().subscribe(data => {
      this.users = data;
    });
  }
}
