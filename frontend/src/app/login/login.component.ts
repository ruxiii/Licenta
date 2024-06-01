import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../axios.service';
import { UsersService } from '../users/users.service'; // Import UsersService

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
  somethingWrong = false;

  constructor(private router: Router,
              private axiosService: AxiosService,
              private userService: UsersService
            ) { }

  ngOnInit() {
    if (typeof window !== "undefined") {  
      window.localStorage.clear();
    }
  }

  
  onSubmitLogin(loginForm: NgForm) {
    const value = loginForm.value; 
    this.axiosService.request(
      "POST",
      "/login",
      {
        userId: value.username, 
        userPassword: value.password, 
      }
    ).then(
      response => {
        this.axiosService.setAuthToken(response.data.token);
        
        const username =value.username;
        this.userService.setUsername(username);   

        const userRole = response.data.authenticationEntity.authorities[0].authority;
        this.userService.setUserRole(userRole);

        this.router.navigate(['/welcome']);
      }
    ).catch(
      error => {
        if (error.response.status === 401) {
          this.somethingWrong = true;
        }
      }
    );
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}