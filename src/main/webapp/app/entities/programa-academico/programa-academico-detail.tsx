import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './programa-academico.reducer';

export const ProgramaAcademicoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const programaAcademicoEntity = useAppSelector(state => state.programaAcademico.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="programaAcademicoDetailsHeading">
          <Translate contentKey="proyectoh4App.programaAcademico.detail.title">ProgramaAcademico</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.id}</dd>
          <dt>
            <span id="codigoPrograma">
              <Translate contentKey="proyectoh4App.programaAcademico.codigoPrograma">Codigo Programa</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.codigoPrograma}</dd>
          <dt>
            <span id="nombrePrograma">
              <Translate contentKey="proyectoh4App.programaAcademico.nombrePrograma">Nombre Programa</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.nombrePrograma}</dd>
          <dt>
            <span id="duracion">
              <Translate contentKey="proyectoh4App.programaAcademico.duracion">Duracion</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.duracion}</dd>
          <dt>
            <span id="vigencia">
              <Translate contentKey="proyectoh4App.programaAcademico.vigencia">Vigencia</Translate>
            </span>
          </dt>
          <dd>
            {programaAcademicoEntity.vigencia ? (
              <TextFormat value={programaAcademicoEntity.vigencia} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/programa-academico" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/programa-academico/${programaAcademicoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProgramaAcademicoDetail;
