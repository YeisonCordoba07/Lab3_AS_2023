import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISemestre } from 'app/shared/model/semestre.model';
import { getEntities } from './semestre.reducer';

export const Semestre = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const semestreList = useAppSelector(state => state.semestre.entities);
  const loading = useAppSelector(state => state.semestre.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="semestre-heading" data-cy="SemestreHeading">
        <Translate contentKey="proyectoh4App.semestre.home.title">Semestres</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.semestre.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/semestre/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.semestre.home.createLabel">Create new Semestre</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {semestreList && semestreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.idSemestre">Id Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.fechaInicio">Fecha Inicio</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.fechaTerminacion">Fecha Terminacion</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.typeSemestre">Type Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.stateSemestre">State Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.tipoSemestre">Tipo Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.semestre.estadoSemestre">Estado Semestre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {semestreList.map((semestre, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/semestre/${semestre.id}`} color="link" size="sm">
                      {semestre.id}
                    </Button>
                  </td>
                  <td>{semestre.idSemestre}</td>
                  <td>
                    {semestre.fechaInicio ? <TextFormat type="date" value={semestre.fechaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {semestre.fechaTerminacion ? (
                      <TextFormat type="date" value={semestre.fechaTerminacion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{semestre.typeSemestre}</td>
                  <td>{semestre.stateSemestre}</td>
                  <td>
                    {semestre.tipoSemestre ? <Link to={`/tipo-semestre/${semestre.tipoSemestre.id}`}>{semestre.tipoSemestre.id}</Link> : ''}
                  </td>
                  <td>
                    {semestre.estadoSemestre ? (
                      <Link to={`/estado-semestre/${semestre.estadoSemestre.id}`}>{semestre.estadoSemestre.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/semestre/${semestre.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/semestre/${semestre.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/semestre/${semestre.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="proyectoh4App.semestre.home.notFound">No Semestres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Semestre;
