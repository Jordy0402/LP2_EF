package pe.com.cibertec.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import pe.com.cibertec.entity.ProductoEntity;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PdfGeneratorService {
    void export(HttpServletResponse response, List<ProductoEntity> productos, String username) throws DocumentException, IOException;
}
