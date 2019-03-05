import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from './model/doctor';
import { Visit } from './model/visit';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  getDoctorsBySpecialization(specialization: string): Observable<Array<Doctor>> {
    return this.http.get<Array<Doctor>>('http://localhost:8080/doctor/' + specialization);

  }

  getDoctorById(id: string): Observable<Doctor> {
    return this.http.get<Doctor>('http://localhost:8080/doctor/id/' + id);
  }

  getDoctorProfil() {
    return this.http.get('http://localhost:8080/doctor/profile');
  }

  getPatientProfil() {
    return this.http.get('http://localhost:8080/patient/profile');
  }

  addVisit(visit): Observable<HttpResponse<Object>> {
    console.log(visit);
    return this.http.post('http://localhost:8080/visit/order', visit, {
      observe: 'response'
    });
  }

  sortVisit(id: string): Observable<Array<Visit>> {
    return this.http.get<Array<Visit>>('http://localhost:8080/visit/sort/' + id);
  }
}
