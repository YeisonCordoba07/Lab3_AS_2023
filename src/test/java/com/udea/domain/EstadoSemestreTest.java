package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadoSemestreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadoSemestre.class);
        EstadoSemestre estadoSemestre1 = new EstadoSemestre();
        estadoSemestre1.setId(1L);
        EstadoSemestre estadoSemestre2 = new EstadoSemestre();
        estadoSemestre2.setId(estadoSemestre1.getId());
        assertThat(estadoSemestre1).isEqualTo(estadoSemestre2);
        estadoSemestre2.setId(2L);
        assertThat(estadoSemestre1).isNotEqualTo(estadoSemestre2);
        estadoSemestre1.setId(null);
        assertThat(estadoSemestre1).isNotEqualTo(estadoSemestre2);
    }
}
