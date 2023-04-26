import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMateriaSemestre } from 'app/shared/model/materia-semestre.model';
import { getEntities } from './materia-semestre.reducer';

export const MateriaSemestre = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const materiaSemestreList = useAppSelector(state => state.materiaSemestre.entities);
  const loading = useAppSelector(state => state.materiaSemestre.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="materia-semestre-heading" data-cy="MateriaSemestreHeading">
        <Translate contentKey="proyectoh4App.materiaSemestre.home.title">Materia Semestres</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.materiaSemestre.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/materia-semestre/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.materiaSemestre.home.createLabel">Create new Materia Semestre</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {materiaSemestreList && materiaSemestreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.idMateriaSemestre">Id Materia Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.cedulaEstudiante">Cedula Estudiante</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.idSemestre">Id Semestre</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.codigoMateria">Codigo Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.notaDefinitiva">Nota Definitiva</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.materia">Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.estudiante">Estudiante</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.materiaSemestre.semestre">Semestre</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materiaSemestreList.map((materiaSemestre, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/materia-semestre/${materiaSemestre.id}`} color="link" size="sm">
                      {materiaSemestre.id}
                    </Button>
                  </td>
                  <td>{materiaSemestre.idMateriaSemestre}</td>
                  <td>{materiaSemestre.cedulaEstudiante}</td>
                  <td>{materiaSemestre.idSemestre}</td>
                  <td>{materiaSemestre.codigoMateria}</td>
                  <td>{materiaSemestre.notaDefinitiva}</td>
                  <td>
                    {materiaSemestre.materia ? <Link to={`/materia/${materiaSemestre.materia.id}`}>{materiaSemestre.materia.id}</Link> : ''}
                  </td>
                  <td>
                    {materiaSemestre.estudiante ? (
                      <Link to={`/estudiante/${materiaSemestre.estudiante.id}`}>{materiaSemestre.estudiante.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {materiaSemestre.semestre ? (
                      <Link to={`/semestre/${materiaSemestre.semestre.id}`}>{materiaSemestre.semestre.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/materia-semestre/${materiaSemestre.id}`}
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
                        to={`/materia-semestre/${materiaSemestre.id}/edit`}
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
                        to={`/materia-semestre/${materiaSemestre.id}/delete`}
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
              <Translate contentKey="proyectoh4App.materiaSemestre.home.notFound">No Materia Semestres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MateriaSemestre;
