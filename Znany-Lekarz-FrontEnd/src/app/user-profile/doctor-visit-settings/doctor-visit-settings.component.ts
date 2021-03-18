import {Component, Input} from '@angular/core';
import {Doctor} from "../../model/Doctor";

@Component({
  selector: 'app-doctor-visit-settings',
  templateUrl: './doctor-visit-settings.component.html',
  styleUrls: ['./doctor-visit-settings.component.css']
})
export class DoctorVisitSettingsComponent {

  @Input()
  doctor: Doctor;

  constructor() {
  }

}
