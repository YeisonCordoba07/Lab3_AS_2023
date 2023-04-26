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
import { getEntity, updateEntity, createEntity, reset } from './plan-estudios.reducer';

export const PlanEstudiosUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const programaAcademicos = useAppSelector(state => state.programaAcademico.entities);
  const planEstudiosEntity = useAppSelector(state => state.planEstudios.entity);
  const loading = useAppSelector(state => state.planEstudios.loading);
  const updating = useAppSelector(state => state.planEstudios.updating);
  const updateSuccess = useAppSelector(state => state.planEstudios.updateSuccess);

  const handleClose = () => {
    navigate('/plan-estudios');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProgramaAcademicos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...planEstudiosEntity,
      ...values,
      programaAcademico: programaAcademicos.find(it => it.id.toString() === values.programaAcademico.toString()),
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
          ...planEstudiosEntity,
          programaAcademico: planEstudiosEntity?.programaAcademico?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.planEstudios.home.createOrEditLabel" data-cy="PlanEstudiosCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.planEstudios.home.createOrEditLabel">Create or edit a PlanEstudios</Translate>
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
                  id="plan-estudios-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.planEstudios.version')}
                id="plan-estudios-version"
                name="version"
                data-cy="version"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.planEstudios.codigoPrograma')}
                id="plan-estudios-codigoPrograma"
                name="codigoPrograma"
                data-cy="codigoPrograma"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.planEstudios.fechaAprobacion')}
                id="plan-estudios-fechaAprobacion"
                name="fechaAprobacion"
                data-cy="fechaAprobacion"
                type="date"
              />
              <ValidatedField
                id="plan-estudios-programaAcademico"
                name="programaAcademico"
                data-cy="programaAcademico"
                label={translate('proyectoh4App.planEstudios.programaAcademico')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plan-estudios" replace color="info">
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

export default PlanEstudiosUpdate;
