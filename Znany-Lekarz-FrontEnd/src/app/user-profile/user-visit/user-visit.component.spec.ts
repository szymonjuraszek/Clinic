import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UserVisitComponent } from './user-visit.component';

describe('UserVisitComponent', () => {
  let component: UserVisitComponent;
  let fixture: ComponentFixture<UserVisitComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UserVisitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserVisitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
