import { IUsuario } from 'app/shared/model/usuario.model';
import { State } from 'app/shared/model/enumerations/state.model';

export interface IEquipo {
  id?: number;
  activoFijo?: string;
  marca?: string;
  procesador?: string;
  office?: string;
  sistemaOperativo?: string;
  discoDuro?: string;
  ram?: string;
  observaciones?: string;
  imgUrlContentType?: string;
  imgUrl?: any;
  tipo?: State;
  windowss?: string;
  precio?: string;
  usuarios?: IUsuario[];
}

export class Equipo implements IEquipo {
  constructor(
    public id?: number,
    public activoFijo?: string,
    public marca?: string,
    public procesador?: string,
    public office?: string,
    public sistemaOperativo?: string,
    public discoDuro?: string,
    public ram?: string,
    public observaciones?: string,
    public imgUrlContentType?: string,
    public imgUrl?: any,
    public tipo?: State,
    public windowss?: string,
    public precio?: string,
    public usuarios?: IUsuario[]
  ) {}
}
