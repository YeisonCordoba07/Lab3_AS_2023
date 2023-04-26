import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './historia-academica.reducer';

export const HistoriaAcademicaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const historiaAcademicaEntity = useAppSelector(state => state.historiaAcademica.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="historiaAcademicaDetailsHeading">
          <Translate contentKey="proyectoh4App.historiaAcademica.detail.title">HistoriaAcademica</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.id}</dd>
          <dt>
            <span id="idHistoriaAcademica">
              <Translate contentKey="proyectoh4App.historiaAcademica.idHistoriaAcademica">Id Historia Academica</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.idHistoriaAcademica}</dd>
          <dt>
            <span id="cedulaEstudiante">
              <Translate contentKey="proyectoh4App.historiaAcademica.cedulaEstudiante">Cedula Estudiante</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.cedulaEstudiante}</dd>
          <dt>
            <span id="idSemestre">
              <Translate contentKey="proyectoh4App.historiaAcademica.idSemestre">Id Semestre</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.idSemestre}</dd>
          <dt>
            <span id="codigoPrograma">
              <Translate contentKey="proyectoh4App.historiaAcademica.codigoPrograma">Codigo Programa</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.codigoPrograma}</dd>
          <dt>
            <span id="promedioAcomulado">
              <Translate contentKey="proyectoh4App.historiaAcademica.promedioAcomulado">Promedio Acomulado</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.promedioAcomulado}</dd>
          <dt>
            <span id="promedioSemestre">
              <Translate contentKey="proyectoh4App.historiaAcademica.promedioSemestre">Promedio Semestre</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.promedioSemestre}</dd>
          <dt>
            <span id="idTercio">
              <Translate contentKey="proyectoh4App.historiaAcademica.idTercio">Id Tercio</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.idTercio}</dd>
          <dt>
            <span id="situationAcademica">
              <Translate contentKey="proyectoh4App.historiaAcademica.situationAcademica">Situation Academica</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.situationAcademica}</dd>
          <dt>
            <span id="stateSemestre">
              <Translate contentKey="proyectoh4App.historiaAcademica.stateSemestre">State Semestre</Translate>
            </span>
          </dt>
          <dd>{historiaAcademicaEntity.stateSemestre}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.historiaAcademica.estudiante">Estudiante</Translate>
          </dt>
          <dd>{historiaAcademicaEntity.estudiante ? historiaAcademicaEntity.estudiante.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.historiaAcademica.semestre">Semestre</Translate>
          </dt>
          <dd>{historiaAcademicaEntity.semestre ? historiaAcademicaEntity.semestre.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.historiaAcademica.situacionAcademica">Situacion Academica</Translate>
          </dt>
          <dd>{historiaAcademicaEntity.situacionAcademica ? historiaAcademicaEntity.situacionAcademica.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.historiaAcademica.tercio">Tercio</Translate>
          </dt>
          <dd>{historiaAcademicaEntity.tercio ? historiaAcademicaEntity.tercio.id : ''}</dd>
          <dt>
            <Translate contentKey="proyectoh4App.historiaAcademica.estadoSemestre">Estado Semestre</Translate>
          </dt>
          <dd>{historiaAcademicaEntity.estadoSemestre ? historiaAcademicaEntity.estadoSemestre.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/historia-academica" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/historia-academica/${historiaAcademicaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HistoriaAcademicaDetail;
