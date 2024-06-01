import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-edit',
  templateUrl: './event-edit.component.html',
  styleUrl: './event-edit.component.css'
})
export class EventEditComponent {
  isDarkMode: boolean;
  
  constructor(  private axiosService: AxiosService, 
                private router: Router) {}

  onSubmit(eventForm: NgForm) {
    const value = eventForm.value; 
    // console.log(value);
    this.axiosService.request(
    "POST",
    "/events/create",
    {
      eventName: value.eventName
    }
    ).then(() => {
    this.router.navigate(['/events']);
    }).catch(
      error => {
        console.log(error);
      }
    );
  }
}
