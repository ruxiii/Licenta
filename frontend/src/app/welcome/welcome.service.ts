import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class WelcomeService {

    private welcomeUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.welcomeUrl = 'http://localhost:8080/maps';
    }

    public getMaps(): Observable<any[]> {
        const url = '/login';
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
        const headers = { headers: header };
        return this.http.get<any[]>(this.welcomeUrl, headers);
    }
}