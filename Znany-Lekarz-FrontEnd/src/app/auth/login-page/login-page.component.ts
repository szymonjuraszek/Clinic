import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthService} from '../auth.service';
import {NgForm} from '@angular/forms';
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {
  }

  @ViewChild('formData')
  formData: NgForm;

  errorMessage: string;

  ngOnInit() {
  }

  login(formData: NgForm) {
    this.authService.login(formData.value.email,
      formData.value.password).subscribe((res) => {

      if (res.status === 200) {
        this.authService.userStatus = true;
        this.router.navigate(['/UserProfile']);
      } else {
        console.log('nie udalo sie')
      }

    }, (error => {
      console.error(error)
    }));

    this.formData.reset();
  }

}
