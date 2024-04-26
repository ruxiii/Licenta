import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { MapsComponent } from "./maps.component";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class MapsService {

    private mapUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.mapUrl = 'http://localhost:8080/maps';
    }

    public getMaps(): Observable<MapsComponent[]> {
        const url = '/login';
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
        const headers = { headers: header };
        return this.http.get<MapsComponent[]>(this.mapUrl, headers);
    }
}