import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './semestre.reducer';

export const SemestreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const semestreEntity = useAppSelector(state => state.semestre.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="semestreDetailsHeading">
          <Translate contentKey="proyectoh4App.semestre.detail.title">Semestre</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{semestreEntity.id}</dd>
          <dt>
            <span id="idSemestre">
              <Translate contentKey="proyectoh4App.semestre.idSemestre">Id Semestre</Translate>
            </span>
          </dt>
          <dd>{semestreEntity.idSemestre}</dd>
          <dt>
            <span id="fechaInicio">
              <Translate contentKey="proyectoh4App.semestre.fechaInicio">Fecha Inicio</Translate>
            </span>
          </dt>
          <dd>
            {semestreEntity.fechaInicio ? (
              <TextFormat value={semestreEntity.fechaInicio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaTerminacion">
              <Translate contentKey="proyectoh4App.semestre.fechaTerminacion">Fecha Terminacion</Translate>
            </span>
          </dt>
          <dd>
            {semestreEntity.fechaTerminacion ? (
              <TextFormat value={semestreEntity.fechaTerminacion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="typeSemestre">
              <Translate contentKey="proyectoh4App.semestre.typeSemestre">Type Semestre</Translate>
            </span>
          </dt>
          <dd>{semestreEntity.typeSemestre}</dd>
          <dt>
            <span id="stateSemestre">
              <Translate contentKey="proyectoh4App.semestre.stateSemestre">State Semestre</Translate>
            </span>
          </dt>
          <dd>{semestreEntity.stateSemestre}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.semestre.tipoSemestre">Tipo Semestre</Translate>
          </dt>
          <dd>{semestreEntity.tipoSemestre ? semestreEntity.tipoSemestre.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.semestre.estadoSemestre">Estado Semestre</Translate>
          </dt>
          <dd>{semestreEntity.estadoSemestre ? semestreEntity.estadoSemestre.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/semestre" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/semestre/${semestreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SemestreDetail;
