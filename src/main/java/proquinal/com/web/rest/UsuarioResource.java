package proquinal.com.web.rest;

import io.github.jhipster.service.filter.StringFilter;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import proquinal.com.criteria.UsuarioCriteria;
import proquinal.com.domain.Usuario;
import proquinal.com.security.AuthoritiesConstants;
import proquinal.com.service.UsuarioService;
import proquinal.com.service.UsuarioServiceQuery;
import proquinal.com.service.dto.BusquedaUsuarioDTO;
import proquinal.com.web.rest.errors.BadRequestAlertException;
import proquinal.com.service.dto.UsuarioDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link proquinal.com.domain.Usuario}.
 */
@RestController
@RequestMapping("/api")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    private static final String ENTITY_NAME = "usuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioService usuarioService;

    private final UsuarioServiceQuery usuarioServiceQuery;

    public UsuarioResource(UsuarioService usuarioService, UsuarioServiceQuery usuarioServiceQuery) {
        this.usuarioService = usuarioService;
        this.usuarioServiceQuery = usuarioServiceQuery;
    }

    /**
     * {@code POST  /usuarios} : Create a new usuario.
     *
     * @param usuarioDTO the usuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioDTO, or with status {@code 400 (Bad Request)} if the usuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuarioDTO);
        if (usuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(usuarioService.findByNumeroDocumentoAndAndEquipo(usuarioDTO).isPresent()){
            throw new BadRequestAlertException("A new usuario cannot already have an NUMERO DOCUMENTO and EQUIPO", ENTITY_NAME, "idequipoexist");
        }
        UsuarioDTO result = usuarioService.save(usuarioDTO);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuarios} : Updates an existing usuario.
     *
     * @param usuarioDTO the usuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> updateUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}", usuarioDTO);
        if (usuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<UsuarioDTO> usuarioTemp = usuarioService.findByNumeroDocumentoAndAndEquipo(usuarioDTO);
        if (usuarioTemp.isPresent() && (!usuarioTemp.get().getId().equals(usuarioDTO.getId()))) {
            throw new BadRequestAlertException("A new usuario cannot already have an NUMERO DOCUMENTO and EQUIPO", ENTITY_NAME, "idequipoexist");
        }
        UsuarioDTO result = usuarioService.save(usuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usuarios} : get all the usuarios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarios in body.
     */
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('"+ AuthoritiesConstants.ADMIN+"')")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios(Pageable pageable) {
        log.debug("REST request to get a page of Usuarios");
        Page<UsuarioDTO> page = usuarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/usuarios/list")
    public ResponseEntity<List<Usuario>> List(@RequestBody BusquedaUsuarioDTO busquedaUsuarioDTO){
        UsuarioCriteria usuarioCriteria = createCriteria(busquedaUsuarioDTO);
        List<Usuario> list = usuarioServiceQuery.findByCriterial(usuarioCriteria);
        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

    private UsuarioCriteria createCriteria(BusquedaUsuarioDTO dto){
        UsuarioCriteria usuarioCriteria = new UsuarioCriteria();
        if(dto!=null){
            if(!StringUtils.isBlank(dto.getNumeroDocumento())){
                StringFilter filter = new StringFilter();
                filter.setEquals(dto.getNumeroDocumento());
                usuarioCriteria.setNumeroDocumento(filter);
            }
            if(!StringUtils.isBlank(dto.getEquipo())){
                StringFilter filter = new StringFilter();
                filter.setEquals(dto.getEquipo());
                usuarioCriteria.setEquipo(filter);
            }
        }
        return usuarioCriteria;
    }

    @GetMapping("/usuarios/list")
    @PreAuthorize("hasRole('"+ AuthoritiesConstants.ADMIN+"')")
    public ResponseEntity<List<Usuario>> list(){
        List<Usuario> list = usuarioServiceQuery.findAll();
        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

    /**
     * {@code GET  /usuarios/:id} : get the "id" usuario.
     *
     * @param id the id of the usuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        log.debug("REST request to get Usuario : {}", id);
        Optional<UsuarioDTO> usuarioDTO = usuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioDTO);
    }

    /**
     * {@code DELETE  /usuarios/:id} : delete the "id" usuario.
     *
     * @param id the id of the usuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('"+ AuthoritiesConstants.ADMIN+"')")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        log.debug("REST request to delete Usuario : {}", id);
        usuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
