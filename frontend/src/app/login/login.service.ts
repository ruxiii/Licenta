import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })
export class LoginService{

    private loginUrl : string;

    constructor(private http: HttpClient) { 
        this.loginUrl = 'http://localhost:8080/login';
    }

    login(login: string, password: string): Observable<any> {
        return this.http.post<any>(this.loginUrl, {login: login, password: password});
    }

}