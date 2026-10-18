package com.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.biblioteca.modelo.Socio;
import com.biblioteca.servicio.PrestamoService;
import com.biblioteca.servicio.SocioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HistorialController {

	private final SocioService socioService;
	private final PrestamoService prestamoService;

	public HistorialController(SocioService socioService, PrestamoService prestamoService) {
		this.socioService = socioService;
		this.prestamoService = prestamoService;
	}

	@GetMapping("/socio/historial")
	public String historialPropio(HttpSession session, Model model) {
		Long uid = (Long) session.getAttribute("usuarioId");
		Socio s = socioService.buscarPorUsuarioId(uid);
		model.addAttribute("prestamos", prestamoService.listarPorSocio(s.getId()));
		model.addAttribute("socio", s);
		return "historial/lista";
	}

	@GetMapping("/admin/socios/{id}/historial")
	public String historialDeSocio(@PathVariable Long id, Model model) {
		Socio s = socioService.buscarPorId(id);
		model.addAttribute("prestamos", prestamoService.listarPorSocio(id));
		model.addAttribute("socio", s);
		return "historial/lista";
	}

}