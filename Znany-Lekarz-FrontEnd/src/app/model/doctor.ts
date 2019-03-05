import { Visit } from './visit';

export interface Doctor {
  id?: string;
  firstName?: string;
  lastName?: string;
  specialization?: string;
  email?: string;
  password?: string;
  phoneNumber?: number;
  visitList?: Array<Visit>;
}
