import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../axios.service';
import { AuthenticationService } from '../authentication.service'; // Import AuthenticationService

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'] // Corrected styleUrl to styleUrls
})
export class LoginComponent implements OnInit {
  @Output() onSubmitLoginEvent = new EventEmitter();

  username: string = '';
  password: string = '';
  passwordVisible = false;

  constructor(private router: Router,
              private loginService: LoginService,
              private axiosService: AxiosService,
              private authService: AuthenticationService // Inject AuthenticationService
  ) { }

  ngOnInit() {
    window.localStorage.clear();
  }

  onSubmitLogin(loginForm: NgForm) {
    const value = loginForm.value;
    this.axiosService.request(
      "POST",
      "/login",
      {
        userId: value.username,
        userPassword: value.password,
      }).then(
      response => {
        console.log('Response:', response.data.token);
        this.axiosService.setAuthToken(response.data.token);
        this.authService.login(value.username); // Update AuthenticationService with logged in user's name
        this.router.navigate(['/home']);
      }).catch(
      error => {
        console.log('Error:', error, error.response.data.message);
      }
      );

  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
