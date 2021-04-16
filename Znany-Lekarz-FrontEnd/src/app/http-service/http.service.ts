import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Doctor} from '../model/Doctor';
import {Visit} from '../model/Visit';
import {URLS} from "./URLS";
import {Patient} from "../model/Patient";
import {PlaceVisitSettings} from "../model/PlaceVisitSettings";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) {
  }

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

  getAllAvailableSpecializations(): Observable<Array<string>> {
    return this.http.get<Array<string>>(URLS.DOCTOR_SPECIALIZATIONS);
  }

  updateDoctor(doctorToUpdate: Doctor) {
    return this.http.put(URLS.DOCTOR_PROFILE, doctorToUpdate, {
      observe: 'response'
    });
  }

  updatePatient(patientToUpdate: Patient) {
    return this.http.put(URLS.PATIENT_PROFILE, patientToUpdate, {
      observe: 'response'
    });
  }

  addProfileImage(image): Observable<HttpResponse<Object>> {
    let formData: FormData = new FormData();
    formData.append('image', image);

    return this.http.post(URLS.PHOTO, formData, {
      observe: 'response'
    });
  }

  addVisitPlaceSettings(placeVisitSettings: PlaceVisitSettings): Observable<HttpResponse<Object>>  {
    return this.http.post(URLS.DOCTOR_VISIT_SETTINGS, placeVisitSettings, {
      observe: 'response'
    });
  }

}
