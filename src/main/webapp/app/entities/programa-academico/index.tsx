import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ProgramaAcademico from './programa-academico';
import ProgramaAcademicoDetail from './programa-academico-detail';
import ProgramaAcademicoUpdate from './programa-academico-update';
import ProgramaAcademicoDeleteDialog from './programa-academico-delete-dialog';

const ProgramaAcademicoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ProgramaAcademico />} />
    <Route path="new" element={<ProgramaAcademicoUpdate />} />
    <Route path=":id">
      <Route index element={<ProgramaAcademicoDetail />} />
      <Route path="edit" element={<ProgramaAcademicoUpdate />} />
      <Route path="delete" element={<ProgramaAcademicoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProgramaAcademicoRoutes;
