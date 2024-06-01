import { Component } from '@angular/core';
import { ReservationsService } from '../reservations.service';
import { PlacesComponent } from '../../places/places.component';
import { ActivatedRoute } from '@angular/router';
import { UsersService } from '../../users/users.service';
import { OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { EventsService } from '../../events/events.service';
import { EventsComponent } from '../../events/events.component';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-meeting-room-reservation',
  templateUrl: './meeting-room-reservation.component.html',
  styleUrl: './meeting-room-reservation.component.css'
})
export class MeetingRoomReservationComponent implements OnInit{
  imgId: string = '';
  date: string = '';
  roomId: string = '';
  eventName: string = '';
  reservationStartHour: string = '';
  reservationEndHour: string = '';
  suggestedPlace: PlacesComponent | null = null;
  loggedInUserName: string; 
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  events: EventsComponent[] = [];
  somethingWrong = false;
  hours: number;
  minutes: number;


  constructor(private reservationService: ReservationsService,
              private route: ActivatedRoute,
              private userService: UsersService, private themeService: ThemeService,
              private router: Router,
              private eventsService: EventsService 
  ) { 
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
    this.isDarkMode = isDark;
    });
  }

  ngOnInit(): void {
    this.eventsService.getEvents().subscribe(events => {
      this.events = events;

    });
    this.userService.username$.subscribe(username => {
      this.loggedInUserName = username;
    });
    this.route.params.subscribe(params => {
      this.imgId = params['imgId']; 
      this.date = params['date'];
      this.roomId = params['roomId'];
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

  createReservation(reservationForm: NgForm) {
    this.reservationService.createMeetingRoomReservation(this.imgId, this.date, this.roomId, this.loggedInUserName, reservationForm.value.eventName, reservationForm.value.startHour, reservationForm.value.endHour, false).subscribe(
      (place: PlacesComponent) => {
        console.log(place);
        if (place.placeNameId !== this.roomId) {
          this.suggestedPlace = place;
          console.log(this.suggestedPlace.placeNameId);
          if (confirm(`The room you requested is not available. Would you like to book an alternative room: ${this.suggestedPlace.placeNameId} in the same interval?`)) {
            // Update the request with the new roomId and retry the reservation
            this.roomId = place.placeNameId;
            this.finalizeReservation(reservationForm);
          }
          else{
            alert('Reservation not created!');
            this.router.navigate(['/maps']);
          }
        } else {
          this.finalizeReservation(reservationForm);
        }
      },
      (error) => {
        this.somethingWrong = true;
        console.error('Error creating reservation:', error);
        alert('Error creating reservation.');
      }
    );
  }

  finalizeReservation(reservationForm: NgForm) {
    this.reservationService.createMeetingRoomReservation(this.imgId, this.date, this.roomId, this.loggedInUserName, reservationForm.value.eventName, reservationForm.value.startHour, reservationForm.value.endHour, true).subscribe(
      (place: PlacesComponent) => {
        alert(`Reservation created successfully for ${place.placeNameId}!`);
        this.router.navigate(['/my/reservations']);
      },
      (error) => {
        this.somethingWrong = true;
        console.error('Error creating reservation:', error);
        alert('Error creating reservation.');
      }
    );
  }

  checkForm(reservationForm: NgForm): boolean {
    const startHour = reservationForm.value.startHour;
    const endHour = reservationForm.value.endHour;
    return !(reservationForm.valid && startHour < endHour && this.somethingWrong === false);
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
