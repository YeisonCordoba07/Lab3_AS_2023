import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstadoSolicitud } from 'app/shared/model/estado-solicitud.model';
import { getEntities } from './estado-solicitud.reducer';

export const EstadoSolicitud = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const estadoSolicitudList = useAppSelector(state => state.estadoSolicitud.entities);
  const loading = useAppSelector(state => state.estadoSolicitud.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="estado-solicitud-heading" data-cy="EstadoSolicitudHeading">
        <Translate contentKey="proyectoh4App.estadoSolicitud.home.title">Estado Solicituds</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.estadoSolicitud.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/estado-solicitud/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.estadoSolicitud.home.createLabel">Create new Estado Solicitud</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {estadoSolicitudList && estadoSolicitudList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.estadoSolicitud.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.estadoSolicitud.idEstadoSolicitud">Id Estado Solicitud</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.estadoSolicitud.stateSolicitud">State Solicitud</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {estadoSolicitudList.map((estadoSolicitud, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/estado-solicitud/${estadoSolicitud.id}`} color="link" size="sm">
                      {estadoSolicitud.id}
                    </Button>
                  </td>
                  <td>{estadoSolicitud.idEstadoSolicitud}</td>
                  <td>{estadoSolicitud.stateSolicitud}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/estado-solicitud/${estadoSolicitud.id}`}
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
                        to={`/estado-solicitud/${estadoSolicitud.id}/edit`}
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
                        to={`/estado-solicitud/${estadoSolicitud.id}/delete`}
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
              <Translate contentKey="proyectoh4App.estadoSolicitud.home.notFound">No Estado Solicituds found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EstadoSolicitud;
