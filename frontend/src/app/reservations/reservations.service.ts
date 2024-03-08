import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ReservationsComponent } from "./reservations.component";

@Injectable()
export class ReservationsService{

    private reservationUrl : string;

    constructor(private http: HttpClient) { 
        this.reservationUrl = 'http://localhost:8080/reservations';
    }

    public getReservations(): Observable<ReservationsComponent[]> {
        return this.http.get<ReservationsComponent[]>(this.reservationUrl);
    }
}