import { Component, OnInit, Input } from '@angular/core';
import { Doctor } from '../model/doctor';
import { LocalService } from '../service/local.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {

  constructor(private localService: LocalService) { }

  @Input()
  doctors: Doctor;

  message = 'Znaleziono';

  sendData(doctor: Doctor) {
    this.localService.doctor = doctor;

  }


  ngOnInit() {
  }

}
