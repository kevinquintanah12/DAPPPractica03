package org.uv.DAPPPractica03;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDepartamentos extends JpaRepository<Departamento, Long> {
    
}
