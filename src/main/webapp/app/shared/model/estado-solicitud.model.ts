import { ISolicitudHomologacion } from 'app/shared/model/solicitud-homologacion.model';

export interface IEstadoSolicitud {
  id?: number;
  idEstadoSolicitud?: number | null;
  stateSolicitud?: string | null;
  solicitudHomologacions?: ISolicitudHomologacion[] | null;
}

export const defaultValue: Readonly<IEstadoSolicitud> = {};
