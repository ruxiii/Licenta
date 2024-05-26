import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-clock',
  templateUrl: './clock.component.html',
  styleUrls: ['./clock.component.css']
})
export class ClockComponent {
  hours: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  minutes: number[] = Array.from({ length: 60 }, (_, i) => i);
  selectedHour: number | null = null;
  selectedMinute: number | null = null;
  selectedPeriod: string | null = null; // AM or PM
  hourHandStyle = {};
  minuteHandStyle = {};
  mapId: string;
  date: string;

  constructor(private http: HttpClient,
              private router: Router,
              private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.mapId = params['id'];
      this.date = params['date'];
    });
  }

  selectHour(hour: number) {
    this.selectedHour = hour;
    this.selectedMinute = null; // Reset selected minute when hour is changed
    this.updateClockHands();
  }

  selectMinute(minute: number) {
    this.selectedMinute = minute;
    this.updateClockHands();
  }

  selectPeriod(period: string) {
    this.selectedPeriod = period;
  }

  updateClockHands() {
    const hourDegree = ((this.selectedHour || 0) % 12) * 30 + (this.selectedMinute || 0) / 2;
    const minuteDegree = (this.selectedMinute || 0) * 6;
    this.hourHandStyle = {
      transform: `rotate(${hourDegree}deg)`,
      transition: 'transform 0.3s ease'
    };
    this.minuteHandStyle = {
      transform: `rotate(${minuteDegree}deg)`,
      transition: 'transform 0.3s ease'
    };
  }

  getHourStyle(hour: number) {
    const angle = (hour * 30) - 90; // -90 to start at the top
    return {
      transform: `rotate(${angle}deg) translate(160px) rotate(${-angle}deg)`
    };
  }

  getMinuteStyle(minute: number) {
    const angle = (minute * 6) - 90; // -90 to start at the top
    return {
      transform: `rotate(${angle}deg) translate(190px)`
    };
  }

  formatTime(hour: number, minute: number, period: string): string {
    return `${hour}:${minute.toString().padStart(2, '0')} ${period}`;
  }

  sendTime() {
    if (this.selectedHour !== null && this.selectedMinute !== null && this.selectedPeriod !== null) {
      // Convert selected time to 24-hour format
      const { hourIn24HourFormat, minutes } = this.convertTo24Hour(this.selectedHour, this.selectedMinute, this.selectedPeriod);
  
      // Format the time
      const time = `${hourIn24HourFormat}:${minutes}`;
  
      console.log(time);
      // Assuming you want to navigate to a route with parameters including the formatted time
      this.router.navigate(['/maps/' + this.mapId + '/availabilities/' + this.date + "/" + time]);
    }
  }
  
  convertTo24Hour(selectedHour: number, selectedMinute: number, selectedPeriod: string): { hourIn24HourFormat: number, minutes: string } {
    let hourIn24HourFormat = selectedHour;
    
    if (selectedPeriod === 'AM' && selectedHour === 12) {
      // If it's 12 AM, convert hour to 0
      hourIn24HourFormat = 0;
    } else if (selectedPeriod === 'PM' && selectedHour !== 12) {
      // If it's PM and not 12 PM, add 12 to the hour
      hourIn24HourFormat += 12;
    }
    
    // Format minutes to add leading zero if necessary
    const minutes = selectedMinute < 10 ? `0${selectedMinute}` : `${selectedMinute}`;
  
    return { hourIn24HourFormat, minutes };
  }

  goBack() {
    this.router.navigate(['/maps/' + this.mapId + '/availabilities']);
  }
  
}
