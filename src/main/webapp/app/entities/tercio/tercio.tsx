import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITercio } from 'app/shared/model/tercio.model';
import { getEntities } from './tercio.reducer';

export const Tercio = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const tercioList = useAppSelector(state => state.tercio.entities);
  const loading = useAppSelector(state => state.tercio.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="tercio-heading" data-cy="TercioHeading">
        <Translate contentKey="proyectoh4App.tercio.home.title">Tercios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="proyectoh4App.tercio.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/tercio/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="proyectoh4App.tercio.home.createLabel">Create new Tercio</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tercioList && tercioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="proyectoh4App.tercio.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.tercio.idTercio">Id Tercio</Translate>
                </th>
                <th>
                  <Translate contentKey="proyectoh4App.tercio.tercioDescription">Tercio Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tercioList.map((tercio, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tercio/${tercio.id}`} color="link" size="sm">
                      {tercio.id}
                    </Button>
                  </td>
                  <td>{tercio.idTercio}</td>
                  <td>{tercio.tercioDescription}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tercio/${tercio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tercio/${tercio.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/tercio/${tercio.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="proyectoh4App.tercio.home.notFound">No Tercios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Tercio;
