import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../auth.service';
import {NgForm} from '@angular/forms';
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent{

  constructor(private authService: AuthService, private router: Router) {
  }

  @ViewChild('formData')
  formData: NgForm;

  errorMessage: string;

  login(formData: NgForm) {
    this.errorMessage = '';

    this.authService.login(formData.value.email,
      formData.value.password).subscribe((res) => {

      if (res.status === 200) {
        this.authService.userStatus = true;
        this.router.navigate(['/UserProfile']);
      } else {
        this.errorMessage = 'Blad podczas logowania';
      }

    }, (error => {
      this.errorMessage = 'Blad podczas logowania';
    }));

    this.formData.reset();
  }

}
