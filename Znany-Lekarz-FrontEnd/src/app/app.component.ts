import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor( private authService: AuthService, private router: Router) {
    console.error("App compoennt tu jestem")
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

}

