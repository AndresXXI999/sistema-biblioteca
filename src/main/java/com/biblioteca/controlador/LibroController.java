package com.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.biblioteca.servicio.LibroService;

@Controller
public class LibroController {

	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}

	@GetMapping("/libros")
	public String listar(Model model) {
		model.addAttribute("libros", libroService.listar());
		return "libros/lista";
	}

}
