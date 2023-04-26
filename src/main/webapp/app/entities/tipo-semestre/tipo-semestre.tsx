import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoSemestre } from 'app/shared/model/tipo-semestre.model';
import { getEntities } from './tipo-semestre.reducer';

export const TipoSemestre = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const tipoSemestreList = useAppSelector(state => state.tipoSemestre.entities);
  const loading = useAppSelector(state => state.tipoSemestre.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="tipo-semestre-heading" data-cy="TipoSemestreHeading">
        <Translate contentKey="proyectoh4App.tipoSemestre.home.title">Tipo Semestres</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.tipoSemestre.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tipo-semestre/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.tipoSemestre.home.createLabel">Create new Tipo Semestre</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tipoSemestreList && tipoSemestreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.tipoSemestre.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.tipoSemestre.idTipoSemestre">Id Tipo Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.tipoSemestre.typeSemestre">Type Semestre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tipoSemestreList.map((tipoSemestre, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tipo-semestre/${tipoSemestre.id}`} color="link" size="sm">
                      {tipoSemestre.id}
                    </Button>
                  </td>
                  <td>{tipoSemestre.idTipoSemestre}</td>
                  <td>{tipoSemestre.typeSemestre}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tipo-semestre/${tipoSemestre.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tipo-semestre/${tipoSemestre.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tipo-semestre/${tipoSemestre.id}/delete`}
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
              <Translate contentKey="proyectoh4App.tipoSemestre.home.notFound">No Tipo Semestres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TipoSemestre;
