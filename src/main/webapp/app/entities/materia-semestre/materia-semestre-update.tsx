import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMateria } from 'app/shared/model/materia.model';
import { getEntities as getMaterias } from 'app/entities/materia/materia.reducer';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { getEntities as getEstudiantes } from 'app/entities/estudiante/estudiante.reducer';
import { ISemestre } from 'app/shared/model/semestre.model';
import { getEntities as getSemestres } from 'app/entities/semestre/semestre.reducer';
import { IMateriaSemestre } from 'app/shared/model/materia-semestre.model';
import { getEntity, updateEntity, createEntity, reset } from './materia-semestre.reducer';

export const MateriaSemestreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const materias = useAppSelector(state => state.materia.entities);
  const estudiantes = useAppSelector(state => state.estudiante.entities);
  const semestres = useAppSelector(state => state.semestre.entities);
  const materiaSemestreEntity = useAppSelector(state => state.materiaSemestre.entity);
  const loading = useAppSelector(state => state.materiaSemestre.loading);
  const updating = useAppSelector(state => state.materiaSemestre.updating);
  const updateSuccess = useAppSelector(state => state.materiaSemestre.updateSuccess);

  const handleClose = () => {
    navigate('/materia-semestre');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMaterias({}));
    dispatch(getEstudiantes({}));
    dispatch(getSemestres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...materiaSemestreEntity,
      ...values,
      materia: materias.find(it => it.id.toString() === values.materia.toString()),
      estudiante: estudiantes.find(it => it.id.toString() === values.estudiante.toString()),
      semestre: semestres.find(it => it.id.toString() === values.semestre.toString()),
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
          ...materiaSemestreEntity,
          materia: materiaSemestreEntity?.materia?.id,
          estudiante: materiaSemestreEntity?.estudiante?.id,
          semestre: materiaSemestreEntity?.semestre?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.materiaSemestre.home.createOrEditLabel" data-cy="MateriaSemestreCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.materiaSemestre.home.createOrEditLabel">Create or edit a MateriaSemestre</Translate>
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
                  id="materia-semestre-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.materiaSemestre.idMateriaSemestre')}
                id="materia-semestre-idMateriaSemestre"
                name="idMateriaSemestre"
                data-cy="idMateriaSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.materiaSemestre.cedulaEstudiante')}
                id="materia-semestre-cedulaEstudiante"
                name="cedulaEstudiante"
                data-cy="cedulaEstudiante"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.materiaSemestre.idSemestre')}
                id="materia-semestre-idSemestre"
                name="idSemestre"
                data-cy="idSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.materiaSemestre.codigoMateria')}
                id="materia-semestre-codigoMateria"
                name="codigoMateria"
                data-cy="codigoMateria"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.materiaSemestre.notaDefinitiva')}
                id="materia-semestre-notaDefinitiva"
                name="notaDefinitiva"
                data-cy="notaDefinitiva"
                type="text"
              />
              <ValidatedField
                id="materia-semestre-materia"
                name="materia"
                data-cy="materia"
                label={translate('proyectoh4App.materiaSemestre.materia')}
                type="select"
              >
                <option value="" key="0" />
                {materias
                  ? materias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="materia-semestre-estudiante"
                name="estudiante"
                data-cy="estudiante"
                label={translate('proyectoh4App.materiaSemestre.estudiante')}
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
                id="materia-semestre-semestre"
                name="semestre"
                data-cy="semestre"
                label={translate('proyectoh4App.materiaSemestre.semestre')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/materia-semestre" replace color="info">
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

export default MateriaSemestreUpdate;
