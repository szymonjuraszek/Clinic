import {Component, ViewChild} from '@angular/core';
import {AuthService} from '../auth.service';
import {NgForm} from '@angular/forms';
import {Patient} from 'src/app/model/patient';
import {Doctor} from 'src/app/model/doctor';
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

  register(formData: NgForm, patientRadioButton) {
    this.errorMessage = '';

    if (patientRadioButton.checked) {

      const patientTmp: Patient = formData.value;

      console.error(patientTmp)
      this.authService.registerPatient(patientTmp).subscribe((res) => {
        this.router.navigate(['/loginPage']);
      }, error => {
        this.errorMessage = 'Blad podczas rejestracji';
      });

      this.formData.reset();

    } else {
      const doctorTmp: Doctor = formData.value

      this.authService.registerDoctor(doctorTmp).subscribe((res) => {
        this.router.navigate(['/loginPage']);
      }, error => {
        this.errorMessage = 'Blad podczas rejestracji';
      });

      this.formData.reset();
    }
  }
}

