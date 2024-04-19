import { Component } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @Output() onSubmitLoginEvent = new EventEmitter();

  login: string = '';
  password: string = '';
  passwordVisible = false;

  constructor(private router: Router) { }

  onSubmitLogin(): void {
    this.onSubmitLoginEvent.emit({login: this.login, password: this.password});
    this.router.navigate(['/home']);
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
