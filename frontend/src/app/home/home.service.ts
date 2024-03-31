import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class HomeService {
    private getHomePageUrl : string;
    private getAdminPageUrl : string;

  constructor(private http: HttpClient) { 
    this.getHomePageUrl = 'http://localhost:8080/home';
    this.getAdminPageUrl = 'http://localhost:8080/admin';
  }

  public home(): Observable<string> {
    return this.http.get(this.getHomePageUrl, { responseType: 'text' });
  }

    public admin(): Observable<string> {
        return this.http.get(this.getAdminPageUrl, {responseType: 'text'});
    }
}