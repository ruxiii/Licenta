import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TeamsComponent } from '../teams.component';
import { DepartmentsService } from '../../departments/departments.service';
import { DepartmentsComponent } from '../../departments/departments.component';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['./team-edit.component.css'] // Corrected property name
})
export class TeamEditComponent implements OnInit {
  isDarkMode: boolean;
  somethingWrong = false;
  team: TeamsComponent = new TeamsComponent(); // Initialize the team object
  departments: DepartmentsComponent[] = [];
  departmentsArray: string[] = [];

  constructor(
    private router: Router,
    private departmentsService: DepartmentsService,
    private axiosService: AxiosService
  ) {}

  ngOnInit(): void {
    this.departmentsService.getDepartments().subscribe(departments => {
      this.departments = departments;
      this.departmentsArray = departments.map(dep => dep.departmentId);
    });
  }

  onSubmit(teamForm: NgForm) {
    const value = teamForm.value; 
    // console.log(value);
    this.axiosService.request(
      "POST",
      "/teams/create",
      {
        teamId: value.teamId, 
        teamName: value.teamName,
        departmentId: value.departmentId 
      }
    ).then(() => {
      this.router.navigate(['/teams']);
    }).catch(
      error => {
        if (error.response.status === 403) {
          this.somethingWrong = true;
        }
      }
    );
  }
}
