import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { EventsComponent } from "./events.component";
import { HttpHeaders } from "@angular/common/http";

@Injectable()
export class EventsService{

    private eventUrl: string;
    private creareEventUrl: string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient){
        this.eventUrl = 'http://localhost:8080/events';
        this.creareEventUrl = 'http://localhost:8080/event';
    }

    public getEvents(): Observable<EventsComponent[]>{
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<EventsComponent[]>(this.eventUrl, headers);
        }
    }

    public createEvent(eventName: string){
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.post(this.creareEventUrl, {eventName}, headers);
        }
    }
}