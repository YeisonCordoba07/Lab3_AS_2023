import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEstadoSolicitud } from 'app/shared/model/estado-solicitud.model';
import { getEntity, updateEntity, createEntity, reset } from './estado-solicitud.reducer';

export const EstadoSolicitudUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const estadoSolicitudEntity = useAppSelector(state => state.estadoSolicitud.entity);
  const loading = useAppSelector(state => state.estadoSolicitud.loading);
  const updating = useAppSelector(state => state.estadoSolicitud.updating);
  const updateSuccess = useAppSelector(state => state.estadoSolicitud.updateSuccess);

  const handleClose = () => {
    navigate('/estado-solicitud');
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
      ...estadoSolicitudEntity,
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
          ...estadoSolicitudEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.estadoSolicitud.home.createOrEditLabel" data-cy="EstadoSolicitudCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.estadoSolicitud.home.createOrEditLabel">Create or edit a EstadoSolicitud</Translate>
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
                  id="estado-solicitud-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.estadoSolicitud.idEstadoSolicitud')}
                id="estado-solicitud-idEstadoSolicitud"
                name="idEstadoSolicitud"
                data-cy="idEstadoSolicitud"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.estadoSolicitud.stateSolicitud')}
                id="estado-solicitud-stateSolicitud"
                name="stateSolicitud"
                data-cy="stateSolicitud"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/estado-solicitud" replace color="info">
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

export default EstadoSolicitudUpdate;
