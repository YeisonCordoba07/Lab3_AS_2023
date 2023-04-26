import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRelacion } from 'app/shared/model/relacion.model';
import { getEntities } from './relacion.reducer';

export const Relacion = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const relacionList = useAppSelector(state => state.relacion.entities);
  const loading = useAppSelector(state => state.relacion.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="relacion-heading" data-cy="RelacionHeading">
        <Translate contentKey="proyectoh4App.relacion.home.title">Relacions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.relacion.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/relacion/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.relacion.home.createLabel">Create new Relacion</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {relacionList && relacionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.relacion.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.relacion.codigoMateria">Codigo Materia</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.relacion.codigoMateriaRelacionada">Codigo Materia Relacionada</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.relacion.tipoRelacion">Tipo Relacion</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.relacion.materia">Materia</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {relacionList.map((relacion, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/relacion/${relacion.id}`} color="link" size="sm">
                      {relacion.id}
                    </Button>
                  </td>
                  <td>{relacion.codigoMateria}</td>
                  <td>{relacion.codigoMateriaRelacionada}</td>
                  <td>{relacion.tipoRelacion}</td>
                  <td>{relacion.materia ? <Link to={`/materia/${relacion.materia.id}`}>{relacion.materia.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/relacion/${relacion.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/relacion/${relacion.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/relacion/${relacion.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="proyectoh4App.relacion.home.notFound">No Relacions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Relacion;
