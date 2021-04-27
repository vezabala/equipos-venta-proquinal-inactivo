import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEquipo, Equipo } from 'app/shared/model/equipo.model';
import { EquipoService } from './equipo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-equipo-update',
  templateUrl: './equipo-update.component.html'
})
export class EquipoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    activoFijo: [null, [Validators.required, Validators.maxLength(70)]],
    marca: [null, [Validators.required, Validators.maxLength(70)]],
    procesador: [null, [Validators.required, Validators.maxLength(200)]],
    office: [null, [Validators.required, Validators.maxLength(70)]],
    sistemaOperativo: [null, [Validators.required, Validators.maxLength(70)]],
    discoDuro: [null, [Validators.required, Validators.maxLength(70)]],
    ram: [null, [Validators.required, Validators.maxLength(70)]],
    observaciones: [null, [Validators.maxLength(200)]],
    imgUrl: [],
    imgUrlContentType: [],
    tipo: [null, [Validators.required]],
    windowss: [null, [Validators.required, Validators.maxLength(70)]],
    precio: [null, [Validators.required, Validators.maxLength(70)]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected equipoService: EquipoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipo }) => {
      this.updateForm(equipo);
    });
  }

  updateForm(equipo: IEquipo): void {
    this.editForm.patchValue({
      id: equipo.id,
      activoFijo: equipo.activoFijo,
      marca: equipo.marca,
      procesador: equipo.procesador,
      office: equipo.office,
      sistemaOperativo: equipo.sistemaOperativo,
      discoDuro: equipo.discoDuro,
      ram: equipo.ram,
      observaciones: equipo.observaciones,
      imgUrl: equipo.imgUrl,
      imgUrlContentType: equipo.imgUrlContentType,
      tipo: equipo.tipo,
      windowss: equipo.windowss,
      precio: equipo.precio
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('proquiEquiposVentaApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipo = this.createFromForm();
    if (equipo.id !== undefined) {
      this.subscribeToSaveResponse(this.equipoService.update(equipo));
    } else {
      this.subscribeToSaveResponse(this.equipoService.create(equipo));
    }
  }

  private createFromForm(): IEquipo {
    return {
      ...new Equipo(),
      id: this.editForm.get(['id'])!.value,
      activoFijo: this.editForm.get(['activoFijo'])!.value,
      marca: this.editForm.get(['marca'])!.value,
      procesador: this.editForm.get(['procesador'])!.value,
      office: this.editForm.get(['office'])!.value,
      sistemaOperativo: this.editForm.get(['sistemaOperativo'])!.value,
      discoDuro: this.editForm.get(['discoDuro'])!.value,
      ram: this.editForm.get(['ram'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      imgUrlContentType: this.editForm.get(['imgUrlContentType'])!.value,
      imgUrl: this.editForm.get(['imgUrl'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      windowss: this.editForm.get(['windowss'])!.value,
      precio: this.editForm.get(['precio'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
