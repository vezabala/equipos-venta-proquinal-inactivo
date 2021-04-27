import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEquipo } from 'app/shared/model/equipo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EquipoService } from './equipo.service';
import { EquipoDeleteDialogComponent } from './equipo-delete-dialog.component';
import { BusquedaEquipo } from 'app/entities/model/busquedaEquipo';

@Component({
  selector: 'jhi-equipo',
  templateUrl: './equipo.component.html'
})
export class EquipoComponent implements OnInit, OnDestroy {
  equipos?: IEquipo[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  busqueda: BusquedaEquipo = {
    activoFijo: '',
    tipo: ''
  };

  constructor(
    protected equipoService: EquipoService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.equipoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEquipo[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInEquipos();
  }

  listaEquipos(): void {
    this.equipoService.equipos(this.busqueda).subscribe(
      data => {
        this.equipos = data;
      },
      () => this.onError()
    );
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEquipo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInEquipos(): void {
    this.eventSubscriber = this.eventManager.subscribe('equipoListModification', () => this.loadPage());
  }

  delete(equipo: IEquipo): void {
    const modalRef = this.modalService.open(EquipoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.equipo = equipo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEquipo[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/equipo'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.equipos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  clearActivoFijo(): void {
    this.busqueda.activoFijo = '';
    this.listaEquipos();
  }
  clear(): void {
    this.busqueda.activoFijo = '';
    this.busqueda.tipo = '';
    this.listaEquipos();
  }
}
