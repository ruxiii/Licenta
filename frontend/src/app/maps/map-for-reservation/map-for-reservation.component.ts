import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { MapsService } from '../maps.service';

@Component({
  selector: 'app-map-for-reservation',
  templateUrl: './map-for-reservation.component.html',
  styleUrl: './map-for-reservation.component.css'
})
export class MapForReservationComponent implements OnInit{
  imgId: string;
  imageUrl: string;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private mapsService: MapsService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.imgId = params['id']; 
    });
    this.fetchImage();
  }

  fetchImage() {
    this.mapsService.getMapById(this.imgId).subscribe(
      res => {
        this.retrieveResonse = res;
        this.base64Data = this.retrieveResonse.mapImage;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
      }
    );
  }

  onSeatClick(seatId: string) {
    // Define the content to be displayed in the popup window, including a calendar
    const content = `
      <!DOCTYPE html>
      <html>
        <head>
          <title>Seat Information</title>
          <style>
            body { font-family: Arial, sans-serif; padding: 20px; }
            h1 { color: #333; }
            .calendar {
              width: 100%;
              max-width: 400px;
              border-collapse: collapse;
            }
            .calendar th, .calendar td {
              border: 1px solid #ccc;
              padding: 10px;
              text-align: center;
            }
            .calendar th {
              background-color: #f2f2f2;
            }
            .calendar td {
              cursor: pointer;
            }
          </style>
        </head>
        <body>
          <h1>Seat Information</h1>
          <p>Seat ID: ${seatId}</p>
          <div id="calendar-container"></div>
          <script>
            function generateCalendar(year, month) {
              const calendarContainer = document.getElementById('calendar-container');
              calendarContainer.innerHTML = '';
  
              const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
              const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  
              const firstDay = new Date(year, month).getDay();
              const daysInMonth = new Date(year, month + 1, 0).getDate();
  
              const calendarTable = document.createElement('table');
              calendarTable.className = 'calendar';
  
              const headerRow = document.createElement('tr');
              const header = document.createElement('th');
              header.colSpan = 7;
              header.innerHTML = '<button onclick="prevMonth()">&#10094;</button> ' + monthNames[month] + ' ' + year + ' <button onclick="nextMonth()">&#10095;</button>';
              headerRow.appendChild(header);
              calendarTable.appendChild(headerRow);
  
              const daysRow = document.createElement('tr');
              for (const day of daysOfWeek) {
                const th = document.createElement('th');
                th.innerText = day;
                daysRow.appendChild(th);
              }
              calendarTable.appendChild(daysRow);
  
              let dateRow = document.createElement('tr');
              for (let i = 0; i < firstDay; i++) {
                const emptyCell = document.createElement('td');
                dateRow.appendChild(emptyCell);
              }
  
              for (let date = 1; date <= daysInMonth; date++) {
                if (dateRow.children.length === 7) {
                  calendarTable.appendChild(dateRow);
                  dateRow = document.createElement('tr');
                }
                const dateCell = document.createElement('td');
                dateCell.innerText = date.toString();
                dateCell.onclick = () => alert('You clicked on ' + date + ' ' + monthNames[month] + ' ' + year);
                dateRow.appendChild(dateCell);
              }
              calendarTable.appendChild(dateRow);
              calendarContainer.appendChild(calendarTable);
            }
  
            let currentYear = new Date().getFullYear();
            let currentMonth = new Date().getMonth();
  
            function prevMonth() {
              if (currentMonth === 0) {
                currentMonth = 11;
                currentYear--;
              } else {
                currentMonth--;
              }
              generateCalendar(currentYear, currentMonth);
            }
  
            function nextMonth() {
              if (currentMonth === 11) {
                currentMonth = 0;
                currentYear++;
              } else {
                currentMonth++;
              }
              generateCalendar(currentYear, currentMonth);
            }
  
            document.addEventListener('DOMContentLoaded', () => {
              generateCalendar(currentYear, currentMonth);
            });
          </script>
        </body>
      </html>
    `;
  
    // Define window features (e.g., width, height, etc.)
    const features = 'width=600,height=600,left=100,top=100,resizable=yes,scrollbars=yes,status=no';
  
    // Open the popup window
    const popup = window.open('', '_blank', features);
  
    if (popup) {
      // Write the content to the popup window
      popup.document.open();
      popup.document.write(content);
      popup.document.close();
    } else {
      alert('Popup blocked. Please allow popups for this site.');
    }
  }
  
  
}
