import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsersComponent } from './users.component';
import { HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class UsersService {
  private createUserUrl : string;
  private getAllUsersUrl : string;
  tokenType = 'Bearer ';
  private usernameSubject = new BehaviorSubject<string>(null);
  private userRoleSubject = new BehaviorSubject<string>(null);
  username$ = this.usernameSubject.asObservable();
  userRole$ = this.userRoleSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) { 
    this.getAllUsersUrl = 'http://localhost:8080/users';
    this.createUserUrl = 'http://localhost:8080/users/create';
  }

  public getUsers(): Observable<UsersComponent[]> {
    const url = '/login';
    if (typeof window !== "undefined") {  
      const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
      const headers = { headers: header };
      return this.http.get<UsersComponent[]>(this.getAllUsersUrl, headers);
    }
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

  setUsername(username: string): void {
    this.usernameSubject.next(username);
  }

  getUsername(): string {
    return this.usernameSubject.value;
  }

  setUserRole(userRole: string): void {
    this.userRoleSubject.next(userRole);
  }

  getUserRole(): string {
    return this.userRoleSubject.value;
  }

  logout(): void {
    if (typeof window !== "undefined") {  
      window.localStorage.clear();
      this.setUsername(null); 
      this.router.navigate(['/home']);
    }
  }
}