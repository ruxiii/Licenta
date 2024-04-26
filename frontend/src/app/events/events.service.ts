import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { EventsComponent } from "./events.component";
import { HttpHeaders } from "@angular/common/http";

@Injectable()
export class EventsService{

    private eventUrl: string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient){
        this.eventUrl = 'http://localhost:8080/events';
    }

    public getEvents(): Observable<EventsComponent[]>{
        const url = '/login';
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
        const headers = { headers: header };
        return this.http.get<EventsComponent[]>(this.eventUrl, headers);
    }
}