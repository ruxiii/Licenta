import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { DepartmentsComponent } from './departments.component';
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class DepartmentsService {
    private departmentUrl : string;
    private createDepartmentUrl : string;
    private deleteDepartmentUrl : string;
    private updateDepartmentUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.departmentUrl = 'http://localhost:8080/departments';
        this.createDepartmentUrl = 'http://localhost:8080/departments/create';
        this.deleteDepartmentUrl = 'http://localhost:8080/departments/';
        this.updateDepartmentUrl = 'http://localhost:8080/departments/';
    }
    public getDepartments(): Observable<DepartmentsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<DepartmentsComponent[]>(this.departmentUrl, headers);
        }
    }  

    public getDepartmentsIds(): Observable<DepartmentsComponent[]> {
        return this.http.get<DepartmentsComponent[]>('http://localhost:8080/departments');
    }

    createDepartment(departmentId: string, departmentName: string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.post(this.createDepartmentUrl, {
                departmentId,
                departmentName
            });
        }
    }

    deleteDepartment(departmentId: string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            const deleteUrl = this.deleteDepartmentUrl + departmentId + '/delete';
            return this.http.delete(deleteUrl, headers);
        }
    }

    updateDepartment(departmentId: string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            const updateUrl = this.updateDepartmentUrl + departmentId + '/update';
            return this.http.put(updateUrl, headers);
        }
    }
}