import { Component } from '@angular/core';
import { DepartmentsService } from '../departments.service';
import { DepartmentsComponent } from '../departments.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-department-list',
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css']
})
export class DepartmentListComponent {
  departments: DepartmentsComponent[];
  POSTS: any;
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  page: number = 1;
  count: number = 0;
  tableSize: number = 10;
  tableSizes: number[] = [5, 10, 15, 20];

  constructor(private departmentsService: DepartmentsService,
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
    this.departmentsService.getDepartments().subscribe(data => {
      this.departments = data;
    });
    this.postList();
  }

  onNewDepartment() {
    this.router.navigate(['/departments/create']);
  }

  postList(): void {
    this.departmentsService.getDepartments().subscribe(response => {
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

  deleteDepartment(dep: DepartmentsComponent) {
    if (confirm('Are you sure you want to delete this department?')) {
      console.log('Deleting department:', dep);
      const deleteUrl = '/departments/' + dep.departmentId +'/delete'; // Adjust the URL as needed
      this.axiosService.request('DELETE', deleteUrl, null)
        .then(() => {
          this.departments = this.departments.filter(d => d !== dep);
        })
        .catch(error => {
          console.error('Error deleting department:', error);
        });
    }
  }

  updateDepartment(dep: DepartmentsComponent) {
    console.log('Updating department:', dep);
    const updateUrl = '/departments/' + dep.departmentId + '/update'; // Adjust the URL as needed
    this.axiosService.request('PUT', updateUrl, null)
      .then(() => {
        console.log('Department updated successfully');
      })
      .catch(error => {
        console.error('Error updating department:', error);
      });
  }
  
}
