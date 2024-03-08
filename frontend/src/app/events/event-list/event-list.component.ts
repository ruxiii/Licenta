import { Component } from '@angular/core';
import { EventsService } from '../events.service';
import { EventsComponent } from '../events.component';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
export class EventListComponent {
  events: EventsComponent[];

  constructor(private eventsService: EventsService) {
  }

  ngOnInit() {
    this.eventsService.getEvents().subscribe(data => {
      this.events = data;
    });
  }
}
