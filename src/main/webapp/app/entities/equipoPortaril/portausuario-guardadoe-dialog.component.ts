import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsuario } from 'app/shared/model/usuario.model';

@Component({
  templateUrl: './portausuario-guardadoe-dialog.component.html'
})
export class PortausuarioGuardadoeDialogComponent {
  usuario?: IUsuario;

  constructor(public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    window.history.back();
    this.activeModal.dismiss();
  }
  previousState(): void {
    window.history.back();
    this.activeModal.dismiss();
  }
}
