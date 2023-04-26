import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EstadoSemestre from './estado-semestre';
import EstadoSemestreDetail from './estado-semestre-detail';
import EstadoSemestreUpdate from './estado-semestre-update';
import EstadoSemestreDeleteDialog from './estado-semestre-delete-dialog';

const EstadoSemestreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EstadoSemestre />} />
    <Route path="new" element={<EstadoSemestreUpdate />} />
    <Route path=":id">
      <Route index element={<EstadoSemestreDetail />} />
      <Route path="edit" element={<EstadoSemestreUpdate />} />
      <Route path="delete" element={<EstadoSemestreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EstadoSemestreRoutes;
