package repositorio;

import modelo.evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface evaluacionRepo extends JpaRepository<evaluacion, Long> {

}
