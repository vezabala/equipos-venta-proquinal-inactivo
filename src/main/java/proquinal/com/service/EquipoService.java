package proquinal.com.service;

import proquinal.com.domain.Equipo;
import proquinal.com.repository.EquipoRepository;
import proquinal.com.service.dto.EquipoDTO;
import proquinal.com.service.mapper.EquipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Equipo}.
 */
@Service
@Transactional
public class EquipoService {

    private final Logger log = LoggerFactory.getLogger(EquipoService.class);

    private final EquipoRepository equipoRepository;

    private final EquipoMapper equipoMapper;

    public EquipoService(EquipoRepository equipoRepository, EquipoMapper equipoMapper) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
    }

    /**
     * Save a equipo.
     *
     * @param equipoDTO the entity to save.
     * @return the persisted entity.
     */
    public EquipoDTO save(EquipoDTO equipoDTO) {
        log.debug("Request to save Equipo : {}", equipoDTO);
        Equipo equipo = equipoMapper.toEntity(equipoDTO);
        equipo = equipoRepository.save(equipo);
        return equipoMapper.toDto(equipo);
    }

    /**
     * Get all the equipos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EquipoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipos");
        return equipoRepository.findAll(pageable)
            .map(equipoMapper::toDto);
    }

    /**
     * Get one equipo by activoFijo.
     * @param activoFijo
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<EquipoDTO> findByActivoFijo (String activoFijo) {
        log.debug("Request to get Equipo : {}", activoFijo);
        return equipoRepository.findByActivoFijo(activoFijo)
            .map(equipoMapper::toDto);
    }

    /**
     * Get one equipo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EquipoDTO> findOne(Long id) {
        log.debug("Request to get Equipo : {}", id);
        return equipoRepository.findById(id)
            .map(equipoMapper::toDto);
    }

    /**
     * Delete the equipo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Equipo : {}", id);
        equipoRepository.deleteById(id);
    }
}
