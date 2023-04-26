package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolicitudHomologacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitudHomologacion.class);
        SolicitudHomologacion solicitudHomologacion1 = new SolicitudHomologacion();
        solicitudHomologacion1.setId(1L);
        SolicitudHomologacion solicitudHomologacion2 = new SolicitudHomologacion();
        solicitudHomologacion2.setId(solicitudHomologacion1.getId());
        assertThat(solicitudHomologacion1).isEqualTo(solicitudHomologacion2);
        solicitudHomologacion2.setId(2L);
        assertThat(solicitudHomologacion1).isNotEqualTo(solicitudHomologacion2);
        solicitudHomologacion1.setId(null);
        assertThat(solicitudHomologacion1).isNotEqualTo(solicitudHomologacion2);
    }
}
