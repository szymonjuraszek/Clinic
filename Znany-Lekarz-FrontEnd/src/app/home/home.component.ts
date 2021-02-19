import {Component, ViewChild} from '@angular/core';
import {HttpService} from '../http-service/http.service';
import {Doctor} from '../model/doctor';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {Observable, Subject, merge} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  doctorSpecializations: Array<String>;

  doctors: Array<Doctor>;

  isDataAvailable: boolean = false;

  notFoundMessage: string;

  // @ts-ignore
  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  constructor(private httpService: HttpService) {
    if (!this.doctorSpecializations) {
      this.httpService.getAllAvailableSpecializations().subscribe(value => {
        this.doctorSpecializations = value;
        this.isDataAvailable = true;
      }, error => {
        console.log(error)
      })
    }
  }

  searchDoctors(specialization) {
    this.notFoundMessage = '';

    this.httpService.getDoctorsBySpecialization(specialization.value).subscribe((doctors) => {
      console.log('Znaleziono lekarzy o danej specializaji');
      console.log(doctors);
      this.doctors = doctors;
    }, (error: HttpErrorResponse) => {
      if (error.status === 404) {
        this.notFoundMessage = 'Nie znaleziono lekarza o tej specializacji';
        this.doctors = null;
      }
      console.error(error)
    });
  }

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.doctorSpecializations
        : this.doctorSpecializations.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  }
}
