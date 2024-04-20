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
    this.axiosService.request(
      "POST",
      "/login",
      {
        userId: value.username, 
        userPassword: value.password, 
      }).then(
      response => {
          this.axiosService.setAuthToken(response.data.token);
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
