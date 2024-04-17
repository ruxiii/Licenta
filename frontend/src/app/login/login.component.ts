import { Component } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';

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

  onSubmitLogin(): void {
    this.onSubmitLoginEvent.emit({login: this.login, password: this.password});
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }

}
