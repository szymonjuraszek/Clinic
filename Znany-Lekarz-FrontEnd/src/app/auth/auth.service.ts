import { Injectable, SystemJsNgModuleLoader } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from '../model/doctor';
import { Patient } from '../model/patient';
import { CookieService } from 'ngx-cookie-service';



@Injectable({
  providedIn: 'root'
})
export class AuthService {


  public userStatus: boolean;


  constructor(private http: HttpClient, private cookieService: CookieService) {
    const idToken = this.cookieService.get('access_token');
    if (idToken) {
      // console.log(cookieService);
      this.userStatus = true;
    }
   }

  login(email: string, password: string) {
    const httpHeaders = new HttpHeaders({
      'Content-Type' : 'application/json'
    });

    return this.http.post('/login', {email, password}, {
      headers: httpHeaders,
      observe: 'response'
    });
  }

  registerDoctor(doctor: Doctor): Observable<Response> {
    return this.http.post<Response>('http://localhost:8080/doctor/sign-up', doctor);

  }

  registerPatient(patient: Patient): Observable<Response> {
    return this.http.post<Response>('http://localhost:8080/patient/sign-up', patient);
  }

  logout() {
    this.cookieService.delete('access_token');
    this.userStatus = false;
  }

  /*getUser(res) {
    return this.http.get('http://localhost:8080/patient/sign-up');
  }*/
}
