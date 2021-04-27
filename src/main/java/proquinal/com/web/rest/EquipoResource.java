package proquinal.com.web.rest;

import io.github.jhipster.service.filter.StringFilter;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import proquinal.com.criteria.EquipoCriteria;
import proquinal.com.domain.Equipo;
import proquinal.com.domain.enumeration.State;
import proquinal.com.repository.EquipoRepository;
import proquinal.com.security.AuthoritiesConstants;
import proquinal.com.service.EquipoEscritorioServiceQuery;
import proquinal.com.service.EquipoPortatilServiceQuery;
import proquinal.com.service.EquipoService;
import proquinal.com.service.EquipoServiceQuery;
import proquinal.com.service.dto.BusquedaEquipoDTO;
import proquinal.com.web.rest.errors.BadRequestAlertException;
import proquinal.com.service.dto.EquipoDTO;

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
 * REST controller for managing {@link proquinal.com.domain.Equipo}.
 */
@RestController
@RequestMapping("/api")
public class EquipoResource {

    private final Logger log = LoggerFactory.getLogger(EquipoResource.class);

    private static final String ENTITY_NAME = "equipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipoService equipoService;

    private final EquipoServiceQuery equipoServiceQuery;

    private final EquipoEscritorioServiceQuery equipoEscritorioServiceQuery;

    private final EquipoPortatilServiceQuery equipoPortatiloServiceQuery;

    public EquipoResource(EquipoService equipoService, EquipoServiceQuery equipoServiceQuery, EquipoEscritorioServiceQuery equipoEscritorioServiceQuery, EquipoPortatilServiceQuery equipoPortatiloServiceQuery, EquipoRepository equipoRepository) {
        this.equipoService = equipoService;
        this.equipoServiceQuery = equipoServiceQuery;
        this.equipoEscritorioServiceQuery = equipoEscritorioServiceQuery;
        this.equipoPortatiloServiceQuery = equipoPortatiloServiceQuery;
        this.equipoRepository = equipoRepository;
    }

    private EquipoRepository equipoRepository;

    /**
     * {@code POST  /equipos} : Create a new equipo.
     *
     * @param equipoDTO the equipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipoDTO, or with status {@code 400 (Bad Request)} if the equipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipos")
    @PreAuthorize("hasRole('"+ AuthoritiesConstants.ADMIN+"')")
    public ResponseEntity<EquipoDTO> createEquipo(@Valid @RequestBody EquipoDTO equipoDTO) throws URISyntaxException {
        log.debug("REST request to save Equipo : {}", equipoDTO);
        if (equipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (equipoService.findByActivoFijo(equipoDTO.getActivoFijo()).isPresent()) {
            throw new BadRequestAlertException("A new equipo cannot already have an ACTIVOFIJO", ENTITY_NAME, "activofijoexists");
        }
        EquipoDTO result = equipoService.save(equipoDTO);
        return ResponseEntity.created(new URI("/api/equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipos} : Updates an existing equipo.
     *
     * @param equipoDTO the equipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipoDTO,
     * or with status {@code 400 (Bad Request)} if the equipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipos")
    @PreAuthorize("hasRole('"+ AuthoritiesConstants.ADMIN+"')")
    public ResponseEntity<EquipoDTO> updateEquipo(@Valid @RequestBody EquipoDTO equipoDTO) throws URISyntaxException {
        log.debug("REST request to update Equipo : {}", equipoDTO);
        if (equipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<EquipoDTO> equipoTemp = equipoService.findByActivoFijo(equipoDTO.getActivoFijo());
        if (equipoTemp.isPresent())
            if (!equipoTemp.get().getId().equals(equipoDTO.getId())) {
                throw new BadRequestAlertException("A new equipo cannot already have an ACTIVOFIJO", ENTITY_NAME, "activofijoexists");
            }
        EquipoDTO result = equipoService.save(equipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipos} : get all the equipos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipos in body.
     */
    @GetMapping("/equipos")
    public ResponseEntity<List<EquipoDTO>> getAllEquipos(Pageable pageable) {
        log.debug("REST request to get a page of Equipos");
        Page<EquipoDTO> page = equipoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/equiposEscritorio")
    @Timed
    public ResponseEntity<List<Equipo>> getAllEquiposEscritorio(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EquiposEscritorio");
        Page<Equipo> page = equipoRepository.findByTipo(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/equiposPortatil")
    @Timed
    public ResponseEntity<List<Equipo>> getAllEquiposPortatil(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EquiposPortatil");
        Page<Equipo> page = equipoRepository.findByTipoPortatil(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/equipos/list")
    public ResponseEntity<List<Equipo>> List(@RequestBody BusquedaEquipoDTO busquedaEquipoDTO){
        EquipoCriteria equipoCriteria = createCriteria(busquedaEquipoDTO);
        List<Equipo> list = equipoServiceQuery.findByCriterial(equipoCriteria);
        return new ResponseEntity<List<Equipo>>(list, HttpStatus.OK);
    }

    @PostMapping("/equipos/listE")
    public ResponseEntity<List<Equipo>> ListE(@ApiParam Pageable pageable,@RequestBody BusquedaEquipoDTO busquedaEquipoDTO){
        log.debug("REST request to get a page of EquiposEscri");
        EquipoCriteria equipoCriteria = createCriteria(busquedaEquipoDTO);
        Page<Equipo> page = equipoEscritorioServiceQuery.findByCriterialE(equipoCriteria,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/equipos/listP")
    public ResponseEntity<List<Equipo>> ListP(@ApiParam Pageable pageable,@RequestBody BusquedaEquipoDTO busquedaEquipoDTO){
        log.debug("REST request to get a page of EquiposEscri");
        EquipoCriteria equipoCriteria = createCriteria(busquedaEquipoDTO);
        Page<Equipo> page = equipoPortatiloServiceQuery.findByCriterialP(equipoCriteria,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    private EquipoCriteria createCriteria(BusquedaEquipoDTO dtoE){
        EquipoCriteria equipoCriteria = new EquipoCriteria();
        if(dtoE!=null){
            if(!StringUtils.isBlank(dtoE.getActivoFijo())){
                StringFilter filter = new StringFilter();
                filter.setEquals(dtoE.getActivoFijo());
                equipoCriteria.setActivoFijo(filter);
            }
            if(!StringUtils.isBlank(dtoE.getTipo())){
                EquipoCriteria.StateFilter filter = new EquipoCriteria.StateFilter();
                String tipo = dtoE.getTipo().toUpperCase();
                filter.setEquals(State.valueOf(tipo));
                equipoCriteria.setState(filter);
            }
        }
        return equipoCriteria;
    }

    @GetMapping("/equipos/list")
    public ResponseEntity<List<Equipo>> list(){
        List<Equipo> list = equipoServiceQuery.findAll();
        return new ResponseEntity<List<Equipo>>(list, HttpStatus.OK);
    }

    /**
     * {@code GET  /equipos/:id} : get the "id" equipo.
     *
     * @param id the id of the equipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipos/{id}")
    public ResponseEntity<EquipoDTO> getEquipo(@PathVariable Long id) {
        log.debug("REST request to get Equipo : {}", id);
        Optional<EquipoDTO> equipoDTO = equipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipoDTO);
    }

    /**
     * {@code DELETE  /equipos/:id} : delete the "id" equipo.
     *
     * @param id the id of the equipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipos/{id}")
    @PreAuthorize("hasRole('"+ AuthoritiesConstants.ADMIN+"')")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        log.debug("REST request to delete Equipo : {}", id);
        equipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
