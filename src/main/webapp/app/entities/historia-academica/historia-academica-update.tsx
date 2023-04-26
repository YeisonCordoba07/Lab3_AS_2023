import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstudiante } from 'app/shared/model/estudiante.model';
import { getEntities as getEstudiantes } from 'app/entities/estudiante/estudiante.reducer';
import { ISemestre } from 'app/shared/model/semestre.model';
import { getEntities as getSemestres } from 'app/entities/semestre/semestre.reducer';
import { ISituacionAcademica } from 'app/shared/model/situacion-academica.model';
import { getEntities as getSituacionAcademicas } from 'app/entities/situacion-academica/situacion-academica.reducer';
import { ITercio } from 'app/shared/model/tercio.model';
import { getEntities as getTercios } from 'app/entities/tercio/tercio.reducer';
import { IEstadoSemestre } from 'app/shared/model/estado-semestre.model';
import { getEntities as getEstadoSemestres } from 'app/entities/estado-semestre/estado-semestre.reducer';
import { IHistoriaAcademica } from 'app/shared/model/historia-academica.model';
import { getEntity, updateEntity, createEntity, reset } from './historia-academica.reducer';

export const HistoriaAcademicaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const estudiantes = useAppSelector(state => state.estudiante.entities);
  const semestres = useAppSelector(state => state.semestre.entities);
  const situacionAcademicas = useAppSelector(state => state.situacionAcademica.entities);
  const tercios = useAppSelector(state => state.tercio.entities);
  const estadoSemestres = useAppSelector(state => state.estadoSemestre.entities);
  const historiaAcademicaEntity = useAppSelector(state => state.historiaAcademica.entity);
  const loading = useAppSelector(state => state.historiaAcademica.loading);
  const updating = useAppSelector(state => state.historiaAcademica.updating);
  const updateSuccess = useAppSelector(state => state.historiaAcademica.updateSuccess);

  const handleClose = () => {
    navigate('/historia-academica');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEstudiantes({}));
    dispatch(getSemestres({}));
    dispatch(getSituacionAcademicas({}));
    dispatch(getTercios({}));
    dispatch(getEstadoSemestres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...historiaAcademicaEntity,
      ...values,
      estudiante: estudiantes.find(it => it.id.toString() === values.estudiante.toString()),
      semestre: semestres.find(it => it.id.toString() === values.semestre.toString()),
      situacionAcademica: situacionAcademicas.find(it => it.id.toString() === values.situacionAcademica.toString()),
      tercio: tercios.find(it => it.id.toString() === values.tercio.toString()),
      estadoSemestre: estadoSemestres.find(it => it.id.toString() === values.estadoSemestre.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...historiaAcademicaEntity,
          estudiante: historiaAcademicaEntity?.estudiante?.id,
          semestre: historiaAcademicaEntity?.semestre?.id,
          situacionAcademica: historiaAcademicaEntity?.situacionAcademica?.id,
          tercio: historiaAcademicaEntity?.tercio?.id,
          estadoSemestre: historiaAcademicaEntity?.estadoSemestre?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.historiaAcademica.home.createOrEditLabel" data-cy="HistoriaAcademicaCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.historiaAcademica.home.createOrEditLabel">Create or edit a HistoriaAcademica</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="historia-academica-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.idHistoriaAcademica')}
                id="historia-academica-idHistoriaAcademica"
                name="idHistoriaAcademica"
                data-cy="idHistoriaAcademica"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.cedulaEstudiante')}
                id="historia-academica-cedulaEstudiante"
                name="cedulaEstudiante"
                data-cy="cedulaEstudiante"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.idSemestre')}
                id="historia-academica-idSemestre"
                name="idSemestre"
                data-cy="idSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.codigoPrograma')}
                id="historia-academica-codigoPrograma"
                name="codigoPrograma"
                data-cy="codigoPrograma"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.promedioAcomulado')}
                id="historia-academica-promedioAcomulado"
                name="promedioAcomulado"
                data-cy="promedioAcomulado"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.promedioSemestre')}
                id="historia-academica-promedioSemestre"
                name="promedioSemestre"
                data-cy="promedioSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.idTercio')}
                id="historia-academica-idTercio"
                name="idTercio"
                data-cy="idTercio"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.situationAcademica')}
                id="historia-academica-situationAcademica"
                name="situationAcademica"
                data-cy="situationAcademica"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.historiaAcademica.stateSemestre')}
                id="historia-academica-stateSemestre"
                name="stateSemestre"
                data-cy="stateSemestre"
                type="text"
              />
              <ValidatedField
                id="historia-academica-estudiante"
                name="estudiante"
                data-cy="estudiante"
                label={translate('proyectoh4App.historiaAcademica.estudiante')}
                type="select"
              >
                <option value="" key="0" />
                {estudiantes
                  ? estudiantes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="historia-academica-semestre"
                name="semestre"
                data-cy="semestre"
                label={translate('proyectoh4App.historiaAcademica.semestre')}
                type="select"
              >
                <option value="" key="0" />
                {semestres
                  ? semestres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="historia-academica-situacionAcademica"
                name="situacionAcademica"
                data-cy="situacionAcademica"
                label={translate('proyectoh4App.historiaAcademica.situacionAcademica')}
                type="select"
              >
                <option value="" key="0" />
                {situacionAcademicas
                  ? situacionAcademicas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="historia-academica-tercio"
                name="tercio"
                data-cy="tercio"
                label={translate('proyectoh4App.historiaAcademica.tercio')}
                type="select"
              >
                <option value="" key="0" />
                {tercios
                  ? tercios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="historia-academica-estadoSemestre"
                name="estadoSemestre"
                data-cy="estadoSemestre"
                label={translate('proyectoh4App.historiaAcademica.estadoSemestre')}
                type="select"
              >
                <option value="" key="0" />
                {estadoSemestres
                  ? estadoSemestres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/historia-academica" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default HistoriaAcademicaUpdate;
