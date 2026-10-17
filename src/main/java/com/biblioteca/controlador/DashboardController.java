package com.biblioteca.controlador;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.biblioteca.modelo.Prestamo;
import com.biblioteca.repositorio.LibroRepository;
import com.biblioteca.repositorio.PrestamoRepository;

@Controller
public class DashboardController {

	private final LibroRepository libroRepository;
	private final PrestamoRepository prestamoRepository;

	public DashboardController(LibroRepository libroRepository, PrestamoRepository prestamoRepository) {
		this.libroRepository = libroRepository;
		this.prestamoRepository = prestamoRepository;
	}

	@GetMapping("/admin/dashboard")
	public String dashboard(Model model) {
		// cada fila es [titulo, cantidad]; solo los 5 primeros
		List<Object[]> topLibros = prestamoRepository.contarPrestamosPorLibro().stream().limit(5).toList();

		// se agrupa por id de socio para que dos socios con el mismo nombre no se mezclen
		Map<Long, String> nombres = new LinkedHashMap<>();
		Map<Long, Double> totales = new LinkedHashMap<>();
		for (Prestamo prestamo : prestamoRepository.findByMultaGreaterThanAndMultaPagadaFalse(0)) {
			Long socioId = prestamo.getSocio().getId();
			nombres.put(socioId, prestamo.getSocio().getNombre());
			    totales.merge(socioId, prestamo.getMulta(), (a, b) -> a + b);
		}
		List<Map<String, Object>> sociosConDeuda = new ArrayList<>();
		for (Long socioId : totales.keySet()) {
			sociosConDeuda.add(Map.of("nombre", nombres.get(socioId), "total", totales.get(socioId)));
		}

		model.addAttribute("topLibros", topLibros);
		model.addAttribute("sociosConDeuda", sociosConDeuda);
		model.addAttribute("libros", libroRepository.findAll());
		return "admin/dashboard";
	}
}