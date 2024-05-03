import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { DepartmentsService } from '../departments.service';
import { DepartmentsComponent } from '../departments.component';

@Component({
  selector: 'app-department-update',
  templateUrl: './department-update.component.html',
  styleUrl: './department-update.component.css'
})
export class DepartmentUpdateComponent implements OnInit{
  isDarkMode: boolean;
  somethingWrong = false;
  departments: any[] = [];
  depId: string;

  constructor(  private axiosService: AxiosService, 
                private router: Router,
                private route: ActivatedRoute,
                private departmentService: DepartmentsService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.depId = params['id']; 
    });
    this.loadDepartments();
  }

  loadDepartments() {
    this.departmentService.getDepartments()
      .subscribe(departments => {
        this.departments = departments;
      }, error => {
        console.error('Error fetching departments:', error);
      });
  }

  onSubmit(departmentForm: NgForm) {
    const value = departmentForm.value;
  
    if (confirm('Are you sure you want to update this department?')) {
      const updateUrl = `/departments/${this.depId}/update`; 
      const requestData = {
        departmentId: this.depId,
        departmentName: value.departmentName // Assuming departmentName is the field name
      };

      console.log('Updating department:', requestData);
      console.log(this.departments)

      this.axiosService.request(
        'PUT', 
        updateUrl, 
        requestData  // Send departmentId and departmentName in the request body
      ).then(() => {
          this.departments = this.departments.map(d => {
            if (d.departmentId === value.departmentId) {
              return {...d, ...value}; 
            }
            return d;
          });
          this.router.navigate(['/departments']);
        })
        .catch(error => {
          console.error('Error updating department:', error);
        });
    }
}
}
