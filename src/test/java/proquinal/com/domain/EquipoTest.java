package proquinal.com.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import proquinal.com.web.rest.TestUtil;

public class EquipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipo.class);
        Equipo equipo1 = new Equipo();
        equipo1.setId(1L);
        Equipo equipo2 = new Equipo();
        equipo2.setId(equipo1.getId());
        assertThat(equipo1).isEqualTo(equipo2);
        equipo2.setId(2L);
        assertThat(equipo1).isNotEqualTo(equipo2);
        equipo1.setId(null);
        assertThat(equipo1).isNotEqualTo(equipo2);
    }
}
