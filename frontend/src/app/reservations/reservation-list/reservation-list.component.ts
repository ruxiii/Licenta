import { Component } from '@angular/core';
import { ReservationsService } from '../reservations.service';
import { ReservationsComponent } from '../reservations.component';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrl: './reservation-list.component.css'
})
export class ReservationListComponent {
  reservations: ReservationsComponent[];

  constructor(private reservationsService: ReservationsService) {
  }

  ngOnInit() {
    this.reservationsService.getReservations().subscribe(data => {
      this.reservations = data;
    });
  }
}
