import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PlanEstudios from './plan-estudios';
import PlanEstudiosDetail from './plan-estudios-detail';
import PlanEstudiosUpdate from './plan-estudios-update';
import PlanEstudiosDeleteDialog from './plan-estudios-delete-dialog';

const PlanEstudiosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PlanEstudios />} />
    <Route path="new" element={<PlanEstudiosUpdate />} />
    <Route path=":id">
      <Route index element={<PlanEstudiosDetail />} />
      <Route path="edit" element={<PlanEstudiosUpdate />} />
      <Route path="delete" element={<PlanEstudiosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlanEstudiosRoutes;
