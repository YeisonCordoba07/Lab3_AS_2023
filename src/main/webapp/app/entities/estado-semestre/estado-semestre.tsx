import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstadoSemestre } from 'app/shared/model/estado-semestre.model';
import { getEntities } from './estado-semestre.reducer';

export const EstadoSemestre = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const estadoSemestreList = useAppSelector(state => state.estadoSemestre.entities);
  const loading = useAppSelector(state => state.estadoSemestre.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="estado-semestre-heading" data-cy="EstadoSemestreHeading">
        <Translate contentKey="proyectoh4App.estadoSemestre.home.title">Estado Semestres</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.estadoSemestre.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/estado-semestre/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.estadoSemestre.home.createLabel">Create new Estado Semestre</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {estadoSemestreList && estadoSemestreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.estadoSemestre.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.estadoSemestre.idEstadoSemestre">Id Estado Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.estadoSemestre.stateSemestre">State Semestre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {estadoSemestreList.map((estadoSemestre, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/estado-semestre/${estadoSemestre.id}`} color="link" size="sm">
                      {estadoSemestre.id}
                    </Button>
                  </td>
                  <td>{estadoSemestre.idEstadoSemestre}</td>
                  <td>{estadoSemestre.stateSemestre}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/estado-semestre/${estadoSemestre.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/estado-semestre/${estadoSemestre.id}/edit`}
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
                        to={`/estado-semestre/${estadoSemestre.id}/delete`}
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
              <Translate contentKey="proyectoh4App.estadoSemestre.home.notFound">No Estado Semestres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EstadoSemestre;
