package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramaAcademicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramaAcademico.class);
        ProgramaAcademico programaAcademico1 = new ProgramaAcademico();
        programaAcademico1.setId(1L);
        ProgramaAcademico programaAcademico2 = new ProgramaAcademico();
        programaAcademico2.setId(programaAcademico1.getId());
        assertThat(programaAcademico1).isEqualTo(programaAcademico2);
        programaAcademico2.setId(2L);
        assertThat(programaAcademico1).isNotEqualTo(programaAcademico2);
        programaAcademico1.setId(null);
        assertThat(programaAcademico1).isNotEqualTo(programaAcademico2);
    }
}
