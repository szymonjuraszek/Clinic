import {Component, ViewChild} from '@angular/core';
import {AuthService} from '../auth.service';
import {NgForm} from '@angular/forms';
import {Patient} from 'src/app/model/Patient';
import {Doctor} from 'src/app/model/Doctor';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {

  @ViewChild('formData')
  formData: NgForm;

  errorMessage: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  register(patientRadioButton) {
    this.errorMessage = '';

    if (patientRadioButton.checked) {

      const patientTmp: Patient = this.formData.value;

      console.error(patientTmp)
      this.authService.registerPatient(patientTmp).subscribe((res) => {
        this.router.navigate(['/loginPage']);
      }, error => {
        console.error(error);
        this.errorMessage = 'Blad podczas rejestracji';
      });

      this.formData.reset();

    } else {
      const doctorTmp: Doctor = this.formData.value

      this.authService.registerDoctor(doctorTmp).subscribe((res) => {
        this.router.navigate(['/loginPage']);
      }, error => {
        console.error(error);
        this.errorMessage = 'Blad podczas rejestracji';
      });

      this.formData.reset();
    }
  }

  handleChange(evt) {
    this.formData.reset();
    console.debug('Changed register form to: ' + evt.target.value)
  }
}

