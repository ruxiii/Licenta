import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { PlacesComponent } from "./places.component";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class PlacesService {

    private placeUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.placeUrl = 'http://localhost:8080/places';
    }

    public getPlaces(): Observable<PlacesComponent[]> {
        const url = '/login';
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
        const headers = { headers: header };
        return this.http.get<PlacesComponent[]>(this.placeUrl, headers);
    }

}