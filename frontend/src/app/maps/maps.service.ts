import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { MapsComponent } from "./maps.component";

@Injectable()
export class MapsService {

    private mapUrl : string;

    constructor(private http: HttpClient) { 
        this.mapUrl = 'http://localhost:8080/maps';
    }

    public getMaps(): Observable<MapsComponent[]> {
        return this.http.get<MapsComponent[]>(this.mapUrl);
    }
}