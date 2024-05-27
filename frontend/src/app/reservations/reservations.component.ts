import { Component } from '@angular/core';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent {
  reservationId: string;
  reservationDate: string;
  reservationStartHour: string;
  reservationEndHour: string;
  eventName: string;
  userId: string;
  placeNameId: string;
}
