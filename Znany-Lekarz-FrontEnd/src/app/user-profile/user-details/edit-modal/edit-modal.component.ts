import {Component, Input} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../http-service/http.service";
import {Role} from "../../../model/Role";
import {LogService} from "../../../logging/log.service";

@Component({
  selector: 'app-edit-modal',
  templateUrl: './edit-modal.component.html',
  styleUrls: ['./edit-modal.component.css']
})
export class EditModalComponent {

  @Input()
  user: any;

  constructor(private modalService: NgbModal, private httpService: HttpService, private logger: LogService) {
  }

  public get role(): typeof Role {
    return Role;
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((formData) => {

      switch (this.user.role) {
        case Role.ROLE_PATIENT: {
          this.logger.info('Update profile for ' + Role.ROLE_PATIENT.toString(), formData.value)
          this.httpService.updatePatient(formData.value).subscribe((response) => {

            if (response.status === 200) {
              this.logger.info('Response after update patient profile', response);
              this.user.firstName = response.body['firstName'];
              this.user.lastName = response.body['lastName'];
              this.user.phoneNumber = response.body['phoneNumber'];
            } else {
              this.logger.error('Response after update patient profile', response);
            }
          });

          break;
        }
        case Role.ROLE_DOCTOR: {
          this.logger.info('Update profile for ' + Role.ROLE_DOCTOR.toString(), formData.value)
          this.httpService.updateDoctor(formData.value).subscribe((response) => {
            this.logger.info('Response after update patient profile', response);
            this.user.firstName = response.body['firstName'];
            this.user.lastName = response.body['lastName'];
            this.user.phoneNumber = response.body['phoneNumber'];
            this.user.specialization = response.body['specialization'];
            this.user.degree = response.body['degree'];
          });

          break;
        }
      }

    }, (reason) => {
    });
  }

}
