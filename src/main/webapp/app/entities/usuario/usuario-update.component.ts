import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUsuario, Usuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from './usuario.service';
import { IEquipo } from 'app/shared/model/equipo.model';
import { EquipoService } from 'app/entities/equipo/equipo.service';
import { UsuarioGuardadoeDialogComponent } from 'app/entities/usuario/usuario-guardadoe-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-usuario-update',
  templateUrl: './usuario-update.component.html'
})
export class UsuarioUpdateComponent implements OnInit {
  isSaving = false;
  equipos: IEquipo[] = [];

  editForm = this.fb.group({
    id: [],
    numeroDocumento: [null, [Validators.required, Validators.maxLength(255)]],
    nombres: [null, [Validators.required, Validators.maxLength(50)]],
    apellidos: [null, [Validators.required, Validators.maxLength(50)]],
    area: [null, [Validators.required, Validators.maxLength(50)]],
    equipoId: [null, Validators.required]
  });

  constructor(
    protected usuarioService: UsuarioService,
    protected equipoService: EquipoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuario }) => {
      this.updateForm(usuario);

      this.equipoService.query().subscribe((res: HttpResponse<IEquipo[]>) => (this.equipos = res.body || []));
    });
  }

  updateForm(usuario: IUsuario): void {
    this.editForm.patchValue({
      id: usuario.id,
      numeroDocumento: usuario.numeroDocumento,
      nombres: usuario.nombres,
      apellidos: usuario.apellidos,
      area: usuario.area,
      equipoId: usuario.equipoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  ifsave(): void {
    this.modalService.open(UsuarioGuardadoeDialogComponent, { size: 'lg', backdrop: 'static' });
  }

  save(): void {
    this.isSaving = true;
    const usuario = this.createFromForm();
    if (usuario.id !== undefined) {
      this.subscribeToSaveResponse(this.usuarioService.update(usuario));
    } else {
      this.subscribeToSaveResponse(this.usuarioService.create(usuario));
    }
  }

  private createFromForm(): IUsuario {
    return {
      ...new Usuario(),
      id: this.editForm.get(['id'])!.value,
      numeroDocumento: this.editForm.get(['numeroDocumento'])!.value,
      nombres: this.editForm.get(['nombres'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      area: this.editForm.get(['area'])!.value,
      equipoId: this.editForm.get(['equipoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuario>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.ifsave();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEquipo): any {
    return item.id;
  }
}
