import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditImageModalComponent } from './edit-image-modal.component';

describe('EditImageModalComponent', () => {
  let component: EditImageModalComponent;
  let fixture: ComponentFixture<EditImageModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditImageModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditImageModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
