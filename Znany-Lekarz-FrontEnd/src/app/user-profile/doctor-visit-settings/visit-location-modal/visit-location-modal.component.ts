import {Component, Input, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PlaceVisitSettings} from "../../../model/PlaceVisitSettings";
import {TimeOfVisit} from "../../../model/TimeOfVisit";
import {Day} from "../../../model/Day";
import {Address} from "../../../model/Address";
import {HttpService} from "../../../http-service/http.service";
import {Doctor} from "../../../model/Doctor";

@Component({
  selector: 'app-visit-location-modal',
  templateUrl: './visit-location-modal.component.html',
  styleUrls: ['./visit-location-modal.component.css']
})
export class VisitLocationModalComponent {

  @Input()
  doctor: Doctor;

  days = Object.keys(Day);

  time = {hour: 13, minute: 30};

  constructor(private modalService: NgbModal, private httpService: HttpService) {
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((formData) => {

      const keysJson = Object.keys(formData.value).filter(item =>
        item !== 'street' &&
        item !== 'zip_code' &&
        item !== 'city' &&
        item !== 'visitDurationInMin'
      );

      const placeVisitSettings = new PlaceVisitSettings(
        new Address(
          formData.value['street'],
          formData.value['zip_code'],
          formData.value['city']
        ),
        formData.value['visitDurationInMin']
      )

      for (let i = 0; i < keysJson.length - 1; i = i + 2) {
        placeVisitSettings.generalTimetableForVisits.push(
          new TimeOfVisit(
            formData.value[keysJson[i]],
            formData.value[keysJson[i + 1]],
            Day[keysJson[i].split("_", 1)[0]]
          ));
      }
      //
      console.error(formData.value);

      this.httpService.addVisitPlaceSettings(placeVisitSettings).subscribe((res) => {
        console.error(res);
        this.doctor.placeVisitSettingsArray.push(placeVisitSettings);
      });

    }, (reason) => {
    });
  }

  validVisitDurationInMin(object) {
    if(object.data < 1 || object.data > 60) {
      object.data = 0;
    }
  }
}
