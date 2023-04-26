package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoriaAcademicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriaAcademica.class);
        HistoriaAcademica historiaAcademica1 = new HistoriaAcademica();
        historiaAcademica1.setId(1L);
        HistoriaAcademica historiaAcademica2 = new HistoriaAcademica();
        historiaAcademica2.setId(historiaAcademica1.getId());
        assertThat(historiaAcademica1).isEqualTo(historiaAcademica2);
        historiaAcademica2.setId(2L);
        assertThat(historiaAcademica1).isNotEqualTo(historiaAcademica2);
        historiaAcademica1.setId(null);
        assertThat(historiaAcademica1).isNotEqualTo(historiaAcademica2);
    }
}
