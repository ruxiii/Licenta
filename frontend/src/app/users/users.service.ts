import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsersComponent } from './users.component';
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class UsersService {
  private createUserUrl : string;
  private getAllUsersUrl : string;
  tokenType = 'Bearer ';

  constructor(private http: HttpClient) { 
    this.getAllUsersUrl = 'http://localhost:8080/users';
    this.createUserUrl = 'http://localhost:8080/users/create';
  }

  public getUsers(): Observable<UsersComponent[]> {
    const url = '/login';
    const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
    const headers = { headers: header };
    return this.http.get<UsersComponent[]>(this.getAllUsersUrl);
  }  

  createUser(userId: string, userName: string, userFirstName: string, userEmail: string, userPassword: string, userRole: string, teamId: string): Observable<any> {
    return this.http.post(this.createUserUrl, {
      userId,
      userName,
      userFirstName,
      userEmail,
      userPassword,
      userRole,
      teamId
    });
  }
}