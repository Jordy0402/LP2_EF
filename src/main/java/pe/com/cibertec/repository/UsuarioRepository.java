package pe.com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.com.cibertec.entity.UsuarioEntity;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);  // Para buscar usuarios por correo durante el login
}
