import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SpecializationFieldComponent } from './specialization-field.component';

describe('SpecializationFieldComponent', () => {
  let component: SpecializationFieldComponent;
  let fixture: ComponentFixture<SpecializationFieldComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecializationFieldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecializationFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
