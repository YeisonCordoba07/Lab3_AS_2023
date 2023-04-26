import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProgramaAcademico } from 'app/shared/model/programa-academico.model';
import { getEntities as getProgramaAcademicos } from 'app/entities/programa-academico/programa-academico.reducer';
import { IPlanEstudios } from 'app/shared/model/plan-estudios.model';
import { getEntities as getPlanEstudios } from 'app/entities/plan-estudios/plan-estudios.reducer';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { getEntity, updateEntity, createEntity, reset } from './estudiante.reducer';

export const EstudianteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const programaAcademicos = useAppSelector(state => state.programaAcademico.entities);
  const planEstudios = useAppSelector(state => state.planEstudios.entities);
  const estudianteEntity = useAppSelector(state => state.estudiante.entity);
  const loading = useAppSelector(state => state.estudiante.loading);
  const updating = useAppSelector(state => state.estudiante.updating);
  const updateSuccess = useAppSelector(state => state.estudiante.updateSuccess);

  const handleClose = () => {
    navigate('/estudiante');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProgramaAcademicos({}));
    dispatch(getPlanEstudios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...estudianteEntity,
      ...values,
      programaAcademico: programaAcademicos.find(it => it.id.toString() === values.programaAcademico.toString()),
      planEstudios: planEstudios.find(it => it.id.toString() === values.planEstudios.toString()),
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
          ...estudianteEntity,
          programaAcademico: estudianteEntity?.programaAcademico?.id,
          planEstudios: estudianteEntity?.planEstudios?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.estudiante.home.createOrEditLabel" data-cy="EstudianteCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.estudiante.home.createOrEditLabel">Create or edit a Estudiante</Translate>
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
                  id="estudiante-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.estudiante.cedula')}
                id="estudiante-cedula"
                name="cedula"
                data-cy="cedula"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.nombre')}
                id="estudiante-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.apellido')}
                id="estudiante-apellido"
                name="apellido"
                data-cy="apellido"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.correoInstitucional')}
                id="estudiante-correoInstitucional"
                name="correoInstitucional"
                data-cy="correoInstitucional"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.correoPersonal')}
                id="estudiante-correoPersonal"
                name="correoPersonal"
                data-cy="correoPersonal"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.celular')}
                id="estudiante-celular"
                name="celular"
                data-cy="celular"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.estrato')}
                id="estudiante-estrato"
                name="estrato"
                data-cy="estrato"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.fechaIngreso')}
                id="estudiante-fechaIngreso"
                name="fechaIngreso"
                data-cy="fechaIngreso"
                type="date"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.version')}
                id="estudiante-version"
                name="version"
                data-cy="version"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estudiante.codigoPrograma')}
                id="estudiante-codigoPrograma"
                name="codigoPrograma"
                data-cy="codigoPrograma"
                type="text"
              />
              <ValidatedField
                id="estudiante-programaAcademico"
                name="programaAcademico"
                data-cy="programaAcademico"
                label={translate('proyectoh4App.estudiante.programaAcademico')}
                type="select"
              >
                <option value="" key="0" />
                {programaAcademicos
                  ? programaAcademicos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="estudiante-planEstudios"
                name="planEstudios"
                data-cy="planEstudios"
                label={translate('proyectoh4App.estudiante.planEstudios')}
                type="select"
              >
                <option value="" key="0" />
                {planEstudios
                  ? planEstudios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/estudiante" replace color="info">
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

export default EstudianteUpdate;
