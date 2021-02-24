import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';
import {HttpService} from "./http-service/http.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor( private authService: AuthService, private router: Router/*, private httpService: HttpService*/) {
    // this.httpService.getAllAvailableSpecializations().subscribe(value => {
    //   // this.doctorSpecializations = value;
    // }, error => {
    //   console.log(error)
    // })
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

}

