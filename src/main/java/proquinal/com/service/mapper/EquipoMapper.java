package proquinal.com.service.mapper;


import proquinal.com.domain.*;
import proquinal.com.service.dto.EquipoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipo} and its DTO {@link EquipoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EquipoMapper extends EntityMapper<EquipoDTO, Equipo> {


    @Mapping(target = "usuarios", ignore = true)
    @Mapping(target = "removeUsuarios", ignore = true)
    Equipo toEntity(EquipoDTO equipoDTO);

    default Equipo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equipo equipo = new Equipo();
        equipo.setId(id);
        return equipo;
    }
}
