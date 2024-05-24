import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { MapsComponent } from "./maps.component";
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class MapsService {

    private mapUrl : string;
    private createMapUrl : string;
    private getMapByIdUrl : string;
    tokenType = 'Bearer ';

    constructor(private http: HttpClient) { 
        this.mapUrl = 'http://localhost:8080/maps';
        this.createMapUrl = 'http://localhost:8080/maps/create';
        this.getMapByIdUrl = 'http://localhost:8080/maps/';
    }

    public getMaps(): Observable<MapsComponent[]> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            return this.http.get<MapsComponent[]>(this.mapUrl, headers);
        }
    }

    createMap(id: string, file: File): Observable<any> {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('id', id); // Append the ID to FormData
    
        // Set the headers including Authorization
        const headers = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem('auth_token'));
    
        // Make the HTTP POST request with formData and headers
        return this.http.post(this.createMapUrl, formData, { headers, reportProgress: true, observe: 'events' });
    }

    getMapById(id: string, date: string, hour:string): Observable<any> {
        const url = '/login';
        if (typeof window !== "undefined") {  
            const header = new HttpHeaders().set('Authorization', this.tokenType + window.localStorage.getItem("auth_token")); 
            const headers = { headers: header };
            const getUrl = this.getMapByIdUrl + id + "/availabilities/" + date + "/" + hour;
            return this.http.get<MapsComponent>(getUrl, headers);
        }
    }
    
}