import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http.service';
import { Doctor } from '../model/doctor';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private httpService: HttpService) { }

  doctors: Array<Doctor>;

  ngOnInit() {
  }

  searchDoctors(specialization) {

    this.httpService.getDoctorsBySpecialization(specialization.value).subscribe( (res) => {
      console.log(res);
      if (res === null) {
        console.log('Nie znaleziono!');
      } else {
        console.log('Znaleziono lekarzy o danej specializacji');
        this.doctors = res;
      }
    });
  }

}
