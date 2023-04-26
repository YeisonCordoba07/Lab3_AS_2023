import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EstadoSolicitud from './estado-solicitud';
import EstadoSolicitudDetail from './estado-solicitud-detail';
import EstadoSolicitudUpdate from './estado-solicitud-update';
import EstadoSolicitudDeleteDialog from './estado-solicitud-delete-dialog';

const EstadoSolicitudRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EstadoSolicitud />} />
    <Route path="new" element={<EstadoSolicitudUpdate />} />
    <Route path=":id">
      <Route index element={<EstadoSolicitudDetail />} />
      <Route path="edit" element={<EstadoSolicitudUpdate />} />
      <Route path="delete" element={<EstadoSolicitudDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EstadoSolicitudRoutes;
