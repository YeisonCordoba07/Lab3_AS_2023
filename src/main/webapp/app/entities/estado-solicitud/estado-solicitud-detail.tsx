import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './estado-solicitud.reducer';

export const EstadoSolicitudDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const estadoSolicitudEntity = useAppSelector(state => state.estadoSolicitud.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="estadoSolicitudDetailsHeading">
          <Translate contentKey="proyectoh4App.estadoSolicitud.detail.title">EstadoSolicitud</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{estadoSolicitudEntity.id}</dd>
          <dt>
            <span id="idEstadoSolicitud">
              <Translate contentKey="proyectoh4App.estadoSolicitud.idEstadoSolicitud">Id Estado Solicitud</Translate>
            </span>
          </dt>
          <dd>{estadoSolicitudEntity.idEstadoSolicitud}</dd>
          <dt>
            <span id="stateSolicitud">
              <Translate contentKey="proyectoh4App.estadoSolicitud.stateSolicitud">State Solicitud</Translate>
            </span>
          </dt>
          <dd>{estadoSolicitudEntity.stateSolicitud}</dd>
        </dl>
        <Button tag={Link} to="/estado-solicitud" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/estado-solicitud/${estadoSolicitudEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EstadoSolicitudDetail;
