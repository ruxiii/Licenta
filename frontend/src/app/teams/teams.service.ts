import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TeamsComponent } from "./teams.component";
import { HttpHeaders } from "@angular/common/http";

@Injectable()
export class TeamsService{

    private teamUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.teamUrl = 'http://localhost:8080/teams';
    }

    public getTeams(): Observable<TeamsComponent[]> {
        const url = '/login';
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
        const headers = { headers: header };
        return this.http.get<TeamsComponent[]>(this.teamUrl, headers);
    }

    
    public getTeamsIds(): Observable<TeamsComponent[]> {
        return this.http.get<TeamsComponent[]>('http://localhost:8080/teams');
    }
}