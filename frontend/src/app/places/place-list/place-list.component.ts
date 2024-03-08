import { Component } from '@angular/core';
import { PlacesComponent } from '../places.component';
import { PlacesService } from '../places.service';

@Component({
  selector: 'app-place-list',
  templateUrl: './place-list.component.html',
  styleUrl: './place-list.component.css'
})
export class PlaceListComponent {
  places: PlacesComponent[];

  constructor(private placesService: PlacesService) {
  }

  ngOnInit() {
    this.placesService.getPlaces().subscribe(data => {
      this.places = data;
    });
  }

}
