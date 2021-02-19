import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from '../model/doctor';
import { Visit } from '../model/visit';
import {URLS} from "./URLS";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  getDoctorsBySpecialization(specialization: string): Observable<Array<Doctor>> {
    return this.http.get<Array<Doctor>>(URLS.DOCTOR_SPECIALIZATION + specialization);
  }

  getDoctorById(id: string): Observable<Doctor> {
    return this.http.get<Doctor>(URLS.DOCTOR_ID + id);
  }

  getDoctorProfile() {
    return this.http.get(URLS.DOCTOR_PROFILE);
  }

  getPatientProfile() {
    return this.http.get(URLS.PATIENT_PROFILE);
  }

  addVisit(visit): Observable<HttpResponse<Object>> {
    console.log(visit);
    return this.http.post(URLS.VISIT_ORDER, visit, {
      observe: 'response'
    });
  }

  sortVisit(id: string): Observable<Array<Visit>> {
    return this.http.get<Array<Visit>>(URLS.VISIT_SORT + id);
  }

  getAllAvailableSpecializations(): Observable<Array<String>> {
    return this.http.get<Array<String>>(URLS.DOCTOR_SPECIALIZATIONS);
  }
}
