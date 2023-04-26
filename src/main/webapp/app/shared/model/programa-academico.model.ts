import dayjs from 'dayjs';
import { ISolicitudHomologacion } from 'app/shared/model/solicitud-homologacion.model';
import { IPlanEstudios } from 'app/shared/model/plan-estudios.model';
import { IEstudiante } from 'app/shared/model/estudiante.model';

export interface IProgramaAcademico {
  id?: number;
  codigoPrograma?: number | null;
  nombrePrograma?: string | null;
  duracion?: number | null;
  vigencia?: string | null;
  solicitudHomologacions?: ISolicitudHomologacion[] | null;
  planEstudios?: IPlanEstudios[] | null;
  estudiantes?: IEstudiante[] | null;
}

export const defaultValue: Readonly<IProgramaAcademico> = {};
