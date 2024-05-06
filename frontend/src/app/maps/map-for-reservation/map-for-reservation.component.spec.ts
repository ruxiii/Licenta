import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapForReservationComponent } from './map-for-reservation.component';

describe('MapForReservationComponent', () => {
  let component: MapForReservationComponent;
  let fixture: ComponentFixture<MapForReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MapForReservationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MapForReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
