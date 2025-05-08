package org.uv.DAPPPractica03;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author isaias
 */
@Repository
public interface RepositoryEmpleados extends JpaRepository<Empleado, Long> {
    
}
