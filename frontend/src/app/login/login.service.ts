import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class LoginService{

    private loginUrl : string;

    constructor(private http: HttpClient) { 
        this.loginUrl = 'http://localhost:8080/login';
    }

    public login(login: string, password: string): Observable<boolean> {
        return this.http.post<boolean>(this.loginUrl, {login: login, password: password});
    }
}