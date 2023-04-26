import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './solicitud-homologacion.reducer';

export const SolicitudHomologacionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const solicitudHomologacionEntity = useAppSelector(state => state.solicitudHomologacion.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="solicitudHomologacionDetailsHeading">
          <Translate contentKey="proyectoh4App.solicitudHomologacion.detail.title">SolicitudHomologacion</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{solicitudHomologacionEntity.id}</dd>
          <dt>
            <span id="idSolicitud">
              <Translate contentKey="proyectoh4App.solicitudHomologacion.idSolicitud">Id Solicitud</Translate>
            </span>
          </dt>
          <dd>{solicitudHomologacionEntity.idSolicitud}</dd>
          <dt>
            <span id="stateSolicitud">
              <Translate contentKey="proyectoh4App.solicitudHomologacion.stateSolicitud">State Solicitud</Translate>
            </span>
          </dt>
          <dd>{solicitudHomologacionEntity.stateSolicitud}</dd>
          <dt>
            <span id="codigoPrograma">
              <Translate contentKey="proyectoh4App.solicitudHomologacion.codigoPrograma">Codigo Programa</Translate>
            </span>
          </dt>
          <dd>{solicitudHomologacionEntity.codigoPrograma}</dd>
          <dt>
            <span id="fechaSolicitud">
              <Translate contentKey="proyectoh4App.solicitudHomologacion.fechaSolicitud">Fecha Solicitud</Translate>
            </span>
          </dt>
          <dd>
            {solicitudHomologacionEntity.fechaSolicitud ? (
              <TextFormat value={solicitudHomologacionEntity.fechaSolicitud} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="comentario">
              <Translate contentKey="proyectoh4App.solicitudHomologacion.comentario">Comentario</Translate>
            </span>
          </dt>
          <dd>{solicitudHomologacionEntity.comentario}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.solicitudHomologacion.programaAcademico">Programa Academico</Translate>
          </dt>
          <dd>{solicitudHomologacionEntity.programaAcademico ? solicitudHomologacionEntity.programaAcademico.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.solicitudHomologacion.estadoSolicitud">Estado Solicitud</Translate>
          </dt>
          <dd>{solicitudHomologacionEntity.estadoSolicitud ? solicitudHomologacionEntity.estadoSolicitud.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/solicitud-homologacion" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/solicitud-homologacion/${solicitudHomologacionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SolicitudHomologacionDetail;
