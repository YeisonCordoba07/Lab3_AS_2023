import { ISemestre } from 'app/shared/model/semestre.model';
import { IHistoriaAcademica } from 'app/shared/model/historia-academica.model';

export interface IEstadoSemestre {
  id?: number;
  idEstadoSemestre?: number | null;
  stateSemestre?: string | null;
  semestres?: ISemestre[] | null;
  historiaAcademicas?: IHistoriaAcademica[] | null;
}

export const defaultValue: Readonly<IEstadoSemestre> = {};
