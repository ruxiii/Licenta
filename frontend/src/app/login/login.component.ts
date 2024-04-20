import { Component, OnInit } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../axios.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  @Output() onSubmitLoginEvent = new EventEmitter();

  username: string = '';
  password: string = '';
  passwordVisible = false;

  constructor(private router: Router, 
              private loginService: LoginService,
              private axiosService: AxiosService
  ) { }

  ngOnInit() {
    window.localStorage.clear();
  }

  onSubmitLogin(loginForm: NgForm) {
    const value = loginForm.value; 
    // this.onSubmitLoginEvent.emit({username: this.username, password: this.password});
    // this.loginService.login(this.username, this.password).subscribe(
    //   data => {
    //     // Store JWT token securely (e.g., in local storage)
    //     localStorage.setItem('jwtToken', data.jwt);
    //     // Redirect or perform actions after successful login
    //     // For example:
    //     // this.router.navigate(['/dashboard']);
    //   },
    //   error => {
    //     console.error('Error:', error);
    //     // Handle authentication error (e.g., display error message)
    //   }
    // );
    this.axiosService.setAuthToken(null);
    this.axiosService.request(
      "POST",
      "/login",
      {
        userId: value.username, 
        userPassword: value.password, 
      }).then(
      response => {
        console.log('value', value.username, value.password);
        console.log('response', response.data.token);
          this.axiosService.setAuthToken(response.data.token);
          // this.componentToShow = "messages";
          this.router.navigate(['/home']);
      }).catch(
      error => {
        console.log('Error:', error, error.response.data.message);
          this.axiosService.setAuthToken(null);
          // this.componentToShow = "welcome";
      }
  );
    
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
