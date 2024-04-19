import { Component } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @Output() onSubmitLoginEvent = new EventEmitter();

  username: string = '';
  password: string = '';
  passwordVisible = false;

  constructor(private router: Router, 
              private loginService: LoginService
  ) { }

  onSubmitLogin(): void {
    console.log('Login: ' + this.username + ' Password: ' + this.password);
    this.onSubmitLoginEvent.emit({username: this.username, password: this.password});
    this.loginService.login(this.username, this.password).subscribe(
      data => {
        // Store JWT token securely (e.g., in local storage)
        localStorage.setItem('jwtToken', data.jwt);
        // Redirect or perform actions after successful login
        // For example:
        // this.router.navigate(['/dashboard']);
      },
      error => {
        console.error('Error:', error);
        // Handle authentication error (e.g., display error message)
      }
    );
    this.router.navigate(['/home']);
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
