import {Address} from "./Address";

export interface Visit {

  duration?: number;

  typeVisit?: string;

  timeVisit?: Date;

  idDoctor?: string;

  idPatient?: string;

  address?: Address;
}
