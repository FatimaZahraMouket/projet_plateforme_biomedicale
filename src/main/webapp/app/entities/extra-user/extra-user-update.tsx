import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IExtraUser } from 'app/shared/model/extra-user.model';
import { getEntity, updateEntity, createEntity, reset } from './extra-user.reducer';

export const ExtraUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const extraUserEntity = useAppSelector(state => state.extraUser.entity);
  const loading = useAppSelector(state => state.extraUser.loading);
  const updating = useAppSelector(state => state.extraUser.updating);
  const updateSuccess = useAppSelector(state => state.extraUser.updateSuccess);

  const handleClose = () => {
    navigate('/extra-user');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...extraUserEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...extraUserEntity,
          user: extraUserEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appbiomedicaleApp.extraUser.home.createOrEditLabel" data-cy="ExtraUserCreateUpdateHeading">
            <Translate contentKey="appbiomedicaleApp.extraUser.home.createOrEditLabel">Create or edit a ExtraUser</Translate>
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
                  id="extra-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appbiomedicaleApp.extraUser.cin')}
                id="extra-user-cin"
                name="cin"
                data-cy="cin"
                type="text"
              />
              <ValidatedField
                label={translate('appbiomedicaleApp.extraUser.numeroTelephone')}
                id="extra-user-numeroTelephone"
                name="numeroTelephone"
                data-cy="numeroTelephone"
                type="text"
              />
              <ValidatedField
                label={translate('appbiomedicaleApp.extraUser.dateNaissance')}
                id="extra-user-dateNaissance"
                name="dateNaissance"
                data-cy="dateNaissance"
                type="date"
              />
              <ValidatedField
                label={translate('appbiomedicaleApp.extraUser.nationalite')}
                id="extra-user-nationalite"
                name="nationalite"
                data-cy="nationalite"
                type="text"
              />
              <ValidatedField
                label={translate('appbiomedicaleApp.extraUser.adresse')}
                id="extra-user-adresse"
                name="adresse"
                data-cy="adresse"
                type="text"
              />
              <ValidatedField
                label={translate('appbiomedicaleApp.extraUser.genre')}
                id="extra-user-genre"
                name="genre"
                data-cy="genre"
                type="text"
              />
              <ValidatedField
                id="extra-user-user"
                name="user"
                data-cy="user"
                label={translate('appbiomedicaleApp.extraUser.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/extra-user" replace color="info">
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

export default ExtraUserUpdate;
