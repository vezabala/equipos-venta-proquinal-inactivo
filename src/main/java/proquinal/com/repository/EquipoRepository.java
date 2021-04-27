package proquinal.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import proquinal.com.domain.Equipo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Equipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long>,JpaSpecificationExecutor<Equipo> {
    Optional<Equipo> findByActivoFijo(String activoFijo);

    @Query("SELECT e FROM Equipo e WHERE e.tipo ='ESCRITORIO'")
    Page<Equipo> findByTipo(Pageable pageable);

    @Query("SELECT e FROM Equipo e WHERE e.tipo ='PORTATIL'")
    Page<Equipo> findByTipoPortatil(Pageable pageable);

    @Query("SELECT e FROM Equipo e WHERE e.tipo ='ESCRITORIO'")
    Page<Equipo> findAllByTipoEscritorio(@Nullable Specification<Equipo> var1, Pageable var2);

    @Query("SELECT e FROM Equipo e WHERE e.tipo ='PORTATIL'")
    Page<Equipo> findAllByPortatil(@Nullable Specification<Equipo> var1, Pageable var2);
}
