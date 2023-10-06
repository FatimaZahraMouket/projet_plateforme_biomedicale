import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICamera } from 'app/shared/model/camera.model';
import { getEntities as getCameras } from 'app/entities/camera/camera.reducer';
import { IBoitier } from 'app/shared/model/boitier.model';
import { getEntity, updateEntity, createEntity, reset } from './boitier.reducer';

export const BoitierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cameras = useAppSelector(state => state.camera.entities);
  const boitierEntity = useAppSelector(state => state.boitier.entity);
  const loading = useAppSelector(state => state.boitier.loading);
  const updating = useAppSelector(state => state.boitier.updating);
  const updateSuccess = useAppSelector(state => state.boitier.updateSuccess);

  const handleClose = () => {
    navigate('/boitier');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCameras({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...boitierEntity,
      ...values,
      camera: cameras.find(it => it.id.toString() === values.camera.toString()),
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
          ...boitierEntity,
          camera: boitierEntity?.camera?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appbiomedicaleApp.boitier.home.createOrEditLabel" data-cy="BoitierCreateUpdateHeading">
            <Translate contentKey="appbiomedicaleApp.boitier.home.createOrEditLabel">Create or edit a Boitier</Translate>
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
                  id="boitier-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appbiomedicaleApp.boitier.type')}
                id="boitier-type"
                name="type"
                data-cy="type"
                type="text"
              />
              <ValidatedField label={translate('appbiomedicaleApp.boitier.ref')} id="boitier-ref" name="ref" data-cy="ref" type="text" />
              <ValidatedField
                label={translate('appbiomedicaleApp.boitier.nbrBranche')}
                id="boitier-nbrBranche"
                name="nbrBranche"
                data-cy="nbrBranche"
                type="text"
              />
              <ValidatedField
                id="boitier-camera"
                name="camera"
                data-cy="camera"
                label={translate('appbiomedicaleApp.boitier.camera')}
                type="select"
              >
                <option value="" key="0" />
                {cameras
                  ? cameras.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/boitier" replace color="info">
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

export default BoitierUpdate;
