import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Relacion from './relacion';
import RelacionDetail from './relacion-detail';
import RelacionUpdate from './relacion-update';
import RelacionDeleteDialog from './relacion-delete-dialog';

const RelacionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Relacion />} />
    <Route path="new" element={<RelacionUpdate />} />
    <Route path=":id">
      <Route index element={<RelacionDetail />} />
      <Route path="edit" element={<RelacionUpdate />} />
      <Route path="delete" element={<RelacionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RelacionRoutes;
