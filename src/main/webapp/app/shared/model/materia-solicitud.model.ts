import { IMateria } from 'app/shared/model/materia.model';
import { ISolicitudHomologacion } from 'app/shared/model/solicitud-homologacion.model';
import { IEstudiante } from 'app/shared/model/estudiante.model';

export interface IMateriaSolicitud {
  id?: number;
  idSolicitud?: number | null;
  codigoMateria?: number | null;
  idSemestrePasada?: number | null;
  notaDefinitiva?: number | null;
  cedulaEstufiante?: string | null;
  materia?: IMateria | null;
  solicitudHomologacion?: ISolicitudHomologacion | null;
  estudiante?: IEstudiante | null;
}

export const defaultValue: Readonly<IMateriaSolicitud> = {};
