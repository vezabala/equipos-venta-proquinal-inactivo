package proquinal.com.service;

import io.github.jhipster.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proquinal.com.criteria.EquipoCriteria;
import proquinal.com.domain.Equipo;
import proquinal.com.domain.Equipo_;
import proquinal.com.repository.EquipoRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EquipoEscritorioServiceQuery extends QueryService<Equipo> {
    @Autowired
    EquipoRepository equipoRepository;

    public Page<Equipo> findByCriterialE(EquipoCriteria equipoCriteria,Pageable pageable) {
        final Specification<Equipo> specification = createSpecification(equipoCriteria);
        Page<Equipo> equipos = equipoRepository.findAllByTipoEscritorio(specification, pageable);
        return equipos;
    }

    private Specification<Equipo> createSpecification(EquipoCriteria criteria) {
        Specification<Equipo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getActivoFijo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getActivoFijo(), Equipo_.activoFijo));
            }
            if (criteria.getState() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getState(), Equipo_.tipo));
            }
        }
        return specification;
    }
}
