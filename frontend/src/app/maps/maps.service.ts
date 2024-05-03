import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { MapsComponent } from "./maps.component";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class MapsService {

    private mapUrl : string;
    private createMapUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.mapUrl = 'http://localhost:8080/maps';
        this.createMapUrl = 'http://localhost:8080/maps/create';
    }

    public getMaps(): Observable<MapsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<MapsComponent[]>(this.mapUrl, headers);
        }
    }

    createMap(mapDto: any, mapFile: File): Observable<any> {
        const formData = new FormData();
        formData.append('map', JSON.stringify(mapDto));
        formData.append('mapFile', mapFile);
    
        const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem('auth_token'));
        const headers = { headers: header };
    
        return this.http.post(this.createMapUrl, formData, headers);
      }
}