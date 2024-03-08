import { Component } from '@angular/core';
import { DepartmentsService } from '../departments.service';
import { DepartmentsComponent } from '../departments.component';


@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrl: './department-list.component.css'
})
export class DepartmentListComponent {
  departments: DepartmentsComponent[];

  constructor(private departmentsService: DepartmentsService) {
  }

  ngOnInit() {
    this.departmentsService.getDepartments().subscribe(data => {
      this.departments = data;
    });
  }
}
