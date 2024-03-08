import { Component } from '@angular/core';
import { MapsService } from '../maps.service';
import { MapsComponent } from '../maps.component';

@Component({
  selector: 'app-map-list',
  templateUrl: './map-list.component.html',
  styleUrl: './map-list.component.css'
})
export class MapListComponent {
  maps: MapsComponent[];

  constructor(private mapsService: MapsService) {
  }

  ngOnInit() {
    this.mapsService.getMaps().subscribe(data => {
      this.maps = data;
    });
  }
}
