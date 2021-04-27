import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsuario } from 'app/shared/model/usuario.model';
import { BusquedaUsuario } from 'app/entities/model/busquedaUsuario';

type EntityResponseType = HttpResponse<IUsuario>;
type EntityArrayResponseType = HttpResponse<IUsuario[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  public resourceUrl = SERVER_API_URL + 'api/usuarios';
  public equipoUrl = SERVER_API_URL + 'api/equipos';

  constructor(protected http: HttpClient) {}

  create(usuario: IUsuario): Observable<EntityResponseType> {
    return this.http.post<IUsuario>(this.resourceUrl, usuario, { observe: 'response' });
  }

  update(usuario: IUsuario): Observable<EntityResponseType> {
    return this.http.put<IUsuario>(this.resourceUrl, usuario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUsuario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  equipos(): Observable<any[]> {
    return this.http.get<any[]>(this.equipoUrl + '/list');
  }

  usuarios(busqueda: BusquedaUsuario): Observable<any[]> {
    return this.http.post<any[]>(this.resourceUrl + '/list', busqueda);
  }
}
