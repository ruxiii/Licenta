import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  private userName = new BehaviorSubject<string>('');

  constructor(private router: Router) { }

  login(username: string, password: string) {
    this.userName.next(username);
    this.loggedIn.next(true);
  }

  logout() {
    this.userName.next('');
    this.loggedIn.next(false);
    window.localStorage.clear();
    window.alert('You have been logged out');
    this.router.navigate(['/home']);
  }

  isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  getUserName() {
    return this.userName.asObservable();
  }
}
