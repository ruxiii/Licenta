import { Component, OnInit } from '@angular/core';
import { TeamsComponent } from '../teams.component';
import { DepartmentsComponent } from '../../departments/departments.component';
import { ActivatedRoute, Router } from '@angular/router';
import { AxiosService } from '../../axios.service';
import { TeamsService } from '../teams.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-team-update',
  templateUrl: './team-update.component.html',
  styleUrl: './team-update.component.css'
})
export class TeamUpdateComponent implements OnInit{
  isDarkMode: boolean;
  somethingWrong = false;
  team: TeamsComponent = new TeamsComponent(); 
  departments: DepartmentsComponent[] = [];
  departmentsArray: string[] = [];
  teamId: string;
  teams: TeamsComponent[];

  constructor(  private router: Router,
                private teamsService: TeamsService,
                private axiosService: AxiosService,
                private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.teamId = params['id']; 
    });
    this.loadTeams();
  }

  loadTeams(){
    this.teamsService.getTeams().subscribe(teams => {
      this.teams = teams;
      const departmentIdsSet = new Set(teams.map(team => team.departmentId));
      this.departmentsArray =  Array.from(departmentIdsSet);
    });
  }

  onSubmit(teamForm: NgForm) {
    const value = teamForm.value;
  
    if (confirm('Are you sure you want to update this department?')) {
      const updateUrl = `/teams/${this.teamId}/update`; 
      const requestData = {
        teamId: this.teamId,
        teamName: value.teamName,
        departmentId: value.departmentId
      };

      this.axiosService.request(
        'PUT', 
        updateUrl, 
        requestData  // Send departmentId and departmentName in the request body
      ).then(() => {
          this.teams = this.teams.map(t => {
            if (t.teamId === value.teamId) {
              return {...t, ...value}; 
            }
            return t;
          });
          this.router.navigate(['/teams']);
        })
        .catch(error => {
          console.error('Error updating department:', error);
        });
    }
  }
}
