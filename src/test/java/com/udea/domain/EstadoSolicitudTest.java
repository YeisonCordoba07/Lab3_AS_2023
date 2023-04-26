package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoSolicitudTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoSolicitud.class);
        EstadoSolicitud estadoSolicitud1 = new EstadoSolicitud();
        estadoSolicitud1.setId(1L);
        EstadoSolicitud estadoSolicitud2 = new EstadoSolicitud();
        estadoSolicitud2.setId(estadoSolicitud1.getId());
        assertThat(estadoSolicitud1).isEqualTo(estadoSolicitud2);
        estadoSolicitud2.setId(2L);
        assertThat(estadoSolicitud1).isNotEqualTo(estadoSolicitud2);
        estadoSolicitud1.setId(null);
        assertThat(estadoSolicitud1).isNotEqualTo(estadoSolicitud2);
    }
}
