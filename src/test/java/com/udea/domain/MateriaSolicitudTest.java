package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriaSolicitudTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriaSolicitud.class);
        MateriaSolicitud materiaSolicitud1 = new MateriaSolicitud();
        materiaSolicitud1.setId(1L);
        MateriaSolicitud materiaSolicitud2 = new MateriaSolicitud();
        materiaSolicitud2.setId(materiaSolicitud1.getId());
        assertThat(materiaSolicitud1).isEqualTo(materiaSolicitud2);
        materiaSolicitud2.setId(2L);
        assertThat(materiaSolicitud1).isNotEqualTo(materiaSolicitud2);
        materiaSolicitud1.setId(null);
        assertThat(materiaSolicitud1).isNotEqualTo(materiaSolicitud2);
    }
}
