import { Visit } from './visit';

export interface Patient {

  firstName?: string;
  lastName?: string;
  email?: string;
  password?: string;
  phoneNumber?: number;
  visits?: Array<Visit>;
}
