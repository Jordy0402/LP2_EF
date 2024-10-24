package pe.com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.com.cibertec.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    // Aquí ya tienes todos los métodos básicos: findAll(), findById(), save(), delete(), etc.
}
