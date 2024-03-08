import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { PlacesComponent } from "./places.component";

@Injectable()
export class PlacesService {

    private placeUrl : string;

    constructor(private http: HttpClient) { 
        this.placeUrl = 'http://localhost:8080/places';
    }

    public getPlaces(): Observable<PlacesComponent[]> {
        return this.http.get<PlacesComponent[]>(this.placeUrl);
    }

}