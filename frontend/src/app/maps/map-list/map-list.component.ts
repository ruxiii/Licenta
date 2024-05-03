import { Component } from '@angular/core';
import { MapsService } from '../maps.service';
import { MapsComponent } from '../maps.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';

@Component({
  selector: 'app-map-list',
  templateUrl: './map-list.component.html',
  styleUrl: './map-list.component.css'
})
export class MapListComponent {
  maps: MapsComponent[];
  isDarkMode: boolean;
  private themeSubscription: Subscription;

  constructor(private mapsService: MapsService,
              private router: Router,
              private themeService: ThemeService
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit() {
    this.mapsService.getMaps().subscribe(data => {
      this.maps = data;
    });
  }

  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }

  onNewMap(){
    this.router.navigate(['/maps/create']);
  }
}
