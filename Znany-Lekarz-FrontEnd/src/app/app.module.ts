import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AppComponent } from './app.component';
import { HttpService } from './http.service';
import { AppRountingModule } from './app.routing.module';
import { HomeComponent } from './home/home.component';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginPageComponent } from './auth/login-page/login-page.component';
import { RegisterPageComponent } from './auth/register-page/register-page.component';
import { AuthService } from './auth/auth.service';
import { AuthInerceptor } from './auth/auth.interceptor';
import { CookieService } from 'ngx-cookie-service';
import { ItemComponent } from './item/item.component';
import { LocalService } from './service/local.service';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { DoctorTmpDetailsComponent } from './doctor-tmp/doctor-tmp-details/doctor-tmp-details.component';


import 'core-js/es6/reflect';
import 'core-js/es7/reflect';
import 'zone.js/dist/zone';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginPageComponent,
    PageNotFoundComponent,
    RegisterPageComponent,
    ItemComponent,
    UserProfileComponent,
    DoctorTmpDetailsComponent,

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRountingModule,
    BrowserModule,
    BrowserAnimationsModule
  ],
  providers: [HttpService, AuthService, LocalService, CookieService,
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInerceptor,
    multi: true
  }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

platformBrowserDynamic().bootstrapModule(AppModule).then(ref => {
  // Ensure Angular destroys itself on hgot reloads.
  if (window['ngRef']) {
    window['ngRef'].destroy();
  }
  window['ngRef'] = ref;

  // Otherwise, log the boot error
}).catch(err => console.error(err));
