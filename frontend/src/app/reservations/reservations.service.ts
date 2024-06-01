import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ReservationsComponent } from "./reservations.component";
import { HttpHeaders } from "@angular/common/http";
import { eventNames } from "process";
import { PlacesComponent } from "../places/places.component";

@Injectable()
export class ReservationsService{

    private reservationUrl : string;
    private createReservationUrl : string;
    private myReservationUrl : string;
    private createMeetingRoomReservationUrl : string;
    private deleteReservationUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.reservationUrl = 'http://localhost:8080/reservations';
        this.createReservationUrl = 'http://localhost:8080/';
        this.myReservationUrl = 'http://localhost:8080/my/reservations/';
        this.createMeetingRoomReservationUrl = 'http://localhost:8080/';
        this.deleteReservationUrl = 'http://localhost:8080/reservations/';
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

    public getMyReservations(userId: string): Observable<ReservationsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<ReservationsComponent[]>(this.myReservationUrl + userId, headers);
        }
    }

    public createMeetingRoomReservation(imgId: string, date: string, roomId: string, userId: string, eventName: string, reservationStartHour: string, reservationEndHour: string, flag: boolean): Observable<PlacesComponent> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.post<PlacesComponent>(this.createMeetingRoomReservationUrl + imgId + '/' + date + '/reservation/meetingRoom/' + roomId , {
                reservationStartHour,
                reservationEndHour,
                eventName,
                userId,
                flag
            }, headers);
        }
    }

    public deleteReservation(reservationId: string): Observable<ReservationsComponent> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.delete<ReservationsComponent>(this.deleteReservationUrl + reservationId + '/delete', headers);
        }
    }
}