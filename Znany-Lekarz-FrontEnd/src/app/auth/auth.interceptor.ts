import { HttpInterceptor, HttpRequest, HttpEvent, HttpHandler } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthInerceptor implements HttpInterceptor {

  constructor(private cookieService: CookieService) { }

  intercept(req: HttpRequest<any>,
     next: HttpHandler): Observable<HttpEvent<any>> {

      // const idToken = localStorage.getItem('id_token');
      const idToken = this.cookieService.get('access_token');
      // this.cookieService.delete('access_token');

      console.log('Na zewnatrz');

      if (idToken) {
        const cloned = req.clone({
          headers: req.headers.set('Authorization',
          'Bearer ' + idToken)
        });

        return next.handle(cloned);

      } else {
        return next.handle(req);
      }

  }

}
