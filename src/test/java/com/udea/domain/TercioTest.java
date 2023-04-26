package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TercioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tercio.class);
        Tercio tercio1 = new Tercio();
        tercio1.setId(1L);
        Tercio tercio2 = new Tercio();
        tercio2.setId(tercio1.getId());
        assertThat(tercio1).isEqualTo(tercio2);
        tercio2.setId(2L);
        assertThat(tercio1).isNotEqualTo(tercio2);
        tercio1.setId(null);
        assertThat(tercio1).isNotEqualTo(tercio2);
    }
}
