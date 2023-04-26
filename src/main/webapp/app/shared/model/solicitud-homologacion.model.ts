import dayjs from 'dayjs';
import { IMateriaSolicitud } from 'app/shared/model/materia-solicitud.model';
import { IProgramaAcademico } from 'app/shared/model/programa-academico.model';
import { IEstadoSolicitud } from 'app/shared/model/estado-solicitud.model';

export interface ISolicitudHomologacion {
  id?: number;
  idSolicitud?: number | null;
  stateSolicitud?: number | null;
  codigoPrograma?: number | null;
  fechaSolicitud?: string | null;
  comentario?: string | null;
  materiaSolicituds?: IMateriaSolicitud[] | null;
  programaAcademico?: IProgramaAcademico | null;
  estadoSolicitud?: IEstadoSolicitud | null;
}

export const defaultValue: Readonly<ISolicitudHomologacion> = {};
