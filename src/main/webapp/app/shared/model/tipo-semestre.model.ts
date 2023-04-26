import { ISemestre } from 'app/shared/model/semestre.model';

export interface ITipoSemestre {
  id?: number;
  idTipoSemestre?: number | null;
  typeSemestre?: string | null;
  semestres?: ISemestre[] | null;
}

export const defaultValue: Readonly<ITipoSemestre> = {};
