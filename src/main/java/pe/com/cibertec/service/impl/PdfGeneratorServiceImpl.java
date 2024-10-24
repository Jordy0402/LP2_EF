package pe.com.cibertec.service.impl;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pe.com.cibertec.entity.ProductoEntity;
import pe.com.cibertec.service.PdfGeneratorService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    @Override
    public void export(HttpServletResponse response, List<ProductoEntity> productos, String username) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Productos Generados por: " + username));
        document.add(new Paragraph(" ")); // Espacio en blanco

        // Agregar productos al documento
        for (ProductoEntity producto : productos) {
            document.add(new Paragraph("ID: " + producto.getId()));
            document.add(new Paragraph("Nombre: " + producto.getNombre())); // Asegúrate de tener este método en ProductoEntity
            document.add(new Paragraph("Precio: " + producto.getPrecio())); // Asegúrate de tener este método en ProductoEntity
            document.add(new Paragraph("Cantidad: " + producto.getCantidad())); // Asegúrate de tener este método en ProductoEntity
            document.add(new Paragraph(" ")); // Espacio en blanco entre productos
        }

        document.close();
    }
}
