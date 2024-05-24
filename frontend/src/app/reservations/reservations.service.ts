import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ReservationsComponent } from "./reservations.component";
import { HttpHeaders } from "@angular/common/http";
import { eventNames } from "process";

@Injectable()
export class ReservationsService{

    private reservationUrl : string;
    private createReservationUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.reservationUrl = 'http://localhost:8080/reservations';
        this.createReservationUrl = 'http://localhost:8080/';
    }

    public getReservations(): Observable<ReservationsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<ReservationsComponent[]>(this.reservationUrl, headers);
        }
    }

    public createReservation(imgId: string, date: string, seatId: string, userId: string, eventName: string, reservationStartHour: string, reservationEndHour: string): Observable<ReservationsComponent> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.post<ReservationsComponent>(this.createReservationUrl + imgId + '/' + date + '/reservation/' + seatId , {
                reservationStartHour,
                reservationEndHour,
                eventName,
                userId
            }, headers);
        }
    }
}