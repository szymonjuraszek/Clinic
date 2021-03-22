import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DoctorVisitSettingsComponent } from './doctor-visit-settings.component';

describe('DoctorVisitSettingsComponent', () => {
  let component: DoctorVisitSettingsComponent;
  let fixture: ComponentFixture<DoctorVisitSettingsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DoctorVisitSettingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorVisitSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
