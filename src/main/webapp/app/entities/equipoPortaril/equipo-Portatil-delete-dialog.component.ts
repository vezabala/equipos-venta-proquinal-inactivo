import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoPortatilService } from './equipoPortatil.service';

@Component({
  templateUrl: './equipo-Portatil-delete-dialog.component.html'
})
export class EquipoPortatilDeleteDialogComponent {
  equipo?: IEquipo;

  constructor(
    protected equipoService: EquipoPortatilService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.equipoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('equipoListModification');
      this.activeModal.close();
    });
  }
}
