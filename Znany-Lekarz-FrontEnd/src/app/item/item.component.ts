import {Component, Input} from '@angular/core';
import {Doctor} from '../model/doctor';
import {LocalService} from '../service/local.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent {

  constructor(private localService: LocalService) {
  }

  @Input()
  doctors: Doctor[];

  sendData(doctor: Doctor) {
    this.localService.doctor = doctor;
  }
}
