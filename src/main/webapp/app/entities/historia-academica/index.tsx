import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HistoriaAcademica from './historia-academica';
import HistoriaAcademicaDetail from './historia-academica-detail';
import HistoriaAcademicaUpdate from './historia-academica-update';
import HistoriaAcademicaDeleteDialog from './historia-academica-delete-dialog';

const HistoriaAcademicaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HistoriaAcademica />} />
    <Route path="new" element={<HistoriaAcademicaUpdate />} />
    <Route path=":id">
      <Route index element={<HistoriaAcademicaDetail />} />
      <Route path="edit" element={<HistoriaAcademicaUpdate />} />
      <Route path="delete" element={<HistoriaAcademicaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HistoriaAcademicaRoutes;
