package proquinal.com.service.mapper;


import proquinal.com.domain.*;
import proquinal.com.service.dto.UsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {EquipoMapper.class})
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {

    @Mapping(source = "equipo.id", target = "equipoId")
    @Mapping(source = "equipo.activoFijo", target = "equipoActivoFijo")
    UsuarioDTO toDto(Usuario usuario);

    @Mapping(source = "equipoId", target = "equipo")
    Usuario toEntity(UsuarioDTO usuarioDTO);

    default Usuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
