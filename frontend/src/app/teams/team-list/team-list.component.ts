import { Component } from '@angular/core';
import { TeamsService } from '../teams.service';
import { TeamsComponent } from '../teams.component';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { ThemeService } from '../../theme-toggle/theme.service';
import { AxiosService } from '../../axios.service';

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
            private themeService: ThemeService,
            private axiosService: AxiosService
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
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

  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }

  onNewTeam() {
    this.router.navigate(['/teams/create']);
  }

  deleteTeam(team: TeamsComponent) {
    if (confirm('Are you sure you want to delete this team?')) {
      const deleteUrl = '/teams/' + team.teamId +'/delete'; // Adjust the URL as needed
      this.axiosService.request('DELETE', deleteUrl, null)
        .then(() => {
          this.teams = this.teams.filter(t => t !== team);
        })
        .catch(error => {
          console.error('Error deleting team:', error);
        });
    }
  }

  updateTeam(team: TeamsComponent) {
    this.router.navigate(['/teams/' + team.teamId + '/update']);
  }

}
