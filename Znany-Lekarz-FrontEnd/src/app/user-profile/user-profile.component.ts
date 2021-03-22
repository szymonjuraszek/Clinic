import {Component, OnInit} from '@angular/core';
import {HttpService} from '../http-service/http.service';
import {CookieService} from 'ngx-cookie-service';
import jwt_decode from 'jwt-decode';
import {User} from "../model/User";
import {Role} from "../model/Role";
import {LogService} from "../logging/log.service";
import {URLS} from "../http-service/URLS";
import {Doctor} from "../model/Doctor";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private http: HttpService, private cookieService: CookieService, private logger: LogService) {
  }

  user: User;

  public get role(): typeof Role {
    return Role;
  }

  ngOnInit() {

    const userFromToken = jwt_decode(this.cookieService.get('access_token'));

    if (userFromToken['authorities'][0] === Role.ROLE_DOCTOR) {
      this.http.getDoctorProfile().subscribe((doctor: Doctor) => {
        this.user = doctor;
        this.logger.info('Doctor data retrieved from: ' + URLS.DOCTOR_PROFILE, this.user.email);
      }, (error => {
        this.logger.error(error);
      }));
    } else if (userFromToken['authorities'][0] === Role.ROLE_PATIENT) {
      this.http.getPatientProfile().subscribe((patient) => {
        console.error(patient)
        this.user = patient;
        this.logger.info('Patient data retrieved from: ' + URLS.PATIENT_PROFILE, this.user.email);
      }, error => {
        this.logger.error(error);
      });
    }

  }

}
