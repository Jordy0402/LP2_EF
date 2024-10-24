package pe.com.cibertec.service;
import java.util.List;

import pe.com.cibertec.entity.ProductoEntity;


public interface ProductoService {
    List<ProductoEntity> buscarTodosProductos();
    ProductoEntity buscarProductoPorId(Integer id);
	void guardarProducto(ProductoEntity nuevoProducto);
}