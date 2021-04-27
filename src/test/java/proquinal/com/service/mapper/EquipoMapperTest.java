package proquinal.com.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EquipoMapperTest {

    private EquipoMapper equipoMapper;

    @BeforeEach
    public void setUp() {
        equipoMapper = new EquipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(equipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(equipoMapper.fromId(null)).isNull();
    }
}
