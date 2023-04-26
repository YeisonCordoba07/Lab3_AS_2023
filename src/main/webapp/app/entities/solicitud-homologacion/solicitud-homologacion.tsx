import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISolicitudHomologacion } from 'app/shared/model/solicitud-homologacion.model';
import { getEntities } from './solicitud-homologacion.reducer';

export const SolicitudHomologacion = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const solicitudHomologacionList = useAppSelector(state => state.solicitudHomologacion.entities);
  const loading = useAppSelector(state => state.solicitudHomologacion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="solicitud-homologacion-heading" data-cy="SolicitudHomologacionHeading">
        <Translate contentKey="proyectoh4App.solicitudHomologacion.home.title">Solicitud Homologacions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.solicitudHomologacion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/solicitud-homologacion/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.solicitudHomologacion.home.createLabel">Create new Solicitud Homologacion</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {solicitudHomologacionList && solicitudHomologacionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.idSolicitud">Id Solicitud</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.stateSolicitud">State Solicitud</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.codigoPrograma">Codigo Programa</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.fechaSolicitud">Fecha Solicitud</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.comentario">Comentario</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.programaAcademico">Programa Academico</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.solicitudHomologacion.estadoSolicitud">Estado Solicitud</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {solicitudHomologacionList.map((solicitudHomologacion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/solicitud-homologacion/${solicitudHomologacion.id}`} color="link" size="sm">
                      {solicitudHomologacion.id}
                    </Button>
                  </td>
                  <td>{solicitudHomologacion.idSolicitud}</td>
                  <td>{solicitudHomologacion.stateSolicitud}</td>
                  <td>{solicitudHomologacion.codigoPrograma}</td>
                  <td>
                    {solicitudHomologacion.fechaSolicitud ? (
                      <TextFormat type="date" value={solicitudHomologacion.fechaSolicitud} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{solicitudHomologacion.comentario}</td>
                  <td>
                    {solicitudHomologacion.programaAcademico ? (
                      <Link to={`/programa-academico/${solicitudHomologacion.programaAcademico.id}`}>
                        {solicitudHomologacion.programaAcademico.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {solicitudHomologacion.estadoSolicitud ? (
                      <Link to={`/estado-solicitud/${solicitudHomologacion.estadoSolicitud.id}`}>
                        {solicitudHomologacion.estadoSolicitud.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/solicitud-homologacion/${solicitudHomologacion.id}`}
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
                        to={`/solicitud-homologacion/${solicitudHomologacion.id}/edit`}
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
                        to={`/solicitud-homologacion/${solicitudHomologacion.id}/delete`}
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
              <Translate contentKey="proyectoh4App.solicitudHomologacion.home.notFound">No Solicitud Homologacions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SolicitudHomologacion;
