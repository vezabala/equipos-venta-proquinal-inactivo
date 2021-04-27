import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { EquipoComponent } from 'app/entities/equipo/equipo.component';
import { UsuarioComponent } from 'app/entities/usuario/usuario.component';
import { EquipoEscritorioComponent } from 'app/entities/equipoEscritorio/equipoEscritorio.component';
const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

const routes: Routes = [
  { path: '', component: EquipoComponent },
  { path: 'equipos', component: EquipoComponent },
  { path: '**', redirectTo: 'equipos', pathMatch: 'full' },
  { path: '', component: UsuarioComponent },
  { path: 'usuarios', component: UsuarioComponent },
  { path: '**', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: '', component: EquipoEscritorioComponent },
  { path: 'equipos', component: EquipoEscritorioComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN]
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule)
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule)
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class ProquiEquiposVentaAppRoutingModule {}
