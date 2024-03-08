import { Component } from '@angular/core';
import { TeamsService } from '../teams.service';
import { TeamsComponent } from '../teams.component';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrl: './team-list.component.css'
})
export class TeamListComponent {
  teams: TeamsComponent[];

  constructor(private teamsService: TeamsService) {
  }

  ngOnInit() {
    this.teamsService.getTeams().subscribe(data => {
      this.teams = data;
    });
  }
}
