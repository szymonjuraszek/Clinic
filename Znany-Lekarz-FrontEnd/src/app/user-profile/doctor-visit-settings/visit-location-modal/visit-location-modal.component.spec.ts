import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitLocationModalComponent } from './visit-location-modal.component';

describe('VisitLocationModalComponent', () => {
  let component: VisitLocationModalComponent;
  let fixture: ComponentFixture<VisitLocationModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitLocationModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitLocationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
