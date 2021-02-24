import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecializationFieldComponent } from './specialization-field.component';

describe('SpecializationFieldComponent', () => {
  let component: SpecializationFieldComponent;
  let fixture: ComponentFixture<SpecializationFieldComponent>;

  beforeEach(async(() => {
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
