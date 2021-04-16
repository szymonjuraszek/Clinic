import {Address} from "./Address";
import {TimeOfVisit} from "./TimeOfVisit";

export class PlaceVisitSettings {

  address: Address;

  visitDurationInMin: number;

  generalTimetableForVisits: Array<TimeOfVisit>;

  constructor(address: Address, visitDurationInMin: number) {
    this.address = address;
    this.visitDurationInMin = visitDurationInMin;
    this.generalTimetableForVisits = new Array<TimeOfVisit>();
  }
}
