import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsuario } from 'app/shared/model/usuario.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UsuarioService } from './usuario.service';
import { UsuarioDeleteDialogComponent } from './usuario-delete-dialog.component';
import { BusquedaUsuario } from 'app/entities/model/busquedaUsuario';
import * as XLSX from 'xlsx';

@Component({
  selector: 'jhi-usuario',
  templateUrl: './usuario.component.html'
})
export class UsuarioComponent implements OnInit, OnDestroy {
  usuarios?: IUsuario[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  equipos: any[] = [];
  usuariosList: any[] = [];
  equipoElegido: any = null;

  busqueda: BusquedaUsuario = {
    numeroDocumento: '',
    equipo: ''
  };
  /*name of the excel-file which will be downloaded. */
  fileName = 'Usuarios.xlsx';

  constructor(
    protected usuarioService: UsuarioService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.usuarioService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IUsuario[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
      this.listaEquiposU();
      this.listaUsuarios();
    });
    this.registerChangeInUsuarios();
  }

  listaEquiposU(): void {
    this.usuarioService.equipos().subscribe(
      data => {
        this.equipos = data;
      },
      () => this.onError()
    );
  }

  listaUsuarios(): void {
    this.usuarioService.usuarios(this.busqueda).subscribe(
      data => {
        this.usuariosList = data;
      },
      () => this.onError()
    );
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsuario): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsuarios(): void {
    this.eventSubscriber = this.eventManager.subscribe('usuarioListModification', () => this.loadPage());
  }

  delete(usuario: IUsuario): void {
    const modalRef = this.modalService.open(UsuarioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usuario = usuario;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUsuario[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/usuario'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.usuarios = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  onChangeEsuario(): void {
    if (this.equipoElegido) {
      this.busqueda.equipo = this.equipoElegido.activoFijo;
    } else {
      this.busqueda.equipo = '';
    }
    this.listaUsuarios();
  }

  clearNumeroDocumento(): void {
    this.busqueda.numeroDocumento = '';
    this.listaUsuarios();
  }
  clear(): void {
    this.equipoElegido = null;
    this.busqueda.equipo = '';
    this.busqueda.numeroDocumento = '';
    this.listaUsuarios();
  }

  exportexcel(): void {
    const element = document.getElementById('excel-table');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

    XLSX.writeFile(wb, this.fileName);
  }
}
