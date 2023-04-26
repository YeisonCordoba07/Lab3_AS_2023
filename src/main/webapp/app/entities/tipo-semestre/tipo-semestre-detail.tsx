import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tipo-semestre.reducer';

export const TipoSemestreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tipoSemestreEntity = useAppSelector(state => state.tipoSemestre.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tipoSemestreDetailsHeading">
          <Translate contentKey="proyectoh4App.tipoSemestre.detail.title">TipoSemestre</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tipoSemestreEntity.id}</dd>
          <dt>
            <span id="idTipoSemestre">
              <Translate contentKey="proyectoh4App.tipoSemestre.idTipoSemestre">Id Tipo Semestre</Translate>
            </span>
          </dt>
          <dd>{tipoSemestreEntity.idTipoSemestre}</dd>
          <dt>
            <span id="typeSemestre">
              <Translate contentKey="proyectoh4App.tipoSemestre.typeSemestre">Type Semestre</Translate>
            </span>
          </dt>
          <dd>{tipoSemestreEntity.typeSemestre}</dd>
        </dl>
        <Button tag={Link} to="/tipo-semestre" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tipo-semestre/${tipoSemestreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TipoSemestreDetail;
