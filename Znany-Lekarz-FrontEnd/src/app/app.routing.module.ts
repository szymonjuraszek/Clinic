import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {HomeComponent} from './home/home.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';

import {LoginPageComponent} from './auth/login-page/login-page.component';
import {RegisterPageComponent} from './auth/register-page/register-page.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {DoctorTmpDetailsComponent} from './doctor-tmp/doctor-tmp-details/doctor-tmp-details.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'UserProfile',
    component: UserProfileComponent,
  },
  {
    path: 'DoctorTmp',
    children: [
      {
        path: ':id',
        component: DoctorTmpDetailsComponent
      }
    ]
  },
  {
    path: 'RegisterPage',
    component: RegisterPageComponent
  },
  {
    path: 'loginPage',
    component: LoginPageComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];


@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
