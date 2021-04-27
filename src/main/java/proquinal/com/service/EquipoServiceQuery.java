package proquinal.com.service;

import io.github.jhipster.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proquinal.com.criteria.EquipoCriteria;
import proquinal.com.domain.Equipo_;
import proquinal.com.domain.enumeration.State;
import proquinal.com.repository.EquipoRepository;
import proquinal.com.domain.Equipo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EquipoServiceQuery extends QueryService<Equipo> {
    @Autowired
    EquipoRepository equipoRepository;

    public List<Equipo> findByCriterial(EquipoCriteria equipoCriteria) {
        final Specification<Equipo> specification = createSpecification(equipoCriteria);
        List<Equipo> equipos = equipoRepository.findAll(specification);
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

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }
}
