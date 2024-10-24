package pe.com.cibertec.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    
    @GetMapping("/listar_productos")
    public String listarProductos(Model model, HttpSession session) {
   
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        
        

        String correoSesion = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(correoSesion);
        model.addAttribute("nombreUsuario", usuarioEncontrado.getNombre());
        model.addAttribute("foto", usuarioEncontrado.getUrlImagen());

       
        List<ProductoEntity> listaProductos = productoService.buscarTodosProductos();
        model.addAttribute("productos", listaProductos);

        return "listar_productos"; 
    }


    
    @GetMapping("/crear_producto")
    public String mostrarCrearProducto(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        String correoSesion = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(correoSesion);
        model.addAttribute("nombreUsuario", usuarioEncontrado.getNombre());

       
        List<String> categorias = Arrays.asList("Electrónica", "Ropa", "Alimentos", "Hogar", "Salud");
        model.addAttribute("categorias", categorias); 

        return "crear_producto"; 
    }


    @GetMapping("/editar_producto/{id}")
    public String editarProducto(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        String correoSesion = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(correoSesion);
        model.addAttribute("nombreUsuario", usuarioEncontrado.getNombre());

       ProductoEntity producto = productoService.buscarProductoPorId(id.intValue()); // Convertir Long a Integer

        model.addAttribute("producto", producto);

       
        List<String> categorias = Arrays.asList("Electrónica", "Ropa", "Alimentos", "Hogar", "Salud");
        model.addAttribute("categorias", categorias); 

        return "editar_producto";
    }


    
    @GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource> generarPdf() throws IOException {
       
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
