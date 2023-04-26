package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoSemestreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSemestre.class);
        TipoSemestre tipoSemestre1 = new TipoSemestre();
        tipoSemestre1.setId(1L);
        TipoSemestre tipoSemestre2 = new TipoSemestre();
        tipoSemestre2.setId(tipoSemestre1.getId());
        assertThat(tipoSemestre1).isEqualTo(tipoSemestre2);
        tipoSemestre2.setId(2L);
        assertThat(tipoSemestre1).isNotEqualTo(tipoSemestre2);
        tipoSemestre1.setId(null);
        assertThat(tipoSemestre1).isNotEqualTo(tipoSemestre2);
    }
}
