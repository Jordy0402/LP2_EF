package pe.com.cibertec.service;
import pe.com.cibertec.entity.ProductoEntity;

import java.util.List;

public interface ProductoService {
    List<ProductoEntity> findAll();  // Método para obtener todos los productos
    ProductoEntity save(ProductoEntity producto);  // Método para guardar un producto
    ProductoEntity findById(Long id);  // Método para encontrar un producto por ID
    void delete(Long id);  // Método para eliminar un producto por ID
}