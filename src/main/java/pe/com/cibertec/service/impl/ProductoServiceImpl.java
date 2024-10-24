package pe.com.cibertec.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.entity.ProductoEntity;
import pe.com.cibertec.repository.ProductoRepository;
import pe.com.cibertec.service.ProductoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoEntity> buscarTodosProductos() {
        return productoRepository.findAll();
    }


    @Override
    public ProductoEntity buscarProductoPorId(Integer id) {
        return productoRepository.findById(id).get();
    }


	@Override
	public void guardarProducto(ProductoEntity nuevoProducto) {
		// TODO Auto-generated method stub
		
	}
}