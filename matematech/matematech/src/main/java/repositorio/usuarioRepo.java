package repositorio;

import modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuarioRepo extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}