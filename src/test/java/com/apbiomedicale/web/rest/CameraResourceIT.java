package com.apbiomedicale.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apbiomedicale.IntegrationTest;
import com.apbiomedicale.domain.Camera;
import com.apbiomedicale.repository.CameraRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CameraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CameraResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cameras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCameraMockMvc;

    private Camera camera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camera createEntity(EntityManager em) {
        Camera camera = new Camera().nom(DEFAULT_NOM).resolution(DEFAULT_RESOLUTION);
        return camera;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Camera createUpdatedEntity(EntityManager em) {
        Camera camera = new Camera().nom(UPDATED_NOM).resolution(UPDATED_RESOLUTION);
        return camera;
    }

    @BeforeEach
    public void initTest() {
        camera = createEntity(em);
    }

    @Test
    @Transactional
    void createCamera() throws Exception {
        int databaseSizeBeforeCreate = cameraRepository.findAll().size();
        // Create the Camera
        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(camera)))
            .andExpect(status().isCreated());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeCreate + 1);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCamera.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
    }

    @Test
    @Transactional
    void createCameraWithExistingId() throws Exception {
        // Create the Camera with an existing ID
        camera.setId(1L);

        int databaseSizeBeforeCreate = cameraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCameraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(camera)))
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCameras() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get all the cameraList
        restCameraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(camera.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION)));
    }

    @Test
    @Transactional
    void getCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get the camera
        restCameraMockMvc
            .perform(get(ENTITY_API_URL_ID, camera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(camera.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION));
    }

    @Test
    @Transactional
    void getNonExistingCamera() throws Exception {
        // Get the camera
        restCameraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera
        Camera updatedCamera = cameraRepository.findById(camera.getId()).get();
        // Disconnect from session so that the updates on updatedCamera are not directly saved in db
        em.detach(updatedCamera);
        updatedCamera.nom(UPDATED_NOM).resolution(UPDATED_RESOLUTION);

        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCamera.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCamera.getResolution()).isEqualTo(UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    void putNonExistingCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, camera.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(camera))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(camera))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(camera)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCameraWithPatch() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera using partial update
        Camera partialUpdatedCamera = new Camera();
        partialUpdatedCamera.setId(camera.getId());

        partialUpdatedCamera.nom(UPDATED_NOM).resolution(UPDATED_RESOLUTION);

        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCamera.getResolution()).isEqualTo(UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    void fullUpdateCameraWithPatch() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera using partial update
        Camera partialUpdatedCamera = new Camera();
        partialUpdatedCamera.setId(camera.getId());

        partialUpdatedCamera.nom(UPDATED_NOM).resolution(UPDATED_RESOLUTION);

        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCamera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCamera))
            )
            .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameraList.get(cameraList.size() - 1);
        assertThat(testCamera.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCamera.getResolution()).isEqualTo(UPDATED_RESOLUTION);
    }

    @Test
    @Transactional
    void patchNonExistingCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, camera.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(camera))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(camera))
            )
            .andExpect(status().isBadRequest());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCamera() throws Exception {
        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();
        camera.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCameraMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(camera)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Camera in the database
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        int databaseSizeBeforeDelete = cameraRepository.findAll().size();

        // Delete the camera
        restCameraMockMvc
            .perform(delete(ENTITY_API_URL_ID, camera.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Camera> cameraList = cameraRepository.findAll();
        assertThat(cameraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
