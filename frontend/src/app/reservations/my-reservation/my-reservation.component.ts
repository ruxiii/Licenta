import { Component } from '@angular/core';
import { ReservationsComponent } from '../reservations.component';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { ReservationsService } from '../reservations.service';
import { UsersService } from '../../users/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-reservation',
  templateUrl: './my-reservation.component.html',
  styleUrl: './my-reservation.component.css'
})
export class MyReservationComponent {
  reservations: ReservationsComponent[];
  POSTS: any;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: number[] = [5, 10, 15, 20];
  loggedInUserName: string; 
  mapId: string;

  constructor(  private reservationsService: ReservationsService,
                private themeService: ThemeService,
                private userService: UsersService,
                private router: Router) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
    this.isDarkMode = isDark;
    });  
  }

  ngOnInit() {
    this.userService.username$.subscribe(username => {
      this.loggedInUserName = username;
    });
    this.reservationsService.getMyReservations(this.loggedInUserName).subscribe(data => {
      this.reservations = data;
    });
    this.postList();
  }

  postList(): void {
    this.reservationsService.getReservations().subscribe(response => {
      this.POSTS = response;
    });
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.postList();
  }

  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.postList();
  }

  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }

  onSeeMap(reservation: ReservationsComponent) {
    if(reservation.placeNameId[0] === 'P' ){
      this.mapId = "PS2";
    }
    else{
      this.mapId = reservation.placeNameId[reservation.placeNameId.length - 2] + reservation.placeNameId[reservation.placeNameId.length - 1];
    }
    this.router.navigate(['/maps/' + this.mapId + '/availabilities/' + reservation.reservationDate + '/' + reservation.reservationStartHour]);
  }

  isExpired(reservationDate: string, reservationEndHour: string): boolean {
    const currentDateTime = new Date();
    // console.log(currentDateTime);
  
    const [day, month, year] = reservationDate.split('-');
  
    const reservationEndDateTime = new Date(parseInt(year), parseInt(month) - 1, parseInt(day));
  
    const [hour, minute] = reservationEndHour.split(':');
    reservationEndDateTime.setHours(parseInt(hour));
    reservationEndDateTime.setMinutes(parseInt(minute));
    // console.log(reservationEndDateTime);

    if (currentDateTime > reservationEndDateTime) {
      return true;
    }
  }

  deleteReservation(reservation: ReservationsComponent) {
    this.reservationsService.deleteReservation(reservation.reservationId).subscribe(data => {
      this.reservations = this.reservations.filter(u => u !== reservation);
    });
  }
  
}
