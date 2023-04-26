import { IMateria } from 'app/shared/model/materia.model';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { ISemestre } from 'app/shared/model/semestre.model';

export interface IMateriaSemestre {
  id?: number;
  idMateriaSemestre?: number | null;
  cedulaEstudiante?: string | null;
  idSemestre?: number | null;
  codigoMateria?: number | null;
  notaDefinitiva?: number | null;
  materia?: IMateria | null;
  estudiante?: IEstudiante | null;
  semestre?: ISemestre | null;
}

export const defaultValue: Readonly<IMateriaSemestre> = {};
