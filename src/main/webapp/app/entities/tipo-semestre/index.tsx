import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TipoSemestre from './tipo-semestre';
import TipoSemestreDetail from './tipo-semestre-detail';
import TipoSemestreUpdate from './tipo-semestre-update';
import TipoSemestreDeleteDialog from './tipo-semestre-delete-dialog';

const TipoSemestreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TipoSemestre />} />
    <Route path="new" element={<TipoSemestreUpdate />} />
    <Route path=":id">
      <Route index element={<TipoSemestreDetail />} />
      <Route path="edit" element={<TipoSemestreUpdate />} />
      <Route path="delete" element={<TipoSemestreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TipoSemestreRoutes;
