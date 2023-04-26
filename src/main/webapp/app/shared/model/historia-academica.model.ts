import { IEstudiante } from 'app/shared/model/estudiante.model';
import { ISemestre } from 'app/shared/model/semestre.model';
import { ISituacionAcademica } from 'app/shared/model/situacion-academica.model';
import { ITercio } from 'app/shared/model/tercio.model';
import { IEstadoSemestre } from 'app/shared/model/estado-semestre.model';

export interface IHistoriaAcademica {
  id?: number;
  idHistoriaAcademica?: number | null;
  cedulaEstudiante?: string | null;
  idSemestre?: number | null;
  codigoPrograma?: number | null;
  promedioAcomulado?: number | null;
  promedioSemestre?: number | null;
  idTercio?: number | null;
  situationAcademica?: number | null;
  stateSemestre?: number | null;
  estudiante?: IEstudiante | null;
  semestre?: ISemestre | null;
  situacionAcademica?: ISituacionAcademica | null;
  tercio?: ITercio | null;
  estadoSemestre?: IEstadoSemestre | null;
}

export const defaultValue: Readonly<IHistoriaAcademica> = {};
