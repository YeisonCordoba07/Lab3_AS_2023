import { IHistoriaAcademica } from 'app/shared/model/historia-academica.model';

export interface ITercio {
  id?: number;
  idTercio?: number | null;
  tercioDescription?: string | null;
  historiaAcademicas?: IHistoriaAcademica[] | null;
}

export const defaultValue: Readonly<ITercio> = {};
