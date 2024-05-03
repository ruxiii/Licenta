import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-department-edit',
  templateUrl: './department-edit.component.html',
  styleUrl: './department-edit.component.css'
})
export class DepartmentEditComponent {
  isDarkMode: boolean;
  somethingWrong = false;

  constructor(  private axiosService: AxiosService, 
                private router: Router) {
  }

  onSubmit(departmentForm: NgForm) {
    const value = departmentForm.value; 
    // console.log(value);
    this.axiosService.request(
      "POST",
      "/departments/create",
      {
        departmentId: value.departmentId, 
        departmentName: value.departmentName 
      }
    ).then(() => {
      // Redirect to '/welcome' route upon successful creation
      this.router.navigate(['/departments']);
    }).catch(
      error => {
        if (error.response.status === 403) {
          this.somethingWrong = true;
        }
      }
    );
  }
    

}
