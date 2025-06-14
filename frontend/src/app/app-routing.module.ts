import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './users/user-list/user-list.component';
import { UserEditComponent } from './users/user-edit/user-edit.component';
import { NgModule } from '@angular/core';
import { DepartmentListComponent } from './departments/department-list/department-list.component';
import { MapListComponent } from './maps/map-list/map-list.component';
import { PlaceListComponent } from './places/place-list/place-list.component';
import { TeamListComponent } from './teams/team-list/team-list.component';
import { ReservationListComponent } from './reservations/reservation-list/reservation-list.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { DepartmentEditComponent } from './departments/department-edit/department-edit.component';
import { DepartmentUpdateComponent } from './departments/department-update/department-update.component';
import { TeamEditComponent } from './teams/team-edit/team-edit.component';
import { TeamUpdateComponent } from './teams/team-update/team-update.component';
import { MapEditComponent } from './maps/map-edit/map-edit.component';
import { UserUpdateComponent } from './users/user-update/user-update.component';
import { MapForReservationComponent } from './maps/map-for-reservation/map-for-reservation.component';
import { CalendarComponent } from './calendar/calendar.component';
import { ReservationEditComponent } from './reservations/reservation-edit/reservation-edit.component';
import { EventEditComponent } from './events/event-edit/event-edit.component';
import { ClockComponent } from './clock/clock.component';
import { MyReservationComponent } from './reservations/my-reservation/my-reservation.component';
import { MeetingRoomReservationComponent } from './reservations/meeting-room-reservation/meeting-room-reservation.component';


const appRoutes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },

    { path: 'departments', component: DepartmentListComponent },
    { path: 'departments/create', component: DepartmentEditComponent},
    { path: 'departments/:id/update', component: DepartmentUpdateComponent },

    { path: 'teams', component: TeamListComponent },
    { path: 'teams/create', component: TeamEditComponent},
    { path: 'teams/:id/update', component: TeamUpdateComponent},

    { path: 'users', component: UserListComponent},
    { path: 'users/create', component: UserEditComponent},
    { path: 'users/:id/update', component: UserUpdateComponent},
    
    { path: 'maps', component: MapListComponent },
    { path: 'maps/create', component: MapEditComponent},
    { path: 'maps/:id/availabilities', component: CalendarComponent},
    { path: 'maps/:id/availabilities/:date', component: ClockComponent },
    { path: 'maps/:id/availabilities/:date/:hour', component: MapForReservationComponent},
    
    { path: 'places', component: PlaceListComponent },
    
    { path: 'reservations', component: ReservationListComponent},
    { path: 'my/reservations', component: MyReservationComponent},
    { path: ':imgId/:date/reservation/:seatId', component: ReservationEditComponent}, 
    { path: ':imgId/:date/reservation/meetingRoom/:roomId', component: MeetingRoomReservationComponent}, 

    
    { path: 'events', component: EventListComponent},
    { path: 'events/create', component: EventEditComponent},
    
    { path: 'home', component: HomeComponent},
    
    { path: 'login', component: LoginComponent},
    
    { path: 'welcome', component: WelcomeComponent}
];


@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})

export class AppRoutingModule {}