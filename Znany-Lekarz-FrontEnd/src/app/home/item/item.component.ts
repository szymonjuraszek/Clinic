import {Component, Input, OnInit} from '@angular/core';
import {Doctor} from '../../model/Doctor';
import {NgbCalendar, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {URLS} from "../../http-service/URLS";

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  @Input()
  doctors: Doctor[];

  model: NgbDateStruct;
  date: { year: number, month: number };
  baseURL = URLS.PHOTO_BASE;

  constructor(private calendar: NgbCalendar) {
  }

  ngOnInit() {
    // for(let doctor of this.doctors) {
    //   if (doctor.imageLocation) {
    //     this.ifImageUploaded = true;
    //     this.img.src = URLS.PHOTO_BASE + this.user.imageLocation;
    //     this.logger.log('Get image from server URL: ', this.img.src);
    //   } else {
    //     this.user['imageLocation'] = '';
    //   }
    // }
  }

  sendData(doctor: Doctor) {
    // this.localService.doctor = doctor;
  }

  selectToday() {
    console.error(this.model)
    this.model = this.calendar.getToday();
  }
}
