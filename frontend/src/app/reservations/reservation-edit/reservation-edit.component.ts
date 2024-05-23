import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { NgForm } from '@angular/forms';
import { UsersService } from '../../users/users.service';

@Component({
  selector: 'app-reservation-edit',
  templateUrl: './reservation-edit.component.html',
  styleUrl: './reservation-edit.component.css'
})
export class ReservationEditComponent {
  subscription: Subscription;
  imgId: string;
  date: string;
  seatId: string;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  loggedInUserName: string;
  
  constructor(private route: ActivatedRoute, 
              private themeService: ThemeService,
              private userService: UsersService) { 
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
    this.isDarkMode = isDark;
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.imgId = params['imgId']; 
      this.date = params['date'];
      this.seatId = params['seatId'];
    });  

    this.userService.username$.subscribe(username => {
      this.loggedInUserName = username;
    });

    
  }

  ngOnDestroy() {
    if (this.themeSubscription) {
      this.themeSubscription.unsubscribe();
    }
  }

  toggleDarkTheme(): void {
    // console.log('isDarkMode', this.isDarkMode);
    this.isDarkMode = !this.isDarkMode;
    this.themeService.setDarkMode(this.isDarkMode);
  }

  onSubmit(reservationForm: NgForm) {
    // logic to submit the form
  }
}
