package proquinal.com.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import proquinal.com.web.rest.TestUtil;

public class EquipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipoDTO.class);
        EquipoDTO equipoDTO1 = new EquipoDTO();
        equipoDTO1.setId(1L);
        EquipoDTO equipoDTO2 = new EquipoDTO();
        assertThat(equipoDTO1).isNotEqualTo(equipoDTO2);
        equipoDTO2.setId(equipoDTO1.getId());
        assertThat(equipoDTO1).isEqualTo(equipoDTO2);
        equipoDTO2.setId(2L);
        assertThat(equipoDTO1).isNotEqualTo(equipoDTO2);
        equipoDTO1.setId(null);
        assertThat(equipoDTO1).isNotEqualTo(equipoDTO2);
    }
}
