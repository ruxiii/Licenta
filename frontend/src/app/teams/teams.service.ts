import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TeamsComponent } from "./teams.component";

@Injectable()
export class TeamsService{

    private teamUrl : string;

    constructor(private http: HttpClient) { 
        this.teamUrl = 'http://localhost:8080/teams';
    }

    public getTeams(): Observable<TeamsComponent[]> {
        return this.http.get<TeamsComponent[]>(this.teamUrl);
    }

    
    public getTeamsIds(): Observable<TeamsComponent[]> {
        return this.http.get<TeamsComponent[]>('http://localhost:8080/teams');
    }
}