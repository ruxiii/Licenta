import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ReservationsComponent } from "./reservations.component";
import { HttpHeaders } from "@angular/common/http";

@Injectable()
export class ReservationsService{

    private reservationUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.reservationUrl = 'http://localhost:8080/reservations';
    }

    public getReservations(): Observable<ReservationsComponent[]> {
        const url = '/login';
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
        const headers = { headers: header };
        return this.http.get<ReservationsComponent[]>(this.reservationUrl, headers);
    }
}