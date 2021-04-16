import {Component, Input, OnInit} from '@angular/core';
import {Doctor} from "../../model/Doctor";
import {Role} from "../../model/Role";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PlaceVisitSettings} from "../../model/PlaceVisitSettings";

@Component({
  selector: 'app-doctor-visit-settings',
  templateUrl: './doctor-visit-settings.component.html',
  styleUrls: ['./doctor-visit-settings.component.css']
})
export class DoctorVisitSettingsComponent implements OnInit{

  @Input()
  doctor: Doctor;

  ngOnInit(): void {
    if(this.doctor.placeVisitSettingsArray === undefined) {
      this.doctor.placeVisitSettingsArray = new Array<PlaceVisitSettings>();
    }
  }

}
