import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import {AppComponent} from './app.component';
import {HttpService} from './http-service/http.service';
import {AppRoutingModule} from './app.routing.module';
import {HomeComponent} from './home/home.component';

import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {LoginPageComponent} from './auth/login-page/login-page.component';
import {RegisterPageComponent} from './auth/register-page/register-page.component';
import {AuthService} from './auth/auth.service';
import {AuthInerceptor} from './auth/auth.interceptor';
import {CookieService} from 'ngx-cookie-service';
import {ItemComponent} from './home/item/item.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {DoctorTmpDetailsComponent} from './doctor-tmp/doctor-tmp-details/doctor-tmp-details.component';

import 'core-js/es/array';
import 'zone.js/dist/zone';

import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SpecializationFieldComponent} from './home/specialization-field/specialization-field.component';
import {PasswordValidatorDirective} from "./auth/register-page/directive/password-validator.directive";
import {UserDetailsComponent} from './user-profile/user-details/user-details.component';
import {UserVisitComponent} from './user-profile/user-visit/user-visit.component';
import {EditModalComponent} from './user-profile/user-details/edit-modal/edit-modal.component';
import {DoctorVisitSettingsComponent} from './user-profile/doctor-visit-settings/doctor-visit-settings.component';
import {EditImageModalComponent} from './user-profile/user-details/edit-image-modal/edit-image-modal.component';
import {LoggerModule, NgxLoggerLevel} from "ngx-logger";
import { AccordionModule } from 'ngx-bootstrap/accordion';

import { RouterModule } from '@angular/router';


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
    SpecializationFieldComponent,
    PasswordValidatorDirective,
    UserDetailsComponent,
    UserVisitComponent,
    EditModalComponent,
    DoctorVisitSettingsComponent,
    EditImageModalComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    LoggerModule.forRoot({
      serverLoggingUrl: '/api/logs',
      level: NgxLoggerLevel.DEBUG,
      serverLogLevel: NgxLoggerLevel.ERROR
    }),
    AccordionModule.forRoot(),
    NgbModule
  ],
  providers: [HttpService, AuthService, CookieService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInerceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

platformBrowserDynamic().bootstrapModule(AppModule).then(ref => {
  // Ensure Angular destroys itself on hgot reloads.
  if (window['ngRef']) {
    window['ngRef'].destroy();
  }
  window['ngRef'] = ref;

  // Otherwise, log the boot error
}).catch(err => console.error(err));
