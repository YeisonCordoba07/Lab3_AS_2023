package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriaSemestreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriaSemestre.class);
        MateriaSemestre materiaSemestre1 = new MateriaSemestre();
        materiaSemestre1.setId(1L);
        MateriaSemestre materiaSemestre2 = new MateriaSemestre();
        materiaSemestre2.setId(materiaSemestre1.getId());
        assertThat(materiaSemestre1).isEqualTo(materiaSemestre2);
        materiaSemestre2.setId(2L);
        assertThat(materiaSemestre1).isNotEqualTo(materiaSemestre2);
        materiaSemestre1.setId(null);
        assertThat(materiaSemestre1).isNotEqualTo(materiaSemestre2);
    }
}
