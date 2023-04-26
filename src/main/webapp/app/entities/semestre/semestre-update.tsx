import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoSemestre } from 'app/shared/model/tipo-semestre.model';
import { getEntities as getTipoSemestres } from 'app/entities/tipo-semestre/tipo-semestre.reducer';
import { IEstadoSemestre } from 'app/shared/model/estado-semestre.model';
import { getEntities as getEstadoSemestres } from 'app/entities/estado-semestre/estado-semestre.reducer';
import { ISemestre } from 'app/shared/model/semestre.model';
import { getEntity, updateEntity, createEntity, reset } from './semestre.reducer';

export const SemestreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tipoSemestres = useAppSelector(state => state.tipoSemestre.entities);
  const estadoSemestres = useAppSelector(state => state.estadoSemestre.entities);
  const semestreEntity = useAppSelector(state => state.semestre.entity);
  const loading = useAppSelector(state => state.semestre.loading);
  const updating = useAppSelector(state => state.semestre.updating);
  const updateSuccess = useAppSelector(state => state.semestre.updateSuccess);

  const handleClose = () => {
    navigate('/semestre');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTipoSemestres({}));
    dispatch(getEstadoSemestres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...semestreEntity,
      ...values,
      tipoSemestre: tipoSemestres.find(it => it.id.toString() === values.tipoSemestre.toString()),
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
          ...semestreEntity,
          tipoSemestre: semestreEntity?.tipoSemestre?.id,
          estadoSemestre: semestreEntity?.estadoSemestre?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.semestre.home.createOrEditLabel" data-cy="SemestreCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.semestre.home.createOrEditLabel">Create or edit a Semestre</Translate>
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
                  id="semestre-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.semestre.idSemestre')}
                id="semestre-idSemestre"
                name="idSemestre"
                data-cy="idSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.semestre.fechaInicio')}
                id="semestre-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="date"
              />
              <ValidatedField
                label={translate('proyectoh4App.semestre.fechaTerminacion')}
                id="semestre-fechaTerminacion"
                name="fechaTerminacion"
                data-cy="fechaTerminacion"
                type="date"
              />
              <ValidatedField
                label={translate('proyectoh4App.semestre.typeSemestre')}
                id="semestre-typeSemestre"
                name="typeSemestre"
                data-cy="typeSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.semestre.stateSemestre')}
                id="semestre-stateSemestre"
                name="stateSemestre"
                data-cy="stateSemestre"
                type="text"
              />
              <ValidatedField
                id="semestre-tipoSemestre"
                name="tipoSemestre"
                data-cy="tipoSemestre"
                label={translate('proyectoh4App.semestre.tipoSemestre')}
                type="select"
              >
                <option value="" key="0" />
                {tipoSemestres
                  ? tipoSemestres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="semestre-estadoSemestre"
                name="estadoSemestre"
                data-cy="estadoSemestre"
                label={translate('proyectoh4App.semestre.estadoSemestre')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/semestre" replace color="info">
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

export default SemestreUpdate;
