import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBoitierCapteur } from 'app/shared/model/boitier-capteur.model';
import { getEntities } from './boitier-capteur.reducer';

export const BoitierCapteur = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const boitierCapteurList = useAppSelector(state => state.boitierCapteur.entities);
  const loading = useAppSelector(state => state.boitierCapteur.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="boitier-capteur-heading" data-cy="BoitierCapteurHeading">
        <Translate contentKey="appbiomedicaleApp.boitierCapteur.home.title">Boitier Capteurs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appbiomedicaleApp.boitierCapteur.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/boitier-capteur/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appbiomedicaleApp.boitierCapteur.home.createLabel">Create new Boitier Capteur</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {boitierCapteurList && boitierCapteurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="appbiomedicaleApp.boitierCapteur.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="appbiomedicaleApp.boitierCapteur.branche">Branche</Translate>
                </th>
                <th>
                  <Translate contentKey="appbiomedicaleApp.boitierCapteur.etat">Etat</Translate>
                </th>
                <th>
                  <Translate contentKey="appbiomedicaleApp.boitierCapteur.boitiers">Boitiers</Translate>
                </th>
                <th>
                  <Translate contentKey="appbiomedicaleApp.boitierCapteur.capteurs">Capteurs</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {boitierCapteurList.map((boitierCapteur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/boitier-capteur/${boitierCapteur.id}`} color="link" size="sm">
                      {boitierCapteur.id}
                    </Button>
                  </td>
                  <td>{boitierCapteur.branche}</td>
                  <td>{boitierCapteur.etat ? 'true' : 'false'}</td>
                  <td>
                    {boitierCapteur.boitiers ? <Link to={`/boitier/${boitierCapteur.boitiers.id}`}>{boitierCapteur.boitiers.id}</Link> : ''}
                  </td>
                  <td>
                    {boitierCapteur.capteurs ? <Link to={`/capteur/${boitierCapteur.capteurs.id}`}>{boitierCapteur.capteurs.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/boitier-capteur/${boitierCapteur.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/boitier-capteur/${boitierCapteur.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/boitier-capteur/${boitierCapteur.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="appbiomedicaleApp.boitierCapteur.home.notFound">No Boitier Capteurs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BoitierCapteur;
