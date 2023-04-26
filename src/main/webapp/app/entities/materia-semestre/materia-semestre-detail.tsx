import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materia-semestre.reducer';

export const MateriaSemestreDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materiaSemestreEntity = useAppSelector(state => state.materiaSemestre.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materiaSemestreDetailsHeading">
          <Translate contentKey="proyectoh4App.materiaSemestre.detail.title">MateriaSemestre</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materiaSemestreEntity.id}</dd>
          <dt>
            <span id="idMateriaSemestre">
              <Translate contentKey="proyectoh4App.materiaSemestre.idMateriaSemestre">Id Materia Semestre</Translate>
            </span>
          </dt>
          <dd>{materiaSemestreEntity.idMateriaSemestre}</dd>
          <dt>
            <span id="cedulaEstudiante">
              <Translate contentKey="proyectoh4App.materiaSemestre.cedulaEstudiante">Cedula Estudiante</Translate>
            </span>
          </dt>
          <dd>{materiaSemestreEntity.cedulaEstudiante}</dd>
          <dt>
            <span id="idSemestre">
              <Translate contentKey="proyectoh4App.materiaSemestre.idSemestre">Id Semestre</Translate>
            </span>
          </dt>
          <dd>{materiaSemestreEntity.idSemestre}</dd>
          <dt>
            <span id="codigoMateria">
              <Translate contentKey="proyectoh4App.materiaSemestre.codigoMateria">Codigo Materia</Translate>
            </span>
          </dt>
          <dd>{materiaSemestreEntity.codigoMateria}</dd>
          <dt>
            <span id="notaDefinitiva">
              <Translate contentKey="proyectoh4App.materiaSemestre.notaDefinitiva">Nota Definitiva</Translate>
            </span>
          </dt>
          <dd>{materiaSemestreEntity.notaDefinitiva}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.materiaSemestre.materia">Materia</Translate>
          </dt>
          <dd>{materiaSemestreEntity.materia ? materiaSemestreEntity.materia.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.materiaSemestre.estudiante">Estudiante</Translate>
          </dt>
          <dd>{materiaSemestreEntity.estudiante ? materiaSemestreEntity.estudiante.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.materiaSemestre.semestre">Semestre</Translate>
          </dt>
          <dd>{materiaSemestreEntity.semestre ? materiaSemestreEntity.semestre.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/materia-semestre" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materia-semestre/${materiaSemestreEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MateriaSemestreDetail;
