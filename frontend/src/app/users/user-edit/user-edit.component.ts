import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent{
  user: UsersComponent;
  @ViewChild('userForm') userForm: NgForm;
  subscription: Subscription;

  constructor(private usersService: UsersService, private route: ActivatedRoute, private router: Router) { 
    this.user = new UsersComponent();
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

}
