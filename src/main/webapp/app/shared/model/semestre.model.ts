import dayjs from 'dayjs';
import { IMateriaSemestre } from 'app/shared/model/materia-semestre.model';
import { IHistoriaAcademica } from 'app/shared/model/historia-academica.model';
import { ITipoSemestre } from 'app/shared/model/tipo-semestre.model';
import { IEstadoSemestre } from 'app/shared/model/estado-semestre.model';

export interface ISemestre {
  id?: number;
  idSemestre?: number | null;
  fechaInicio?: string | null;
  fechaTerminacion?: string | null;
  typeSemestre?: number | null;
  stateSemestre?: number | null;
  materiaSemestres?: IMateriaSemestre[] | null;
  historiaAcademicas?: IHistoriaAcademica[] | null;
  tipoSemestre?: ITipoSemestre | null;
  estadoSemestre?: IEstadoSemestre | null;
}

export const defaultValue: Readonly<ISemestre> = {};
