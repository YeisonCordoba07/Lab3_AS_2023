import { IHistoriaAcademica } from 'app/shared/model/historia-academica.model';

export interface ISituacionAcademica {
  id?: number;
  idSituacionAcademica?: number | null;
  situationAcademica?: string | null;
  historiaAcademicas?: IHistoriaAcademica[] | null;
}

export const defaultValue: Readonly<ISituacionAcademica> = {};
