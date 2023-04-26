import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlanEstudios } from 'app/shared/model/plan-estudios.model';
import { getEntities } from './plan-estudios.reducer';

export const PlanEstudios = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const planEstudiosList = useAppSelector(state => state.planEstudios.entities);
  const loading = useAppSelector(state => state.planEstudios.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="plan-estudios-heading" data-cy="PlanEstudiosHeading">
        <Translate contentKey="proyectoh4App.planEstudios.home.title">Plan Estudios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.planEstudios.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/plan-estudios/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.planEstudios.home.createLabel">Create new Plan Estudios</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {planEstudiosList && planEstudiosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.planEstudios.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.planEstudios.version">Version</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.planEstudios.codigoPrograma">Codigo Programa</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.planEstudios.fechaAprobacion">Fecha Aprobacion</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.planEstudios.programaAcademico">Programa Academico</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {planEstudiosList.map((planEstudios, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/plan-estudios/${planEstudios.id}`} color="link" size="sm">
                      {planEstudios.id}
                    </Button>
                  </td>
                  <td>{planEstudios.version}</td>
                  <td>{planEstudios.codigoPrograma}</td>
                  <td>
                    {planEstudios.fechaAprobacion ? (
                      <TextFormat type="date" value={planEstudios.fechaAprobacion} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {planEstudios.programaAcademico ? (
                      <Link to={`/programa-academico/${planEstudios.programaAcademico.id}`}>{planEstudios.programaAcademico.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/plan-estudios/${planEstudios.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/plan-estudios/${planEstudios.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/plan-estudios/${planEstudios.id}/delete`}
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
              <Translate contentKey="proyectoh4App.planEstudios.home.notFound">No Plan Estudios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PlanEstudios;
