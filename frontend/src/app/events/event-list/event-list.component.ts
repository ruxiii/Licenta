import { Component } from '@angular/core';
import { EventsService } from '../events.service';
import { EventsComponent } from '../events.component';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { UsersService } from '../../users/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
export class EventListComponent {
  events: EventsComponent[];
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  userRole: string;

  constructor(private eventsService: EventsService,
              private themeService: ThemeService,
              private userService: UsersService,
              private router: Router
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit() {
    this.eventsService.getEvents().subscribe(data => {
      this.events = data;
    });
    this.userService.userRole$.subscribe(userRole => {
      this.userRole = userRole;
    });
  }

  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }

  onNewEvent(){
    this.router.navigate(['/events/create']);
  }
}
