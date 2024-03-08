import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsersComponent } from './users.component';

@Injectable()
export class UsersService {

  private userUrl : string;

  constructor(private http: HttpClient) { 
    this.userUrl = 'http://localhost:8080/users';
  }

  public getUsers(): Observable<UsersComponent[]> {
    return this.http.get<UsersComponent[]>(this.userUrl);
  }  
}