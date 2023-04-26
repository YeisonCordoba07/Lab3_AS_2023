import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMateria } from 'app/shared/model/materia.model';
import { getEntities } from './materia.reducer';

export const Materia = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const materiaList = useAppSelector(state => state.materia.entities);
  const loading = useAppSelector(state => state.materia.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="materia-heading" data-cy="MateriaHeading">
        <Translate contentKey="proyectoh4App.materia.home.title">Materias</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.materia.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/materia/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.materia.home.createLabel">Create new Materia</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {materiaList && materiaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.materia.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materia.codigoMateria">Codigo Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materia.nombreMateria">Nombre Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materia.creditos">Creditos</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materiaList.map((materia, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/materia/${materia.id}`} color="link" size="sm">
                      {materia.id}
                    </Button>
                  </td>
                  <td>{materia.codigoMateria}</td>
                  <td>{materia.nombreMateria}</td>
                  <td>{materia.creditos}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/materia/${materia.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/materia/${materia.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/materia/${materia.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="proyectoh4App.materia.home.notFound">No Materias found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Materia;
