import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './situacion-academica.reducer';

export const SituacionAcademicaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const situacionAcademicaEntity = useAppSelector(state => state.situacionAcademica.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="situacionAcademicaDetailsHeading">
          <Translate contentKey="proyectoh4App.situacionAcademica.detail.title">SituacionAcademica</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{situacionAcademicaEntity.id}</dd>
          <dt>
            <span id="idSituacionAcademica">
              <Translate contentKey="proyectoh4App.situacionAcademica.idSituacionAcademica">Id Situacion Academica</Translate>
            </span>
          </dt>
          <dd>{situacionAcademicaEntity.idSituacionAcademica}</dd>
          <dt>
            <span id="situationAcademica">
              <Translate contentKey="proyectoh4App.situacionAcademica.situationAcademica">Situation Academica</Translate>
            </span>
          </dt>
          <dd>{situacionAcademicaEntity.situationAcademica}</dd>
        </dl>
        <Button tag={Link} to="/situacion-academica" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/situacion-academica/${situacionAcademicaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SituacionAcademicaDetail;
