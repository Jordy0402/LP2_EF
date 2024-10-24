package pe.com.cibertec.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pe.com.cibertec.entity.ProductoEntity;
import pe.com.cibertec.entity.UsuarioEntity;
import pe.com.cibertec.service.ProductoService;
import pe.com.cibertec.service.UsuarioService;
import pe.com.cibertec.service.impl.PdfService;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PdfService pdfService;

    // Mostrar el menú de productos
    @GetMapping("/menu")
    public String mostrarMenu(Model model, HttpSession session) {
        // Verificar si hay una sesión de usuario activa
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        String correoSesion = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(correoSesion);
        model.addAttribute("foto", usuarioEncontrado.getUrl_Imagen());

        // Cargar productos
        List<ProductoEntity> listaProductos = productoService.buscarTodosProductos();
        model.addAttribute("productos", listaProductos);
        return "menu"; // Asegúrate de que "menu.html" exista en tus vistas.
    }

    // Método para agregar un nuevo producto (ej: gestión de inventario)
    @PostMapping("/agregar_producto")
    public String agregarProducto(@RequestParam("nombre") String nombre,
                                  @RequestParam("precio") double precio,
                                  @RequestParam("cantidad") int cantidad) {

        ProductoEntity nuevoProducto = new ProductoEntity();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setCantidad(cantidad);

        productoService.guardarProducto(nuevoProducto); // Guarda el nuevo producto
        return "redirect:/menu"; // Después de agregar, redirige al menú
    }

    // Generar PDF con el listado de productos (reporte de inventario)
    @GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource> generarPdf() throws IOException {
        // Obtener todos los productos para el reporte
        List<ProductoEntity> listaProductos = productoService.buscarTodosProductos();

        Map<String, Object> datosPdf = new HashMap<>();
        datosPdf.put("productos", listaProductos);

        ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf); // Usar un template para el PDF

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=productos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfBytes));
    }
}
