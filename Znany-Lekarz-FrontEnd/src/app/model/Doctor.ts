import {Address} from "./Address";
import {User} from "./User";

export interface Doctor extends User {

  id?: string;

  specialization?: string;

  places?: Array<Address>;

  degree?: string;
}
