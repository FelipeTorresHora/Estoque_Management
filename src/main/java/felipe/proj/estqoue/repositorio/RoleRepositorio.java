package felipe.proj.estqoue.repositorio;

import felipe.proj.estqoue.entidades.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositorio extends JpaRepository<Role, Long> {
}
