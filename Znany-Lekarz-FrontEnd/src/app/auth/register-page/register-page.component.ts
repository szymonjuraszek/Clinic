import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../auth.service';
import { NgForm } from '@angular/forms';
import { Patient } from 'src/app/model/patient';
import { Doctor } from 'src/app/model/doctor';


@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  @ViewChild('formData')
  formData: NgForm;

  constructor(private authService: AuthService) { }



  ngOnInit() {
  }

  register(formData: NgForm, patienRadioButton) {

    if (patienRadioButton.checked) {

      const patientTmp: Patient = ({email: formData.value.email , password: formData.value.password ,
        firstName: formData.value.firstName , lastName: formData.value.lastName , phoneNumber: formData.value.phoneNumber});

      this.authService.registerPatient(patientTmp).subscribe((res) => {
         // console.log('Status for register patient: ' + res.body);
      });
      this.formData.reset();


    } else {
      const doctorTmp: Doctor = ({email: formData.value.email, password: formData.value.password,
        firstName: formData.value.firstName, lastName: formData.value.lastName, phoneNumber: formData.value.phoneNumber,
         specialization: formData.value.specialization});

      this.authService.registerDoctor(doctorTmp).subscribe((res) => {
        // console.log('Status for register doctor: ' + res.body);

      });
      this.formData.reset();

    }
  }



}

