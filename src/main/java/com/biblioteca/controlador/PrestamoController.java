package com.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.servicio.LibroService;
import com.biblioteca.servicio.PrestamoService;
import com.biblioteca.servicio.SocioService;

@Controller
public class PrestamoController {

	private final PrestamoService prestamoService;
	private final LibroService libroService;
	private final SocioService socioService;

	public PrestamoController(PrestamoService prestamoService, LibroService libroService,
			SocioService socioService) {
		this.prestamoService = prestamoService;
		this.libroService = libroService;
		this.socioService = socioService;
	}

	@GetMapping("/admin/prestamos")
	public String listar(Model model) {
		model.addAttribute("activos", prestamoService.listarActivos());
		model.addAttribute("multasPendientes", prestamoService.listarMultasPendientes());
		return "prestamos/lista";
	}

	@GetMapping("/admin/prestamos/nuevo")
	public String nuevo(Model model) {
		// solo se pueden prestar libros disponibles
		model.addAttribute("libros", libroService.listar().stream().filter(libro -> libro.isDisponible()).toList());
		model.addAttribute("socios", socioService.listar());
		return "prestamos/form";
	}

	@PostMapping("/admin/prestamos/guardar")
	public String guardar(@RequestParam Long libroId, @RequestParam Long socioId, RedirectAttributes flash) {
		try {
			prestamoService.registrarSalida(libroId, socioId);
			flash.addFlashAttribute("mensaje", "Préstamo registrado");
		} catch (RuntimeException e) {
			flash.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/prestamos";
	}

	@PostMapping("/admin/prestamos/devolver/{id}")
	public String devolver(@PathVariable Long id, RedirectAttributes flash) {
		prestamoService.registrarDevolucion(id);
		flash.addFlashAttribute("mensaje", "Devolución registrada");
		return "redirect:/admin/prestamos";
	}

	@PostMapping("/admin/prestamos/pagar-multa/{id}")
	public String pagarMulta(@PathVariable Long id, RedirectAttributes flash) {
		prestamoService.marcarMultaPagada(id);
		flash.addFlashAttribute("mensaje", "Multa marcada como pagada");
		return "redirect:/admin/prestamos";
	}
}