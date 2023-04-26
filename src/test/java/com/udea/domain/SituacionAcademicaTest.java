package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SituacionAcademicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituacionAcademica.class);
        SituacionAcademica situacionAcademica1 = new SituacionAcademica();
        situacionAcademica1.setId(1L);
        SituacionAcademica situacionAcademica2 = new SituacionAcademica();
        situacionAcademica2.setId(situacionAcademica1.getId());
        assertThat(situacionAcademica1).isEqualTo(situacionAcademica2);
        situacionAcademica2.setId(2L);
        assertThat(situacionAcademica1).isNotEqualTo(situacionAcademica2);
        situacionAcademica1.setId(null);
        assertThat(situacionAcademica1).isNotEqualTo(situacionAcademica2);
    }
}
