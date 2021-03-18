import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/User";

@Component({
  selector: 'app-user-visit',
  templateUrl: './user-visit.component.html',
  styleUrls: ['./user-visit.component.css']
})
export class UserVisitComponent implements OnInit {

  @Input()
  user: any;

  constructor() {
  }

  ngOnInit() {
  }

}
