import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEquipo, Equipo } from 'app/shared/model/equipo.model';
import { EquipoPortatilService } from './equipoPortatil.service';
import { EquipoPortatilComponent } from './equipoPortatil.component';
import { EquipoPortatilDetailComponent } from 'app/entities/equipoPortaril/equipo-Portatil-detail.component';
import { EquipoPortatilUpdateComponent } from 'app/entities/equipoPortaril/equipo-Portatil-update.component';
import { PortausuarioUpdateComponent } from './portausuario-update.component';

@Injectable({ providedIn: 'root' })
export class EquipoResolve implements Resolve<IEquipo> {
  constructor(private service: EquipoPortatilService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEquipo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((equipo: HttpResponse<Equipo>) => {
          if (equipo.body) {
            return of(equipo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Equipo());
  }
}

export const equipoPortatilRoute: Routes = [
  {
    path: '',
    component: EquipoPortatilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'proquiEquiposVentaApp.equipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EquipoPortatilDetailComponent,
    resolve: {
      equipo: EquipoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'proquiEquiposVentaApp.equipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EquipoPortatilUpdateComponent,
    resolve: {
      equipo: EquipoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'proquiEquiposVentaApp.equipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/newusuario',
    component: PortausuarioUpdateComponent,
    resolve: {
      equipo: EquipoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'proquiEquiposVentaApp.usuario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EquipoPortatilUpdateComponent,
    resolve: {
      equipo: EquipoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'proquiEquiposVentaApp.equipo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
