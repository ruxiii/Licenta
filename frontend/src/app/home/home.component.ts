import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  homeMessage: string;
  adminMessage: string;
  showHomeMessage: boolean = false;
  showAdminMessage: boolean = false;
  isLoggedIn: boolean = false;

  constructor(private homeService: HomeService,
              private router: Router,
              private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.homeService.home().subscribe(response => {
      this.homeMessage = response;
    });
    this.authService.isLoggedIn().subscribe((loggedIn: boolean) => {
      this.isLoggedIn = loggedIn;
    });
  }

  onNewUser() {
    this.router.navigate(['/users/create']);
  }

  onLogin() {
    this.router.navigate(['/login']);
  }

  onStart() {
    // this.router.navigate(['/start']);
  }
}
