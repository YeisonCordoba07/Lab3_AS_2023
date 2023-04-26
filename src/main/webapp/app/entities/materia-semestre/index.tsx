import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MateriaSemestre from './materia-semestre';
import MateriaSemestreDetail from './materia-semestre-detail';
import MateriaSemestreUpdate from './materia-semestre-update';
import MateriaSemestreDeleteDialog from './materia-semestre-delete-dialog';

const MateriaSemestreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MateriaSemestre />} />
    <Route path="new" element={<MateriaSemestreUpdate />} />
    <Route path=":id">
      <Route index element={<MateriaSemestreDetail />} />
      <Route path="edit" element={<MateriaSemestreUpdate />} />
      <Route path="delete" element={<MateriaSemestreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MateriaSemestreRoutes;
