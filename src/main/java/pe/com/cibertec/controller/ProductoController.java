package pe.com.cibertec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import pe.com.cibertec.entity.ProductoEntity;
import pe.com.cibertec.entity.UsuarioEntity;
import pe.com.cibertec.repository.ProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar productos
    @GetMapping
    public String listarProductos(Model model, HttpSession session) {
        List<ProductoEntity> productos = productoRepository.findAll();
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        model.addAttribute("nombreUsuario", usuario.getNombres());
        model.addAttribute("productos", productos);
        return "listarProductos";  // Página con la tabla de productos
    }

    // Mostrar formulario para crear producto
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearProducto(Model model, HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        model.addAttribute("nombreUsuario", usuario.getNombres());
        model.addAttribute("producto", new ProductoEntity());
        return "crearProducto";
    }

    // Crear nuevo producto
    @PostMapping("/nuevo")
    public String crearProducto(ProductoEntity producto) {
        productoRepository.save(producto);
        return "redirect:/productos";  // Redirige a la lista de productos
    }

    // Mostrar formulario para editar producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable("id") Long id, Model model, HttpSession session) {
        ProductoEntity producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        model.addAttribute("nombreUsuario", usuario.getNombres());
        model.addAttribute("producto", producto);
        return "editarProducto";
    }

    // Actualizar producto
    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable("id") Long id, ProductoEntity producto) {
        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        ProductoEntity producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        productoRepository.delete(producto);
        return "redirect:/productos";
    }

    // Generar PDF con productos (implementación básica)
    @GetMapping("/pdf")
    public String generarPDF(Model model, HttpSession session) {
        List<ProductoEntity> productos = productoRepository.findAll();
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuarioLogueado");
        model.addAttribute("nombreUsuario", usuario.getNombres());
        model.addAttribute("productos", productos);
        return "pdfTemplate";  // Este template sería el que genere el PDF
    }
}
