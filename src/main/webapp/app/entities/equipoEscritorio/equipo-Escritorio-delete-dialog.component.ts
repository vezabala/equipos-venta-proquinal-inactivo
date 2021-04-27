import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoEscritorioService } from './equipoEscritorio.service';

@Component({
  templateUrl: './equipo-Escritorio-delete-dialog.component.html'
})
export class EquipoEscritorioDeleteDialogComponent {
  equipo?: IEquipo;

  constructor(
    protected equipoService: EquipoEscritorioService,
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
