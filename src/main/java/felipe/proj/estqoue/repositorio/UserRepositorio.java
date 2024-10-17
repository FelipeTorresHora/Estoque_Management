package felipe.proj.estqoue.repositorio;

import felipe.proj.estqoue.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorio extends JpaRepository<User, Long> {
}
