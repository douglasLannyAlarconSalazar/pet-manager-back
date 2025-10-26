package com.petmanager.preferencias.backend.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.petmanager.preferencias.backend.dto.ReporteClientesFrecuentesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportePDFService {

    private final ReporteService reporteService;

    /**
     * Genera un reporte PDF de los clientes frecuentes.
     */
    public ByteArrayInputStream generarPDFClientesFrecuentes() throws DocumentException {
        List<ReporteClientesFrecuentesDTO> datos = reporteService.generarReporteClientesFrecuentes();

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        // Encabezado
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
        Paragraph titulo = new Paragraph("Reporte de Clientes Frecuentes", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // Fecha de generación
        Font fontFecha = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);
        Paragraph fecha = new Paragraph("Generado el: " + java.time.LocalDate.now(), fontFecha);
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setSpacingAfter(10);
        document.add(fecha);

        // Tabla de datos
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{2.5f, 1.2f, 1.5f, 2.5f, 2.5f});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);

        // Encabezados con fondo azul
        String[] headers = {"Cliente", "Compras", "Monto Total", "Preferencias", "Productos Favoritos"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headFont));
            cell.setBackgroundColor(new BaseColor(0, 102, 204));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Contenido de la tabla
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

        for (ReporteClientesFrecuentesDTO item : datos) {
            table.addCell(new Phrase(item.getNombre(), cellFont));
            table.addCell(new Phrase(String.valueOf(item.getNumeroCompras()), cellFont));
            table.addCell(new Phrase("$" + item.getTotalGastado(), cellFont));
            table.addCell(new Phrase(String.join(", ", item.getPreferencias()), cellFont));
            table.addCell(new Phrase(String.join(", ", item.getProductosFavoritos()), cellFont));
        }

        document.add(table);

        // Pie de página
        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.GRAY);
        Paragraph footer = new Paragraph("PetManager © " + java.time.Year.now(), fontFooter);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);
        document.add(footer);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
