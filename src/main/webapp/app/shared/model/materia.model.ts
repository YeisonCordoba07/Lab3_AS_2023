import { IMateriaSolicitud } from 'app/shared/model/materia-solicitud.model';
import { IRelacion } from 'app/shared/model/relacion.model';
import { IMateriaSemestre } from 'app/shared/model/materia-semestre.model';

export interface IMateria {
  id?: number;
  codigoMateria?: number | null;
  nombreMateria?: string | null;
  creditos?: number | null;
  materiaSolicituds?: IMateriaSolicitud[] | null;
  relacions?: IRelacion[] | null;
  materiaSemestres?: IMateriaSemestre[] | null;
}

export const defaultValue: Readonly<IMateria> = {};
