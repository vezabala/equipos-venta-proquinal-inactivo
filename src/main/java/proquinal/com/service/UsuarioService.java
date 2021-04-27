package proquinal.com.service;

import proquinal.com.domain.Usuario;
import proquinal.com.repository.UsuarioRepository;
import proquinal.com.service.dto.UsuarioDTO;
import proquinal.com.service.mapper.UsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Usuario}.
 */
@Service
@Transactional
public class UsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        log.debug("Request to save Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    /**
     * Get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable)
            .map(usuarioMapper::toDto);
    }

    /**
     * Get one usuario by numeroDocumento.
     * @param numeroDocumento
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByNumeroDocumento (String numeroDocumento) {
        log.debug("Request to get Usuario : {}", numeroDocumento);
        return usuarioRepository.findByNumeroDocumento(numeroDocumento)
            .map(usuarioMapper::toDto);
    }

    /**
     * Get one usuario apellidos.
     *
     * @param apellidos
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByApellidos(String apellidos) {
        log.debug("Request to get Usuario : {}", apellidos);
        return usuarioRepository.findByApellidos(apellidos)
            .map(usuarioMapper::toDto);
    }
    /**
     * Get one usuario by equipo.
     *
     * @param equipo
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByEquipo (String equipo) {
        log.debug("Request to get Usuario : {}", equipo);
        return usuarioRepository.findByEquipo(equipo)
            .map(usuarioMapper::toDto);
    }

    /**
     * Get one customer by type document and number document.
     *
     * @param usuarioDTO the DTO of the customer
     * @return the optional with the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findByNumeroDocumentoAndAndEquipo(UsuarioDTO usuarioDTO) {
        log.debug("Request to get Usuario : {}", usuarioDTO.getNumeroDocumento()+", "+usuarioDTO.getEquipoId());
        return usuarioRepository.findByNumeroDocumentoAndAndEquipo(usuarioDTO.getNumeroDocumento(),
            usuarioMapper.toEntity(usuarioDTO).getEquipo())
            .map(usuarioMapper::toDto);
    }

    /**
     * Get one usuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findOne(Long id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id)
            .map(usuarioMapper::toDto);
    }

    /**
     * Delete the usuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
