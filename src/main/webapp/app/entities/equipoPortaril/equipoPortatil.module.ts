import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProquiEquiposVentaSharedModule } from 'app/shared/shared.module';
import { EquipoPortatilComponent } from './equipoPortatil.component';
import { EquipoPortatilDetailComponent } from './equipo-Portatil-detail.component';
import { EquipoPortatilUpdateComponent } from './equipo-Portatil-update.component';
import { EquipoPortatilDeleteDialogComponent } from './equipo-Portatil-delete-dialog.component';
import { PortausuarioGuardadoeDialogComponent } from './portausuario-guardadoe-dialog.component';
import { PortausuarioUpdateComponent } from './portausuario-update.component';
import { equipoPortatilRoute } from './equipoPortatil.route';

@NgModule({
  imports: [ProquiEquiposVentaSharedModule, RouterModule.forChild(equipoPortatilRoute)],
  declarations: [
    EquipoPortatilComponent,
    EquipoPortatilDetailComponent,
    EquipoPortatilUpdateComponent,
    EquipoPortatilDeleteDialogComponent,
    PortausuarioGuardadoeDialogComponent,
    PortausuarioUpdateComponent
  ],
  entryComponents: [EquipoPortatilDeleteDialogComponent]
})
export class ProquiEquiposVentaEquipoPortatilModule {}
