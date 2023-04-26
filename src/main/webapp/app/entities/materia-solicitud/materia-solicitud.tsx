import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMateriaSolicitud } from 'app/shared/model/materia-solicitud.model';
import { getEntities } from './materia-solicitud.reducer';

export const MateriaSolicitud = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const materiaSolicitudList = useAppSelector(state => state.materiaSolicitud.entities);
  const loading = useAppSelector(state => state.materiaSolicitud.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="materia-solicitud-heading" data-cy="MateriaSolicitudHeading">
        <Translate contentKey="proyectoh4App.materiaSolicitud.home.title">Materia Solicituds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.materiaSolicitud.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/materia-solicitud/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.materiaSolicitud.home.createLabel">Create new Materia Solicitud</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {materiaSolicitudList && materiaSolicitudList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.idSolicitud">Id Solicitud</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.codigoMateria">Codigo Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.idSemestrePasada">Id Semestre Pasada</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.notaDefinitiva">Nota Definitiva</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.cedulaEstufiante">Cedula Estufiante</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.materia">Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.solicitudHomologacion">Solicitud Homologacion</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSolicitud.estudiante">Estudiante</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materiaSolicitudList.map((materiaSolicitud, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/materia-solicitud/${materiaSolicitud.id}`} color="link" size="sm">
                      {materiaSolicitud.id}
                    </Button>
                  </td>
                  <td>{materiaSolicitud.idSolicitud}</td>
                  <td>{materiaSolicitud.codigoMateria}</td>
                  <td>{materiaSolicitud.idSemestrePasada}</td>
                  <td>{materiaSolicitud.notaDefinitiva}</td>
                  <td>{materiaSolicitud.cedulaEstufiante}</td>
                  <td>
                    {materiaSolicitud.materia ? (
                      <Link to={`/materia/${materiaSolicitud.materia.id}`}>{materiaSolicitud.materia.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {materiaSolicitud.solicitudHomologacion ? (
                      <Link to={`/solicitud-homologacion/${materiaSolicitud.solicitudHomologacion.id}`}>
                        {materiaSolicitud.solicitudHomologacion.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {materiaSolicitud.estudiante ? (
                      <Link to={`/estudiante/${materiaSolicitud.estudiante.id}`}>{materiaSolicitud.estudiante.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/materia-solicitud/${materiaSolicitud.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/materia-solicitud/${materiaSolicitud.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/materia-solicitud/${materiaSolicitud.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="proyectoh4App.materiaSolicitud.home.notFound">No Materia Solicituds found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MateriaSolicitud;
