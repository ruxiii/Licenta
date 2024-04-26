import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  private userName = new BehaviorSubject<string>('');

  constructor() { }

  login(username: string) {
    this.userName.next(username);
    this.loggedIn.next(true);
  }

  logout() {
    this.userName.next('');
    this.loggedIn.next(false);
    window.localStorage.clear();
    window.alert('You have been logged out');
  }

  isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  getUserName() {
    return this.userName.asObservable();
  }
}
