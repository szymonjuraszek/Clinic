import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitTimeSettingsComponent } from './visit-time-settings.component';

describe('VisitTimeSettingsComponent', () => {
  let component: VisitTimeSettingsComponent;
  let fixture: ComponentFixture<VisitTimeSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitTimeSettingsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitTimeSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
