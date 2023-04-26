import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoSemestre } from 'app/shared/model/tipo-semestre.model';
import { getEntity, updateEntity, createEntity, reset } from './tipo-semestre.reducer';

export const TipoSemestreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tipoSemestreEntity = useAppSelector(state => state.tipoSemestre.entity);
  const loading = useAppSelector(state => state.tipoSemestre.loading);
  const updating = useAppSelector(state => state.tipoSemestre.updating);
  const updateSuccess = useAppSelector(state => state.tipoSemestre.updateSuccess);

  const handleClose = () => {
    navigate('/tipo-semestre');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...tipoSemestreEntity,
      ...values,
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
          ...tipoSemestreEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.tipoSemestre.home.createOrEditLabel" data-cy="TipoSemestreCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.tipoSemestre.home.createOrEditLabel">Create or edit a TipoSemestre</Translate>
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
                  id="tipo-semestre-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.tipoSemestre.idTipoSemestre')}
                id="tipo-semestre-idTipoSemestre"
                name="idTipoSemestre"
                data-cy="idTipoSemestre"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.tipoSemestre.typeSemestre')}
                id="tipo-semestre-typeSemestre"
                name="typeSemestre"
                data-cy="typeSemestre"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tipo-semestre" replace color="info">
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

export default TipoSemestreUpdate;
