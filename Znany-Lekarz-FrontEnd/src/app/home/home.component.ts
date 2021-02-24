import {Component} from '@angular/core';
import {HttpService} from '../http-service/http.service';
import {Doctor} from '../model/doctor';
import {Subscription} from 'rxjs';
import {HttpErrorResponse} from "@angular/common/http";
import {LocalCacheService} from "../service/local-cache.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  doctorSpecializations: Array<String>;

  specialization: String;

  doctors: Array<Doctor>;

  notFoundMessage: string;

  specializationSubscription: Subscription;

  constructor(private localCacheService: LocalCacheService, private httpService: HttpService) {
    this.doctorSpecializations = this.localCacheService.getDoctorSpecializations();
    this.specializationSubscription = this.localCacheService.getDoctorSpecializationsSubject().subscribe( (specializations) => {
      this.doctorSpecializations = specializations;
    })
  }

  searchDoctors(specialization: string) {
    this.notFoundMessage = '';

    this.httpService.getDoctorsBySpecialization(specialization).subscribe((doctors) => {
      console.log('Znaleziono lekarzy o danej specializaji');
      console.log(doctors);
      this.doctors = doctors;
    }, (error: HttpErrorResponse) => {
      if (error.status === 404) {
        this.notFoundMessage = 'Nie znaleziono lekarza o tej specializacji';
        this.doctors = null;
      }
      console.error(error)
    });
  }

  ngOnDestroy() {
    this.specializationSubscription.unsubscribe();
  }

  setSpecialization(specialization: string) {
    this.specialization = specialization;
  }
}
