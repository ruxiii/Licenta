import { Component } from '@angular/core';
import { TeamsService } from '../teams.service';
import { TeamsComponent } from '../teams.component';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrl: './team-list.component.css'
})
export class TeamListComponent {
  teams: TeamsComponent[];
  POSTS: any;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: number[] = [5, 10, 15, 20];

  constructor(private teamsService: TeamsService,
            private router: Router,
  ) {
  }

  ngOnInit() {
    this.teamsService.getTeams().subscribe(data => {
      this.teams = data;
    });
    this.postList();
  }

  postList(): void {
    this.teamsService.getTeams().subscribe(response => {
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

  // ngOnDestroy() {
  //   this.themeSubscription.unsubscribe();
  // }

  onNewTeam() {
    this.router.navigate(['/teams/create']);
  }
}
