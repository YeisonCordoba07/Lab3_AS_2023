import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materia-solicitud.reducer';

export const MateriaSolicitudDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materiaSolicitudEntity = useAppSelector(state => state.materiaSolicitud.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materiaSolicitudDetailsHeading">
          <Translate contentKey="proyectoh4App.materiaSolicitud.detail.title">MateriaSolicitud</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materiaSolicitudEntity.id}</dd>
          <dt>
            <span id="idSolicitud">
              <Translate contentKey="proyectoh4App.materiaSolicitud.idSolicitud">Id Solicitud</Translate>
            </span>
          </dt>
          <dd>{materiaSolicitudEntity.idSolicitud}</dd>
          <dt>
            <span id="codigoMateria">
              <Translate contentKey="proyectoh4App.materiaSolicitud.codigoMateria">Codigo Materia</Translate>
            </span>
          </dt>
          <dd>{materiaSolicitudEntity.codigoMateria}</dd>
          <dt>
            <span id="idSemestrePasada">
              <Translate contentKey="proyectoh4App.materiaSolicitud.idSemestrePasada">Id Semestre Pasada</Translate>
            </span>
          </dt>
          <dd>{materiaSolicitudEntity.idSemestrePasada}</dd>
          <dt>
            <span id="notaDefinitiva">
              <Translate contentKey="proyectoh4App.materiaSolicitud.notaDefinitiva">Nota Definitiva</Translate>
            </span>
          </dt>
          <dd>{materiaSolicitudEntity.notaDefinitiva}</dd>
          <dt>
            <span id="cedulaEstufiante">
              <Translate contentKey="proyectoh4App.materiaSolicitud.cedulaEstufiante">Cedula Estufiante</Translate>
            </span>
          </dt>
          <dd>{materiaSolicitudEntity.cedulaEstufiante}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.materiaSolicitud.materia">Materia</Translate>
          </dt>
          <dd>{materiaSolicitudEntity.materia ? materiaSolicitudEntity.materia.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.materiaSolicitud.solicitudHomologacion">Solicitud Homologacion</Translate>
          </dt>
          <dd>{materiaSolicitudEntity.solicitudHomologacion ? materiaSolicitudEntity.solicitudHomologacion.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.materiaSolicitud.estudiante">Estudiante</Translate>
          </dt>
          <dd>{materiaSolicitudEntity.estudiante ? materiaSolicitudEntity.estudiante.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/materia-solicitud" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materia-solicitud/${materiaSolicitudEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MateriaSolicitudDetail;
