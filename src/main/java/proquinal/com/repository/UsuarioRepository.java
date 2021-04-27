package proquinal.com.repository;

import proquinal.com.domain.Equipo;
import proquinal.com.domain.Usuario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Usuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>,JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByNumeroDocumento(String numeroDocumento);

    Optional<Usuario> findByApellidos(String apellidos);

    Optional<Usuario> findByEquipo(String equipo);

    Optional<Usuario> findByNumeroDocumentoAndAndEquipo(String NumeroDocumento, Equipo equipo);
}
