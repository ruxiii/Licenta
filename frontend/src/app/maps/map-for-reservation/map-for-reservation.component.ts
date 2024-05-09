import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { MapsService } from '../maps.service';

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

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private mapsService: MapsService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.imgId = params['id']; 
    });
    this.fetchImage();
  }

  fetchImage() {
    this.mapsService.getMapById(this.imgId).subscribe(
      res => {
        this.retrieveResonse = res;
        this.base64Data = this.retrieveResonse.mapImage;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
      }
    );
  }

  onSeatClick(seatId: string) {
  }
}
