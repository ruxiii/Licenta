import { Component } from '@angular/core';
import { PlacesComponent } from '../places.component';
import { PlacesService } from '../places.service';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';

@Component({
  selector: 'app-place-list',
  templateUrl: './place-list.component.html',
  styleUrls: ['./place-list.component.css']
})
export class PlaceListComponent {
  places: PlacesComponent[];
  POSTS: any;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: number[] = [5, 10, 15, 20];

  constructor(private placesService: PlacesService, private themeService: ThemeService) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit() {
    this.placesService.getPlaces().subscribe(data => {
      this.places = data;
    });
    this.postList();
  }

  postList(): void {
    this.placesService.getPlaces().subscribe(response => {
      this.POSTS = response;
      // console.log(this.POSTS);
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
}
