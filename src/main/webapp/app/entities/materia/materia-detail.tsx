import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materia.reducer';

export const MateriaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materiaEntity = useAppSelector(state => state.materia.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materiaDetailsHeading">
          <Translate contentKey="proyectoh4App.materia.detail.title">Materia</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.id}</dd>
          <dt>
            <span id="codigoMateria">
              <Translate contentKey="proyectoh4App.materia.codigoMateria">Codigo Materia</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.codigoMateria}</dd>
          <dt>
            <span id="nombreMateria">
              <Translate contentKey="proyectoh4App.materia.nombreMateria">Nombre Materia</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.nombreMateria}</dd>
          <dt>
            <span id="creditos">
              <Translate contentKey="proyectoh4App.materia.creditos">Creditos</Translate>
            </span>
          </dt>
          <dd>{materiaEntity.creditos}</dd>
        </dl>
        <Button tag={Link} to="/materia" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materia/${materiaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MateriaDetail;
