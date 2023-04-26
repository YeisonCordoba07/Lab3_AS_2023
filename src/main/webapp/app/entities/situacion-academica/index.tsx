import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SituacionAcademica from './situacion-academica';
import SituacionAcademicaDetail from './situacion-academica-detail';
import SituacionAcademicaUpdate from './situacion-academica-update';
import SituacionAcademicaDeleteDialog from './situacion-academica-delete-dialog';

const SituacionAcademicaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SituacionAcademica />} />
    <Route path="new" element={<SituacionAcademicaUpdate />} />
    <Route path=":id">
      <Route index element={<SituacionAcademicaDetail />} />
      <Route path="edit" element={<SituacionAcademicaUpdate />} />
      <Route path="delete" element={<SituacionAcademicaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SituacionAcademicaRoutes;
