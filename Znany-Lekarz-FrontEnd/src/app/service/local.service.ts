import { Injectable } from '@angular/core';
import { Doctor } from '../model/doctor';

@Injectable({
  providedIn: 'root'
})
export class LocalService {

  doctor: Doctor;
  constructor() { }

}
