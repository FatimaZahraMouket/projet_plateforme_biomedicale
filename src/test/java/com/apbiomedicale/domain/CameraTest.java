package com.apbiomedicale.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apbiomedicale.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CameraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Camera.class);
        Camera camera1 = new Camera();
        camera1.setId(1L);
        Camera camera2 = new Camera();
        camera2.setId(camera1.getId());
        assertThat(camera1).isEqualTo(camera2);
        camera2.setId(2L);
        assertThat(camera1).isNotEqualTo(camera2);
        camera1.setId(null);
        assertThat(camera1).isNotEqualTo(camera2);
    }
}
