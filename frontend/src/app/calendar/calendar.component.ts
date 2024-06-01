import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ThemeService } from '../theme-toggle/theme.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { ActivatedRoute } from '@angular/router'

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  calendar: number[][] = [];
  currentYear: number = new Date().getFullYear();
  currentMonth: number = new Date().getMonth();
  months: string[] = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  mapId: string;

  constructor(private themeService: ThemeService, 
              public dialog: MatDialog,
              private router: Router,
              private route: ActivatedRoute
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
    this.isDarkMode = isDark;
    });
    }

  ngOnInit() {
    this.generateCalendar(this.currentYear, this.currentMonth);
    this.route.params.subscribe(params => {
      this.mapId = params['id'];
    });
  }

  generateCalendar(year: number, month: number) {
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const firstDay = new Date(year, month).getDay();

    this.calendar = [];
    let week: number[] = [];

    for (let i = 0; i < firstDay; i++) {
      week.push(null); // Add empty cells for days before the first day of the month
    }

    for (let date = 1; date <= daysInMonth; date++) {
      week.push(date);
      if (week.length === 7) {
        this.calendar.push(week);
        week = [];
      }
    }

    // Add the last week if it's not complete
    if (week.length > 0) {
      while (week.length < 7) {
        week.push(null);
      }
      this.calendar.push(week);
    }
  }

  prevMonth() {
    this.currentMonth = this.currentMonth === 0 ? 11 : this.currentMonth - 1;
    this.currentYear = this.currentMonth === 11 ? this.currentYear - 1 : this.currentYear;
    this.generateCalendar(this.currentYear, this.currentMonth);
  }

  nextMonth() {
    this.currentMonth = this.currentMonth === 11 ? 0 : this.currentMonth + 1;
    this.currentYear = this.currentMonth === 0 ? this.currentYear + 1 : this.currentYear;
    this.generateCalendar(this.currentYear, this.currentMonth);
  }

  onDateClick(day: number) {
    if (!day) {
      return; // If the day is null (empty cell), do nothing
    }
  
    const currentDate = new Date(); // Get the current date
  
    const clickedDate = new Date(this.currentYear, this.currentMonth, day);
    clickedDate.setHours(currentDate.getHours(), currentDate.getMinutes(), currentDate.getSeconds(), currentDate.getMilliseconds());
  
    const dayOfWeek = clickedDate.getDay(); // Get the day of the week (0 for Sunday, 1 for Monday, etc.)

    if (dayOfWeek === 6 || dayOfWeek === 0) {
      return; // If it's a weekend day, do nothing
    }

    if (clickedDate < currentDate) {
      return;
    }
  
    const formattedDate = day.toString().padStart(2, '0') + '-' + (this.currentMonth + 1).toString().padStart(2, '0') + '-' + this.currentYear;
  
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '250px',
      data: { date: formattedDate }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'yes') {
        this.router.navigate(['/maps/' + this.mapId + '/availabilities/' +  formattedDate]);
      }
    });
  }
  
  
  
  getMonthName(month: number): string {
    const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    return monthNames[month];
  }

  changeMonth() {
    this.generateCalendar(this.currentYear, this.currentMonth);
  }

  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }

  isPastDate(day: number): boolean {
    if (!day) {
      return false; // If the day is null (empty cell), consider it not in the past
    }
  
    const currentDate = new Date(); // Get the current date
  
    const clickedDate = new Date(this.currentYear, this.currentMonth, day);
    clickedDate.setHours(currentDate.getHours(), currentDate.getMinutes(), currentDate.getSeconds(), currentDate.getMilliseconds());
  
    // Check if the clicked date is before the current date
    return clickedDate < currentDate;
  }
  
  isWeekend(day: number): boolean {
    if (!day) {
      return false; // If the day is null (empty cell), it's not a weekend
    }
  
    const clickedDate = new Date(this.currentYear, this.currentMonth, day);
    const dayOfWeek = clickedDate.getDay(); // Get the day of the week (0 for Sunday, 1 for Monday, etc.)
  
    return dayOfWeek === 0 || dayOfWeek === 6; // Return true if it's Sunday (0) or Saturday (6)
  }
  
  goBack(){
    this.router.navigate(['/maps']);
  }

}
