import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISignatureLevel, SignatureLevel } from 'app/shared/model/signature-level.model';
import { SignatureLevelService } from './signature-level.service';
import { SignatureLevelComponent } from './signature-level.component';
import { SignatureLevelDetailComponent } from './signature-level-detail.component';
import { SignatureLevelUpdateComponent } from './signature-level-update.component';

@Injectable({ providedIn: 'root' })
export class SignatureLevelResolve implements Resolve<ISignatureLevel> {
  constructor(private service: SignatureLevelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISignatureLevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((signatureLevel: HttpResponse<SignatureLevel>) => {
          if (signatureLevel.body) {
            return of(signatureLevel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SignatureLevel());
  }
}

export const signatureLevelRoute: Routes = [
  {
    path: '',
    component: SignatureLevelComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'signatureApplicationApp.signatureLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SignatureLevelDetailComponent,
    resolve: {
      signatureLevel: SignatureLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'signatureApplicationApp.signatureLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SignatureLevelUpdateComponent,
    resolve: {
      signatureLevel: SignatureLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'signatureApplicationApp.signatureLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SignatureLevelUpdateComponent,
    resolve: {
      signatureLevel: SignatureLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'signatureApplicationApp.signatureLevel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
