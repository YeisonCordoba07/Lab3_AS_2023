import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SolicitudHomologacion from './solicitud-homologacion';
import SolicitudHomologacionDetail from './solicitud-homologacion-detail';
import SolicitudHomologacionUpdate from './solicitud-homologacion-update';
import SolicitudHomologacionDeleteDialog from './solicitud-homologacion-delete-dialog';

const SolicitudHomologacionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SolicitudHomologacion />} />
    <Route path="new" element={<SolicitudHomologacionUpdate />} />
    <Route path=":id">
      <Route index element={<SolicitudHomologacionDetail />} />
      <Route path="edit" element={<SolicitudHomologacionUpdate />} />
      <Route path="delete" element={<SolicitudHomologacionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SolicitudHomologacionRoutes;
