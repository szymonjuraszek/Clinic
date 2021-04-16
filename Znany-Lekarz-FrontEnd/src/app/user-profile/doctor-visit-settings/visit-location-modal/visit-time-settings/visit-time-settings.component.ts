import {Component, ElementRef, Input, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ControlContainer, ControlValueAccessor, NgForm} from "@angular/forms";
import {Day} from "../../../../model/Day";

@Component({
  selector: 'app-visit-time-settings',
  templateUrl: './visit-time-settings.component.html',
  styleUrls: ['./visit-time-settings.component.css'],
  viewProviders: [{provide: ControlContainer, useExisting: NgForm}]
})
export class VisitTimeSettingsComponent implements OnInit {

  @Input()
  day: Day;

  timeSetterFields = new Array<any>(10);

  counter: number;

  howManyFields = [];

  value = 11

  @ViewChildren('dayTimeFrom') dayTimeFromInputs: QueryList<ElementRef>;


  ngOnInit(): void {
    this.howManyFields.push(++this.counter);
  }

  addNewTimeField() {
    this.counter = this.counter + 2;
    this.howManyFields.push(this.counter);
  }

  removeTimeField() {
    const value = this.howManyFields.pop();
    if (this.counter > 0) {
      this.counter = this.counter - 2;
    }
  }

  validTimePicker(event, index) {

    for (let i of this.dayTimeFromInputs.toArray()) {
      console.error(i.nativeElement)

    }

    console.error('index: ' + index)
    console.error('wartosc: ' + event)

    if(index % 2 == 0) {
      this.timeSetterFields[index] = event;
      if(this.timeSetterFields[index + 1] > event) {
        console.error('ok')
      }
    } else {
      this.timeSetterFields[index] = event;
      if(this.timeSetterFields[index - 1] < event) {
        console.error('ok')
      }
    }

    for (let i = 0; i < this.timeSetterFields.length - 1; i = i + 2) {
      console.error(this.timeSetterFields[i]);
      console.error(this.timeSetterFields[i+1]);
    }
    // console.error('Przerwa')

  }
}
