import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { DepartmentsComponent } from './departments.component';

@Injectable()
export class DepartmentsService {

    private departmentUrl : string;

    constructor(private http: HttpClient) { 
        this.departmentUrl = 'http://localhost:8080/departments';
    }
    public getDepartments(): Observable<DepartmentsComponent[]> {
        return this.http.get<DepartmentsComponent[]>(this.departmentUrl);
    }  
}