import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { MapsService } from '../maps.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { ConfirmationDialogSeatReservedComponent } from '../../confirmation-dialog-seat-reserved/confirmation-dialog-seat-reserved.component';
import { Output, EventEmitter } from '@angular/core';
import { ReservationsService } from '../../reservations/reservations.service';
import { UsersService } from '../../users/users.service';

@Component({
  selector: 'app-map-for-reservation',
  templateUrl: './map-for-reservation.component.html',
  styleUrl: './map-for-reservation.component.css'
})
export class MapForReservationComponent implements OnInit{
  imgId: string;
  imageUrl: string;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  date: string;
  highlightedSeat: string;
  unavailableSeats: string[] =[];
  hour: string;
  reservationsDone: string[][];
  hours: string[];
  hourToBeShown: string;
  timeSlots: string[][];
  hourToBeShown2: string;
  reservations: any;
  loggedInUserName: string;

  constructor(
              private route: ActivatedRoute,
              private mapsService: MapsService,
              private router: Router,
              public dialog: MatDialog,
              public reservationService: ReservationsService,
              private userService: UsersService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.imgId = params['id']; 
      this.date = params['date'];
      this.hour = params['hour'];
    });
    this.fetchImage();
    this.reservationService.getReservations().subscribe(data => {
      this.reservations = data;
    });
    this.userService.username$.subscribe(username => {
      this.loggedInUserName = username;
    });
  }

  fetchImage() {
    console.log(this.imgId);
    console.log(this.date);
    this.mapsService.getMapById(this.imgId, this.date, this.hour).subscribe(
      res => {
        const mapEntityKey = Object.values(res)[0];

        this.retrieveResonse = mapEntityKey;
        this.base64Data = this.retrieveResonse.mapImage;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;

        const reservationsAvailable = Object.values(res)[1];

        const reservationsDone = Object.values(res)[2];
        this.reservationsDone = reservationsDone as string[][];

        const reservationsAvailableArray = reservationsAvailable as string[];

        this.SeatsUnavailable(reservationsAvailableArray);
      }
    );
  }

  SeatsUnavailable(reservationsAvailableArray: string[]): void {
    for (let i = 1; i < 111; i++){
      const seatId = 'D' + i + '-6S';
      if (!reservationsAvailableArray.includes(seatId)) {
        console.log(seatId);
        this.unavailableSeats.push(seatId);
      }
    }

    for (let i = 1; i < 131; i++){
      const seatId = 'D' + i + '-6N';
      if (!reservationsAvailableArray.includes(seatId)) {
        console.log(seatId);
        this.unavailableSeats.push(seatId);
      }
    }

    for (let i = 1; i < 57; i++){
      const seatId = 'D' + i + '-5N';
      if (!reservationsAvailableArray.includes(seatId)) {
        console.log(seatId);
        this.unavailableSeats.push(seatId);
      }
    }

    for (let i = 1; i < 101; i++){
      const seatId = 'P' + i + '-PS2';
      if (!reservationsAvailableArray.includes(seatId)) {
        console.log(seatId);
        this.unavailableSeats.push(seatId);
      }
    }
    }

  seatFunction(seatId: string): boolean {
    return this.unavailableSeats.includes(seatId);
  }


  onSeatClick(seatId: string) {
    this.highlightedSeat = seatId;

    this.hours = [];
    this.timeSlots =  [];
    for (let i =0; i< this.reservationsDone.length; i++){
      if(this.reservationsDone[i][0] === seatId){
        this.hours.push(this.reservationsDone[i][1]);
        this.timeSlots.push([this.reservationsDone[i][1], this.reservationsDone[i][2]]);
      }
    }

    console.log(this.timeSlots);

    this.hours = this.hours.sort((a, b) => a.localeCompare(b)); 
    this.hourToBeShown = '';
    console.log("hourToBeShown before loop", this.hourToBeShown);

    for (let i = 0; i < this.hours.length; i++) {
      console.log("current hour", this.hours[i]);
      if (this.hour < this.hours[i]) {
        this.hourToBeShown = this.hours[i];
        break; 
      }
    }

    this.setHourToBeShown();
    console.log("hourToBeShown after loop", this.hourToBeShown);

    if(this.hours !== undefined && this.hours.length > 0 && this.hourToBeShown !== ''){
      for (let i = 0; i < this.timeSlots.length; i++){
        if(this.timeSlots[i][0] === this.hourToBeShown){
          this.hourToBeShown2 = this.timeSlots[i][1];
          break;
        }
      }

      const dialogRef = this.dialog.open(ConfirmationDialogSeatReservedComponent, {
        width: '250px',
        data: { date1: this.hourToBeShown, date2: this.hourToBeShown2}
      });
    
      dialogRef.afterClosed().subscribe(result => {
        if (result === 'yes') {
          this.router.navigate(['/' + this.imgId + '/' + this.date + '/reservation/' + seatId]);      
        }
        else {
          this.highlightedSeat = null;
        }
      });
    }

    else{
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        width: '250px',
        data: { date: seatId}
      });
    
      dialogRef.afterClosed().subscribe(result => {
        if (result === 'yes') {
          this.router.navigate(['/' + this.imgId + '/' + this.date + '/reservation/' + seatId]);      
        }
        else {
          this.highlightedSeat = null;
        }
      });
    }
  }

  isHighlighted(seatId: string): boolean {
    return this.highlightedSeat === seatId;
  }

  isReserved(seatId: string): boolean {
    return false;
  }

  setHourToBeShown() {
    this.mapsService.setHourToBeShown(this.hourToBeShown);
  }

  isUserReservation(seatId: string): boolean {
    return this.reservations.some(reservation => reservation.placeNameId === seatId && reservation.userId === this.loggedInUserName);
  }
}
