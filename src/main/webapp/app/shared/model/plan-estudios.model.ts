import dayjs from 'dayjs';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { IProgramaAcademico } from 'app/shared/model/programa-academico.model';

export interface IPlanEstudios {
  id?: number;
  version?: string | null;
  codigoPrograma?: number | null;
  fechaAprobacion?: string | null;
  estudiantes?: IEstudiante[] | null;
  programaAcademico?: IProgramaAcademico | null;
}

export const defaultValue: Readonly<IPlanEstudios> = {};
