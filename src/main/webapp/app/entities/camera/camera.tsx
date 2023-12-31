import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICamera } from 'app/shared/model/camera.model';
import { getEntities } from './camera.reducer';

export const Camera = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const cameraList = useAppSelector(state => state.camera.entities);
  const loading = useAppSelector(state => state.camera.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="camera-heading" data-cy="CameraHeading">
        <Translate contentKey="appbiomedicaleApp.camera.home.title">Cameras</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appbiomedicaleApp.camera.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/camera/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appbiomedicaleApp.camera.home.createLabel">Create new Camera</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cameraList && cameraList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="appbiomedicaleApp.camera.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="appbiomedicaleApp.camera.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="appbiomedicaleApp.camera.resolution">Resolution</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cameraList.map((camera, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/camera/${camera.id}`} color="link" size="sm">
                      {camera.id}
                    </Button>
                  </td>
                  <td>{camera.nom}</td>
                  <td>{camera.resolution}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/camera/${camera.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/camera/${camera.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/camera/${camera.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="appbiomedicaleApp.camera.home.notFound">No Cameras found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Camera;
