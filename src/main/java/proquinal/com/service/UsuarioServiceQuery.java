package proquinal.com.service;

import io.github.jhipster.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proquinal.com.criteria.UsuarioCriteria;
import proquinal.com.domain.Equipo_;
import proquinal.com.domain.Usuario;
import proquinal.com.domain.Usuario_;
import proquinal.com.repository.UsuarioRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceQuery extends QueryService<Usuario> {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> findByCriterial (UsuarioCriteria usuarioCriteria){
        final Specification<Usuario> specification = createSpecification(usuarioCriteria);
        List<Usuario> usuarios = usuarioRepository.findAll(specification);
        return usuarios;
    }
    private Specification<Usuario> createSpecification (UsuarioCriteria criteria){
        Specification<Usuario> specification = Specification.where(null);
        if (criteria != null ){
            if(criteria.getNumeroDocumento() != null){
                specification =
                    specification.and(buildStringSpecification(criteria.getNumeroDocumento() , Usuario_.numeroDocumento));
            }
            if(criteria.getEquipo() != null){
                specification =
                    specification.and(buildReferringEntitySpecification(criteria.getEquipo(), Usuario_.equipo , Equipo_.activoFijo));
            }
        }
        return specification;
    }
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
}
