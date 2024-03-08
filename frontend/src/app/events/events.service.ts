import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { EventsComponent } from "./events.component";

@Injectable()
export class EventsService{

    private eventUrl: string;

    constructor(private http: HttpClient){
        this.eventUrl = 'http://localhost:8080/events';
    }

    public getEvents(): Observable<EventsComponent[]>{
        return this.http.get<EventsComponent[]>(this.eventUrl);
    }
}