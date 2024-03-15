import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { UsersService } from '../users.service';
import { UsersComponent } from '../users.component';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserRolesService } from '../user-roles.service';
import { TeamsService } from '../../teams/teams.service';
import { TeamsComponent } from '../../teams/teams.component';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent implements OnInit{
  user: UsersComponent;
  @ViewChild('userForm') userForm: NgForm;
  subscription: Subscription;
  userRoles: string[] = [];
  teams: TeamsComponent[] = [];
  teamsArray: string[] = [];
  

  constructor(private usersService: UsersService, 
              private route: ActivatedRoute, 
              private router: Router,
              private userRolesService: UserRolesService,
              private teamsService: TeamsService) { 
    this.user = new UsersComponent();
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

}