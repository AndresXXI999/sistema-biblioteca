package com.biblioteca.controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.biblioteca.modelo.Prestamo;
import com.biblioteca.repositorio.PrestamoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReporteController {

	private final PrestamoRepository prestamoRepository;

	public ReporteController(PrestamoRepository prestamoRepository) {
		this.prestamoRepository = prestamoRepository;
	}

	@GetMapping(value = "/admin/reportes/prestamos-activos.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public void prestamosActivos(HttpServletResponse response) throws IOException, DocumentException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=prestamos-activos.pdf");

		Document document = new Document();
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		document.add(new Paragraph("Prestamos activos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
		document.add(new Paragraph("Generado el " + LocalDate.now(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
		document.add(new Paragraph(" "));

		PdfPTable tabla = new PdfPTable(5);
		tabla.setWidthPercentage(100);
		tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
		tabla.addCell("Libro");
		tabla.addCell("Socio");
		tabla.addCell("Fecha de salida");
		tabla.addCell("Fecha limite");
		tabla.addCell("Dias de retraso");
		// la primera fila es el encabezado y se repite si la tabla ocupa más de una página
		tabla.setHeaderRows(1);

		LocalDate hoy = LocalDate.now();
		for (Prestamo prestamo : prestamoRepository.findByFechaDevolucionIsNull()) {
			// sin vencer, la diferencia es negativa y se muestra 0
			long diasRetraso = Math.max(0, ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy));
			tabla.addCell(prestamo.getLibro().getTitulo());
			tabla.addCell(prestamo.getSocio().getNombre());
			tabla.addCell(prestamo.getFechaSalida().toString());
			tabla.addCell(prestamo.getFechaLimite().toString());
			tabla.addCell(String.valueOf(diasRetraso));
		}
		document.add(tabla);

		document.close();
	}
}