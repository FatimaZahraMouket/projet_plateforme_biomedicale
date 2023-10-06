package com.apbiomedicale.web.rest;

import com.apbiomedicale.domain.Camera;
import com.apbiomedicale.repository.CameraRepository;
import com.apbiomedicale.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apbiomedicale.domain.Camera}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CameraResource {

    private final Logger log = LoggerFactory.getLogger(CameraResource.class);

    private static final String ENTITY_NAME = "camera";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CameraRepository cameraRepository;

    public CameraResource(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    /**
     * {@code POST  /cameras} : Create a new camera.
     *
     * @param camera the camera to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new camera, or with status {@code 400 (Bad Request)} if the camera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cameras")
    public ResponseEntity<Camera> createCamera(@RequestBody Camera camera) throws URISyntaxException {
        log.debug("REST request to save Camera : {}", camera);
        if (camera.getId() != null) {
            throw new BadRequestAlertException("A new camera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Camera result = cameraRepository.save(camera);
        return ResponseEntity
            .created(new URI("/api/cameras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cameras/:id} : Updates an existing camera.
     *
     * @param id the id of the camera to save.
     * @param camera the camera to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated camera,
     * or with status {@code 400 (Bad Request)} if the camera is not valid,
     * or with status {@code 500 (Internal Server Error)} if the camera couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cameras/{id}")
    public ResponseEntity<Camera> updateCamera(@PathVariable(value = "id", required = false) final Long id, @RequestBody Camera camera)
        throws URISyntaxException {
        log.debug("REST request to update Camera : {}, {}", id, camera);
        if (camera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, camera.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Camera result = cameraRepository.save(camera);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, camera.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cameras/:id} : Partial updates given fields of an existing camera, field will ignore if it is null
     *
     * @param id the id of the camera to save.
     * @param camera the camera to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated camera,
     * or with status {@code 400 (Bad Request)} if the camera is not valid,
     * or with status {@code 404 (Not Found)} if the camera is not found,
     * or with status {@code 500 (Internal Server Error)} if the camera couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cameras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Camera> partialUpdateCamera(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Camera camera
    ) throws URISyntaxException {
        log.debug("REST request to partial update Camera partially : {}, {}", id, camera);
        if (camera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, camera.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cameraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Camera> result = cameraRepository
            .findById(camera.getId())
            .map(existingCamera -> {
                if (camera.getNom() != null) {
                    existingCamera.setNom(camera.getNom());
                }
                if (camera.getResolution() != null) {
                    existingCamera.setResolution(camera.getResolution());
                }

                return existingCamera;
            })
            .map(cameraRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, camera.getId().toString())
        );
    }

    /**
     * {@code GET  /cameras} : get all the cameras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cameras in body.
     */
    @GetMapping("/cameras")
    public List<Camera> getAllCameras() {
        log.debug("REST request to get all Cameras");
        return cameraRepository.findAll();
    }

    /**
     * {@code GET  /cameras/:id} : get the "id" camera.
     *
     * @param id the id of the camera to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the camera, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cameras/{id}")
    public ResponseEntity<Camera> getCamera(@PathVariable Long id) {
        log.debug("REST request to get Camera : {}", id);
        Optional<Camera> camera = cameraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(camera);
    }

    /**
     * {@code DELETE  /cameras/:id} : delete the "id" camera.
     *
     * @param id the id of the camera to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cameras/{id}")
    public ResponseEntity<Void> deleteCamera(@PathVariable Long id) {
        log.debug("REST request to delete Camera : {}", id);
        cameraRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
