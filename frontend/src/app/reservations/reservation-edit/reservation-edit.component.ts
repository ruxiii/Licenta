import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { NgForm } from '@angular/forms';
import { UsersService } from '../../users/users.service';
import { EventsComponent } from '../../events/events.component';
import { EventsService } from '../../events/events.service';
import { ReservationsService } from '../reservations.service';
import { Router } from '@angular/router';

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
  events: EventsComponent[] = [];
  hours: number;
  minutes: number;
  
  constructor(private route: ActivatedRoute, 
              private themeService: ThemeService,
              private userService: UsersService,
              private eventsService: EventsService,
              private reservationsService: ReservationsService,
              private router: Router) { 
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
    this.isDarkMode = isDark;
    });
  }

  ngOnInit(): void {
    this.eventsService.getEvents().subscribe(events => {
      this.events = events;

    });
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
    console.log('imgId', this.imgId);
    console.log('data', this.date)
    console.log('seatId', this.seatId);
    console.log('username', this.loggedInUserName);

    const value = reservationForm.value; //dto
    console.log('start', value.startHour);
    console.log('end', value.endHour);
    console.log('event', value.eventName);

    this.reservationsService.createReservation(
      this.imgId, 
      this.date, 
      this.seatId, 
      this.loggedInUserName,
       value.eventName, 
       value.startHour, 
       value.endHour).subscribe(
        () => {
          this.router.navigate(['/reservations']);
        },
        error => {
          if (error.status === 403) {
          }
        }
      );

  }

  checkForm(reservationForm: NgForm): boolean {
    const startHour = reservationForm.value.startHour;
    const endHour = reservationForm.value.endHour;
    return !(reservationForm.valid && startHour < endHour);
  }

  checkTime(reservationForm: NgForm): [number, number] {
    const startTime = new Date("2000-01-01T" + reservationForm.value.startHour);
    const endTime = new Date("2000-01-01T" + reservationForm.value.endHour);
    const timeDiff = endTime.getTime() - startTime.getTime(); // Difference in milliseconds
    
    this.hours = Math.floor(timeDiff / (1000 * 60 * 60)); // Extract hours
    this.minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60)); // Extract remaining minutes
  
    return [this.hours, this.minutes];
  }
}
