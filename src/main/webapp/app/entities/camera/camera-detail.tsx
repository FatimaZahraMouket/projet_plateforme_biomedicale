import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './camera.reducer';

export const CameraDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cameraEntity = useAppSelector(state => state.camera.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cameraDetailsHeading">
          <Translate contentKey="appbiomedicaleApp.camera.detail.title">Camera</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cameraEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="appbiomedicaleApp.camera.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{cameraEntity.nom}</dd>
          <dt>
            <span id="resolution">
              <Translate contentKey="appbiomedicaleApp.camera.resolution">Resolution</Translate>
            </span>
          </dt>
          <dd>{cameraEntity.resolution}</dd>
        </dl>
        <Button tag={Link} to="/camera" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/camera/${cameraEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CameraDetail;
