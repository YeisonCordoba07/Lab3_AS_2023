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
import { IRelacion } from 'app/shared/model/relacion.model';
import { getEntity, updateEntity, createEntity, reset } from './relacion.reducer';

export const RelacionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const materias = useAppSelector(state => state.materia.entities);
  const relacionEntity = useAppSelector(state => state.relacion.entity);
  const loading = useAppSelector(state => state.relacion.loading);
  const updating = useAppSelector(state => state.relacion.updating);
  const updateSuccess = useAppSelector(state => state.relacion.updateSuccess);

  const handleClose = () => {
    navigate('/relacion');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMaterias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...relacionEntity,
      ...values,
      materia: materias.find(it => it.id.toString() === values.materia.toString()),
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
          ...relacionEntity,
          materia: relacionEntity?.materia?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="proyectoh4App.relacion.home.createOrEditLabel" data-cy="RelacionCreateUpdateHeading">
            <Translate contentKey="proyectoh4App.relacion.home.createOrEditLabel">Create or edit a Relacion</Translate>
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
                  id="relacion-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('proyectoh4App.relacion.codigoMateria')}
                id="relacion-codigoMateria"
                name="codigoMateria"
                data-cy="codigoMateria"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.relacion.codigoMateriaRelacionada')}
                id="relacion-codigoMateriaRelacionada"
                name="codigoMateriaRelacionada"
                data-cy="codigoMateriaRelacionada"
                type="text"
              />
              <ValidatedField
                label={translate('proyectoh4App.relacion.tipoRelacion')}
                id="relacion-tipoRelacion"
                name="tipoRelacion"
                data-cy="tipoRelacion"
                type="text"
              />
              <ValidatedField
                id="relacion-materia"
                name="materia"
                data-cy="materia"
                label={translate('proyectoh4App.relacion.materia')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/relacion" replace color="info">
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

export default RelacionUpdate;
