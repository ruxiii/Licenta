import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TeamsComponent } from "./teams.component";
import { HttpHeaders } from "@angular/common/http";

@Injectable()
export class TeamsService{

    private teamUrl : string;
    private createTeamUrl : string;
    private deleteTeamUrl : string;
    private updateTeamUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.teamUrl = 'http://localhost:8080/teams';
        this.createTeamUrl = 'http://localhost:8080/teams/create';
        this.deleteTeamUrl = 'http://localhost:8080/teams/';
        this.updateTeamUrl = 'http://localhost:8080/teams/';
    }

    public getTeams(): Observable<TeamsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<TeamsComponent[]>(this.teamUrl, headers);
        }
    }

    
    public getTeamsIds(): Observable<TeamsComponent[]> {
        return this.http.get<TeamsComponent[]>('http://localhost:8080/teams');
    }

    createTeam(teamId: string, departmentId: string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.post(this.createTeamUrl, {
                teamId,
                departmentId
            });
        }
    }

    deleteTeam(teamId: string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            const deleteUrl = this.deleteTeamUrl + teamId + '/delete';
            return this.http.delete(deleteUrl, headers);
        }
    }

    updateTeam(teamId: string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            const updateUrl = this.updateTeamUrl + teamId + '/update';
            return this.http.put(updateUrl, headers);
        }
    }
}