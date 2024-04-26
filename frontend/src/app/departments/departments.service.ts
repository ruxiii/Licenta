import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { DepartmentsComponent } from './departments.component';
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class DepartmentsService {

    private departmentUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.departmentUrl = 'http://localhost:8080/departments';
    }
    public getDepartments(): Observable<DepartmentsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<DepartmentsComponent[]>(this.departmentUrl, headers);
        }
    }  
}