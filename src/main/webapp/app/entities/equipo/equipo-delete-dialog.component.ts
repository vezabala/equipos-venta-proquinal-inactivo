import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoService } from './equipo.service';

@Component({
  templateUrl: './equipo-delete-dialog.component.html'
})
export class EquipoDeleteDialogComponent {
  equipo?: IEquipo;

  constructor(protected equipoService: EquipoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

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
