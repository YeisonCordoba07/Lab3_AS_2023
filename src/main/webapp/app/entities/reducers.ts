import materiaSolicitud from 'app/entities/materia-solicitud/materia-solicitud.reducer';
import solicitudHomologacion from 'app/entities/solicitud-homologacion/solicitud-homologacion.reducer';
import materia from 'app/entities/materia/materia.reducer';
import relacion from 'app/entities/relacion/relacion.reducer';
import historiaAcademica from 'app/entities/historia-academica/historia-academica.reducer';
import estudiante from 'app/entities/estudiante/estudiante.reducer';
import programaAcademico from 'app/entities/programa-academico/programa-academico.reducer';
import planEstudios from 'app/entities/plan-estudios/plan-estudios.reducer';
import materiaSemestre from 'app/entities/materia-semestre/materia-semestre.reducer';
import semestre from 'app/entities/semestre/semestre.reducer';
import estadoSolicitud from 'app/entities/estado-solicitud/estado-solicitud.reducer';
import estadoSemestre from 'app/entities/estado-semestre/estado-semestre.reducer';
import tercio from 'app/entities/tercio/tercio.reducer';
import situacionAcademica from 'app/entities/situacion-academica/situacion-academica.reducer';
import tipoSemestre from 'app/entities/tipo-semestre/tipo-semestre.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  materiaSolicitud,
  solicitudHomologacion,
  materia,
  relacion,
  historiaAcademica,
  estudiante,
  programaAcademico,
  planEstudios,
  materiaSemestre,
  semestre,
  estadoSolicitud,
  estadoSemestre,
  tercio,
  situacionAcademica,
  tipoSemestre,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
