package com.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.modelo.Configuracion;
import com.biblioteca.servicio.ConfiguracionService;

@Controller
public class ConfiguracionController {

	private final ConfiguracionService configuracionService;

	public ConfiguracionController(ConfiguracionService configuracionService) {
		this.configuracionService = configuracionService;
	}

	@GetMapping("/admin/configuracion")
	public String mostrar(Model model) {
		model.addAttribute("configuracion", configuracionService.obtener());
		return "admin/configuracion";
	}

	@PostMapping("/admin/configuracion/guardar")
	public String guardar(@RequestParam double multaPorDia, RedirectAttributes flash) {
		if (multaPorDia <= 0) {
			flash.addFlashAttribute("error", "La multa por día debe ser mayor que 0");
			return "redirect:/admin/configuracion";
		}
		// el formulario no envía el id: se actualiza la única fila, no se crea otra
		Configuracion configuracion = configuracionService.obtener();
		configuracion.setMultaPorDia(multaPorDia);
		configuracionService.guardar(configuracion);
		flash.addFlashAttribute("mensaje", "Configuración guardada");
		return "redirect:/admin/configuracion";
	}
}