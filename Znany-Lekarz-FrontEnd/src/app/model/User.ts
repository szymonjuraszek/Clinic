import {Visit} from "./Visit";
import {Role} from "./Role";

export interface User {

  firstName?: string;

  lastName?: string;

  email?: string;

  password?: string;

  phoneNumber?: number;

  visits?: Array<Visit>;

  role?: Role;

  imageLocation?: string;
}
