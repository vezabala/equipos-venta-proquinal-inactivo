export interface IUsuario {
  id?: number;
  numeroDocumento?: string;
  nombres?: string;
  apellidos?: string;
  correo?: string;
  area?: string;
  equipoActivoFijo?: string;
  equipoId?: number;
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public numeroDocumento?: string,
    public nombres?: string,
    public apellidos?: string,
    public correo?: string,
    public area?: string,
    public equipoActivoFijo?: string,
    public equipoId?: number
  ) {}
}
