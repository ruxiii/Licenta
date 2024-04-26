import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../axios.service';
import { AuthenticationService } from '../authentication.service'; // Import AuthenticationService
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
  showPasswordMismatchWarning = false;

  constructor(private router: Router,
              private loginService: LoginService,
              private axiosService: AxiosService,
              private authService: AuthenticationService  
            ) { }

  ngOnInit() {
    window.localStorage.clear();
  }

  
onSubmitLogin(loginForm: NgForm) {
  const value = loginForm.value;
  console.log('Form Value:', value);

  this.loginService.verifyCredentials(value.username, value.password).subscribe(
    (response) => {
      this.authService.login(value.username, value.password);
      this.router.navigate(['/welcome']);
    },
    (error) => {
      console.log('Error:', error);
      if (error.status === 401) {
        this.showPasswordMismatchWarning = true;
      }
    }
  );
}

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
