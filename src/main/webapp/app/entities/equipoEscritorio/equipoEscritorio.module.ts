import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProquiEquiposVentaSharedModule } from 'app/shared/shared.module';
import { EquipoEscritorioComponent } from './equipoEscritorio.component';
import { EquipoEscritorioDetailComponent } from './equipo-Escritorio-detail.component';
import { EquipoEscritorioUpdateComponent } from './equipo-Escritorio-update.component';
import { EquipoEscritorioDeleteDialogComponent } from './equipo-Escritorio-delete-dialog.component';
import { EquipusuarioGuardadoeDialogComponent } from './equipusuario-guardadoe-dialog.component';
import { EquipusuarioUpdateComponent } from './equipusuario-update.component';
import { equipoEscritorioRoute } from './equipoEscritorio.route';

@NgModule({
  imports: [ProquiEquiposVentaSharedModule, RouterModule.forChild(equipoEscritorioRoute)],
  declarations: [
    EquipoEscritorioComponent,
    EquipoEscritorioDetailComponent,
    EquipoEscritorioUpdateComponent,
    EquipoEscritorioDeleteDialogComponent,
    EquipusuarioGuardadoeDialogComponent,
    EquipusuarioUpdateComponent
  ],
  entryComponents: [EquipoEscritorioDeleteDialogComponent]
})
export class ProquiEquiposVentaEquipoEscritorioModule {}
