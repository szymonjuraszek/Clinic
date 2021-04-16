import {Pipe, PipeTransform} from "@angular/core";
import {Day} from "../model/Day";

@Pipe({
  name: 'translate',
  pure: false,
})
export class DayTranslationPipe implements PipeTransform {

  translationSet = new TranslationSet();

  transform(value: Day): string {
    return this.translationSet.get(value);
  }
}

class TranslationSet {

  private dayTranslation: Map<Day, string> = new Map<Day, string>();

  constructor() {
    this.dayTranslation.set(Day.MONDAY, 'Poniedziałek');
    this.dayTranslation.set(Day.FRIDAY, 'Piątek');
    this.dayTranslation.set(Day.SATURDAY, 'Sobota');
    this.dayTranslation.set(Day.TUESDAY, 'Wtorek');
    this.dayTranslation.set(Day.SUNDAY, 'Niedziela');
    this.dayTranslation.set(Day.WEDNESDAY, 'Sroda');
    this.dayTranslation.set(Day.THURSDAY, 'Czwartek');
  }

  get(day: Day) {
    return this.dayTranslation.get(day);
  }

}
