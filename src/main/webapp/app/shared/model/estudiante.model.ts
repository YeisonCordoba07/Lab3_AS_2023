import dayjs from 'dayjs';
import { IMateriaSolicitud } from 'app/shared/model/materia-solicitud.model';
import { IMateriaSemestre } from 'app/shared/model/materia-semestre.model';
import { IHistoriaAcademica } from 'app/shared/model/historia-academica.model';
import { IProgramaAcademico } from 'app/shared/model/programa-academico.model';
import { IPlanEstudios } from 'app/shared/model/plan-estudios.model';

export interface IEstudiante {
  id?: number;
  cedula?: string | null;
  nombre?: string | null;
  apellido?: string | null;
  correoInstitucional?: string | null;
  correoPersonal?: string | null;
  celular?: string | null;
  estrato?: number | null;
  fechaIngreso?: string | null;
  version?: string | null;
  codigoPrograma?: number | null;
  materiaSolicituds?: IMateriaSolicitud[] | null;
  materiaSemestres?: IMateriaSemestre[] | null;
  historiaAcademicas?: IHistoriaAcademica[] | null;
  programaAcademico?: IProgramaAcademico | null;
  planEstudios?: IPlanEstudios | null;
}

export const defaultValue: Readonly<IEstudiante> = {};
