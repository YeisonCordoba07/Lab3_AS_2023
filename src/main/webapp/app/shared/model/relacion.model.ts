import { IMateria } from 'app/shared/model/materia.model';

export interface IRelacion {
  id?: number;
  codigoMateria?: number | null;
  codigoMateriaRelacionada?: number | null;
  tipoRelacion?: string | null;
  materia?: IMateria | null;
}

export const defaultValue: Readonly<IRelacion> = {};
