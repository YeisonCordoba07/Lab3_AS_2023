import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MateriaSolicitud from './materia-solicitud';
import MateriaSolicitudDetail from './materia-solicitud-detail';
import MateriaSolicitudUpdate from './materia-solicitud-update';
import MateriaSolicitudDeleteDialog from './materia-solicitud-delete-dialog';

const MateriaSolicitudRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MateriaSolicitud />} />
    <Route path="new" element={<MateriaSolicitudUpdate />} />
    <Route path=":id">
      <Route index element={<MateriaSolicitudDetail />} />
      <Route path="edit" element={<MateriaSolicitudUpdate />} />
      <Route path="delete" element={<MateriaSolicitudDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MateriaSolicitudRoutes;
