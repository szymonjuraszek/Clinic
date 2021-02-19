import { Component, OnInit } from '@angular/core';
import { HttpService } from '../http-service/http.service';
import { Doctor } from '../model/doctor';
import { Patient } from '../model/patient';
import { CookieService } from 'ngx-cookie-service';
import * as jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private http: HttpService, private cookieService: CookieService) { }

  doctor: Doctor;
  patient: Patient;
  userInfoFromToken;

  ngOnInit() {
    this.userInfoFromToken = jwt_decode(this.cookieService.get('access_token'));


    if (this.userInfoFromToken.authorities[0].toString() === 'ROLE_DOCTOR') {
      this.http.getDoctorProfile().subscribe((data) => {
         this.doctor = data;
        // console.log(this.doctor);
      });
    } else if (this.userInfoFromToken.authorities[0].toString() === 'ROLE_PATIENT') {
      this.http.getPatientProfile().subscribe((data) => {
         this.patient = data;
        // console.log(this.patient);
      });
    }

  }

}
