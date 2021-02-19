import { Component, OnInit} from '@angular/core';
import { HttpService } from 'src/app/http-service/http.service';
import { ActivatedRoute, Params} from '@angular/router';
import { Doctor } from 'src/app/model/doctor';
import { CookieService } from 'ngx-cookie-service';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-doctor-tmp-details',
  templateUrl: './doctor-tmp-details.component.html',
  styleUrls: ['./doctor-tmp-details.component.css']
})
export class DoctorTmpDetailsComponent implements OnInit {

  doctor?: Doctor;

  timeVisits: TimeVisit[];
  visitSelected: number;
  tmpDate: Date;

  idTokenn?;

  tmpDay: number;
  tmpMonth: number;
  tmpYear: number;
  tmpHour: number;

  userInfoFromToken;

  ngOnInit() {
    this.route.paramMap.subscribe((param: Params) => {
      this.http.getDoctorById(param.get('id')).subscribe((res) => {
        this.doctor = res;
        console.log('Jestem w ngOnInit DoctorTmpDetailsComponent');
        // console.log(this.doctor);
      });
    });
  }



  constructor(private http: HttpService, private cookieService: CookieService , private route: ActivatedRoute) {
     this.idTokenn = this.cookieService.get('access_token');

     this.timeVisits = [
      {id: 1, name: 9},
      {id: 2, name: 10},
      {id: 3, name: 11},
      {id: 4, name: 12},
      {id: 5, name: 13},
      {id: 6, name: 15},
      {id: 7, name: 16},
      {id: 8, name: 17},
      {id: 9, name: 18},
      {id: 10, name: 19}
     ];
   }


  order(data: NgForm) {

    const dateString = data.value.dateOfVisit;

    this.tmpDate = new Date(dateString);
    this.tmpDate.setHours(data.value.listHours, 0 , 0);

    console.log(this.tmpDate);

    if (new Date() < this.tmpDate) {

      const visit = {
        duration: 60,
        typeVisit: this.doctor.specialization,
        timeVisit: this.tmpDate,
        idDoctor: this.doctor.id,
      };

      this.http.addVisit(visit).subscribe((res) => {
        console.log('Odpowiedz addVisit()');

        if ( res.status === 226 ) {
          console.log('termin wizyty jest juz zajety');
          alert('Podany termin jest juz zajety');
        } else if ( res.status === 201 ) {
          console.log('Udalo sie umowic wizyte');
          alert('Udalo sie umowic wizyte');
          this.http.sortVisit(visit.idDoctor).subscribe((visits) => {
            this.doctor.visitList = visits;
          });
        } else {
          console.log('Blad serwera ' + res.status);
        }

      });
    } else {
      alert('To juz przeszlosc :)');
    }

  }
}

export class TimeVisit {
    public id: number;
    public name: number;
}



