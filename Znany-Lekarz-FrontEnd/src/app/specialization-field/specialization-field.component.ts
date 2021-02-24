import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {merge, Observable, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, filter, map} from "rxjs/operators";
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-specialization-field',
  templateUrl: './specialization-field.component.html',
  styleUrls: ['./specialization-field.component.css']
})
export class SpecializationFieldComponent {

  @Input() doctorSpecializations: Array<String>;

  @Output() specializationEvent = new EventEmitter<string>();

  // @ts-ignore
  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  search = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
    const inputFocus$ = this.focus$;

    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.doctorSpecializations
        : this.doctorSpecializations.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  }

  onKey(event) {
    this.specializationEvent.emit(event.target.value);
  }

  onFocus(event) {
    this.focus$.next(event.target.value);
    this.specializationEvent.emit(event.target.value);
  }

  onClick(event) {
    this.click$.next(event.target.value);
    this.specializationEvent.emit(event.target.value);
  }
}
