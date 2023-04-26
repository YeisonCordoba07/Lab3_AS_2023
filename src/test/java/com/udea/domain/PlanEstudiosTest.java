package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanEstudiosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanEstudios.class);
        PlanEstudios planEstudios1 = new PlanEstudios();
        planEstudios1.setId(1L);
        PlanEstudios planEstudios2 = new PlanEstudios();
        planEstudios2.setId(planEstudios1.getId());
        assertThat(planEstudios1).isEqualTo(planEstudios2);
        planEstudios2.setId(2L);
        assertThat(planEstudios1).isNotEqualTo(planEstudios2);
        planEstudios1.setId(null);
        assertThat(planEstudios1).isNotEqualTo(planEstudios2);
    }
}
