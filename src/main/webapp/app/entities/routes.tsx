import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MateriaSolicitud from './materia-solicitud';
import SolicitudHomologacion from './solicitud-homologacion';
import Materia from './materia';
import Relacion from './relacion';
import HistoriaAcademica from './historia-academica';
import Estudiante from './estudiante';
import ProgramaAcademico from './programa-academico';
import PlanEstudios from './plan-estudios';
import MateriaSemestre from './materia-semestre';
import Semestre from './semestre';
import EstadoSolicitud from './estado-solicitud';
import EstadoSemestre from './estado-semestre';
import Tercio from './tercio';
import SituacionAcademica from './situacion-academica';
import TipoSemestre from './tipo-semestre';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="materia-solicitud/*" element={<MateriaSolicitud />} />
        <Route path="solicitud-homologacion/*" element={<SolicitudHomologacion />} />
        <Route path="materia/*" element={<Materia />} />
        <Route path="relacion/*" element={<Relacion />} />
        <Route path="historia-academica/*" element={<HistoriaAcademica />} />
        <Route path="estudiante/*" element={<Estudiante />} />
        <Route path="programa-academico/*" element={<ProgramaAcademico />} />
        <Route path="plan-estudios/*" element={<PlanEstudios />} />
        <Route path="materia-semestre/*" element={<MateriaSemestre />} />
        <Route path="semestre/*" element={<Semestre />} />
        <Route path="estado-solicitud/*" element={<EstadoSolicitud />} />
        <Route path="estado-semestre/*" element={<EstadoSemestre />} />
        <Route path="tercio/*" element={<Tercio />} />
        <Route path="situacion-academica/*" element={<SituacionAcademica />} />
        <Route path="tipo-semestre/*" element={<TipoSemestre />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
