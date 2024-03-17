import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UsersComponent } from './users/users.component';
import { UserListComponent } from './users/user-list/user-list.component';
import { AppRoutingModule } from './app-routing.module';
import { UsersService } from './users/users.service';
import { HttpClientModule, HttpClient, HttpBackend } from '@angular/common/http';
import 'whatwg-fetch';
import { HeaderComponent } from './header/header.component';
import { DepartmentsComponent } from './departments/departments.component';
import { TeamsComponent } from './teams/teams.component';
import { MapsComponent } from './maps/maps.component';
import { PlacesComponent } from './places/places.component';
import { ReservationsComponent } from './reservations/reservations.component';
import { EventsComponent } from './events/events.component';
import { DepartmentListComponent } from './departments/department-list/department-list.component';
import { TeamListComponent } from './teams/team-list/team-list.component';
import { MapListComponent } from './maps/map-list/map-list.component';
import { PlaceListComponent } from './places/place-list/place-list.component';
import { ReservationListComponent } from './reservations/reservation-list/reservation-list.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { DepartmentsService } from './departments/departments.service';
import { EventsService } from './events/events.service';
import { MapsService } from './maps/maps.service';
import { PlacesService } from './places/places.service';
import { ReservationsService } from './reservations/reservations.service';
import { TeamsService } from './teams/teams.service';
import { UserEditComponent } from './users/user-edit/user-edit.component';
import { FormsModule } from '@angular/forms';
import { UserRolesService } from './users/user-roles.service';
import { ReactiveFormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ThemeToggleComponent } from './theme-toggle/theme-toggle.component';
import { ThemeService } from './theme-toggle/theme.service';
import { MatIconModule } from '@angular/material/icon';
import { NgxPaginationModule } from 'ngx-pagination';


export function provideCustomHttpClient(backend: HttpBackend): HttpClient {
  return new HttpClient(backend);
}

@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    UserListComponent,
    HeaderComponent,
    DepartmentsComponent,
    TeamsComponent,
    MapsComponent,
    PlacesComponent,
    ReservationsComponent,
    EventsComponent,
    DepartmentListComponent,
    TeamListComponent,
    MapListComponent,
    PlaceListComponent,
    ReservationListComponent,
    EventListComponent,
    UserEditComponent,
    ThemeToggleComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule ,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    NgxPaginationModule
    ],
  providers: [
    provideClientHydration(), 
    UsersService,
    DepartmentsService,
    EventsService,
    MapsService,
    PlacesService,
    ReservationsService,
    TeamsService,
    UserRolesService,
    ThemeService,

    {
      provide: HttpClient,
      useFactory: provideCustomHttpClient,
      deps: [HttpBackend]
    },
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
