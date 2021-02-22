import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Doctor} from '../model/doctor';
import {Patient} from '../model/patient';
import {CookieService} from 'ngx-cookie-service';
import {URLS} from "../http-service/URLS";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public userStatus: boolean;

  constructor(private http: HttpClient, private cookieService: CookieService) {
    const idToken = this.cookieService.get('access_token');
    if (idToken) {
      this.userStatus = true;
    }
  }

  login(email: string, password: string) {
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(URLS.LOGIN, {email, password}, {
      headers: httpHeaders,
      observe: 'response'
    });
  }

  registerDoctor(doctor: Doctor): Observable<Response> {
    return this.http.post<Response>(URLS.DOCTOR_SIGN_UP, doctor);
  }

  registerPatient(patient: Patient): Observable<Response> {
    return this.http.post<Response>(URLS.PATIENT_SIGN_UP, patient);
  }

  logout() {
    this.cookieService.delete('access_token');
    this.userStatus = false;
  }
}
