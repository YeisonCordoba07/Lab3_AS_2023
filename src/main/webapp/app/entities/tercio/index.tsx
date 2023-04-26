import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tercio from './tercio';
import TercioDetail from './tercio-detail';
import TercioUpdate from './tercio-update';
import TercioDeleteDialog from './tercio-delete-dialog';

const TercioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tercio />} />
    <Route path="new" element={<TercioUpdate />} />
    <Route path=":id">
      <Route index element={<TercioDetail />} />
      <Route path="edit" element={<TercioUpdate />} />
      <Route path="delete" element={<TercioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TercioRoutes;
