import { Injectable } from '@angular/core';
import {HttpService} from "../http-service/http.service";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LocalCacheService {

  private doctorSpecializationsSubject = new Subject<Array<String>>();
  private doctorSpecializations = new Array<String>();

  constructor(private httpService: HttpService) {
    this.httpService.getAllAvailableSpecializations().subscribe(specializations => {
      this.doctorSpecializations = specializations;
      this.doctorSpecializationsSubject.next(specializations);
    }, error => {
      console.log(error)
    })
  }

  getDoctorSpecializations() {
    return this.doctorSpecializations;
  }

  getDoctorSpecializationsSubject() {
    return this.doctorSpecializationsSubject.asObservable();
  }

}
