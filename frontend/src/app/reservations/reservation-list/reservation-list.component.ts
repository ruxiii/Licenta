import { Component } from '@angular/core';
import { ReservationsService } from '../reservations.service';
import { ReservationsComponent } from '../reservations.component';  
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrl: './reservation-list.component.css'
})
export class ReservationListComponent {
  reservations: ReservationsComponent[];
  POSTS: any;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: number[] = [5, 10, 15, 20];


  constructor(private reservationsService: ReservationsService,
              private router: Router,
              private themeService: ThemeService,
              private axiosService: AxiosService) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });  
  }

  ngOnInit() {
    this.reservationsService.getReservations().subscribe(data => {
      this.reservations = data;
      console.log(this.reservations);

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

  myReservations() {
    this.router.navigate(['my/reservations']);
  }
}
