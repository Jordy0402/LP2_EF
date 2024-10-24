package pe.com.cibertec.controller;

import com.lowagie.text.DocumentException;
import pe.com.cibertec.entity.ProductoEntity;
import pe.com.cibertec.service.PdfGeneratorService;
import pe.com.cibertec.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/export")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=productos.pdf");

        // Obtener la lista de productos
        List<ProductoEntity> productList = productoService.findAll();

        // Supongamos que tenemos el nombre del usuario autenticado
        String username = "usuarioEjemplo"; // Cambia esto por el usuario real

        // Generar el PDF
        pdfGeneratorService.export(response, productList, username);
    }
}
