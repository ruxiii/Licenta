import { Component } from '@angular/core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent {
  userId: string;
  userName: string;
  userFirstName: string;
  userEmail: string;
  userPassword: string;
  userRole: string;
  teamId: string;
}
