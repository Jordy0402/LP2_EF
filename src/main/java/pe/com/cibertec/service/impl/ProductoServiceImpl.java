package pe.com.cibertec.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.cibertec.entity.ProductoEntity;
import pe.com.cibertec.repository.ProductoRepository;
import pe.com.cibertec.service.ProductoService;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;  // Asegúrate de tener este repositorio

    @Override
    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();  // Obtener todos los productos
    }

    @Override
    public ProductoEntity save(ProductoEntity producto) {
        return productoRepository.save(producto);  // Guardar el producto
    }

    @Override
    public ProductoEntity findById(Long id) {
        return productoRepository.findById(id).orElse(null);  // Encontrar producto por ID
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);  // Eliminar producto por ID
    }
}