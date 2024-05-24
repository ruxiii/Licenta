import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { MapsService } from '../maps.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';

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

  constructor(
              private route: ActivatedRoute,
              private mapsService: MapsService,
              private router: Router,
              public dialog: MatDialog,

  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.imgId = params['id']; 
      this.date = params['date'];
      this.hour = params['hour'];
    });
    this.fetchImage();
  }

  fetchImage() {
    console.log(this.imgId);
    console.log(this.date);
    this.mapsService.getMapById(this.imgId, this.date, this.hour).subscribe(
      res => {
        const mapEntityKey = Object.values(res)[0];
        // console.log(mapEntityKey);
        // const mapEntity = res[mapEntityKey];
        // console.log(mapEntity);
        // console.log(mapEntityKey[0]+mapEntityKey[1]+mapEntityKey[2]);

        this.retrieveResonse = mapEntityKey;
        this.base64Data = this.retrieveResonse.mapImage;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;

        const reservationsAvailable = Object.values(res)[1];

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

  isHighlighted(seatId: string): boolean {
    return this.highlightedSeat === seatId;
  }

  isReserved(seatId: string): boolean {
    return false;
  }
  
}
