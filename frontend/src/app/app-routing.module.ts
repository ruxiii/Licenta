import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './users/user-list/user-list.component';
import { NgModule } from '@angular/core';
import { DepartmentListComponent } from './departments/department-list/department-list.component';
import { MapListComponent } from './maps/map-list/map-list.component';
import { PlaceListComponent } from './places/place-list/place-list.component';
import { TeamListComponent } from './teams/team-list/team-list.component';
import { ReservationListComponent } from './reservations/reservation-list/reservation-list.component';
import { EventListComponent } from './events/event-list/event-list.component';

const appRoutes: Routes = [
    { path: 'departments', component: DepartmentListComponent },
    { path: 'teams', component: TeamListComponent },
    { path: 'users', component: UserListComponent },
    { path: 'maps', component: MapListComponent },
    { path: 'places', component: PlaceListComponent },
    { path: 'reservations', component: ReservationListComponent},
    { path: 'events', component: EventListComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})

export class AppRoutingModule {}