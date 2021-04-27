import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProquiEquiposVentaSharedModule } from 'app/shared/shared.module';
import { UsuarioComponent } from './usuario.component';
import { UsuarioDetailComponent } from './usuario-detail.component';
import { UsuarioUpdateComponent } from './usuario-update.component';
import { UsuarioDeleteDialogComponent } from './usuario-delete-dialog.component';
import { UsuarioGuardadoeDialogComponent } from './usuario-guardadoe-dialog.component';
import { usuarioRoute } from './usuario.route';

@NgModule({
  imports: [ProquiEquiposVentaSharedModule, RouterModule.forChild(usuarioRoute)],
  declarations: [
    UsuarioComponent,
    UsuarioDetailComponent,
    UsuarioUpdateComponent,
    UsuarioDeleteDialogComponent,
    UsuarioGuardadoeDialogComponent
  ],
  entryComponents: [UsuarioDeleteDialogComponent, UsuarioGuardadoeDialogComponent]
})
export class ProquiEquiposVentaUsuarioModule {}
