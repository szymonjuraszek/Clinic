import {Day} from "./Day";

export class TimeOfVisit {

  start: string;

  end: string;

  day: Day;

  constructor(start: string, end: string, day: Day) {
    this.start = start;
    this.end = end;
    this.day = day;
  }
}
