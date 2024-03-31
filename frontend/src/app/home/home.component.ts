import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  homeMessage: string;
  adminMessage: string;
  showHomeMessage: boolean = false;
  showAdminMessage: boolean = false;

  constructor(private homeService: HomeService) { }

  ngOnInit(): void {
    this.homeService.home().subscribe(response => {
      this.homeMessage = response; // Store the home page message
      // this.showHomeMessage = true; // Display home message
      // this.showAdminMessage = false; // Hide admin message
    });
  }

  //   this.homeService.admin().subscribe(response => {
  //     this.adminMessage = response; // Store the admin page message
  //     this.showAdminMessage = true; // Display admin message
  //     this.showHomeMessage = false; // Hide home message
  //   });
  // }
}
