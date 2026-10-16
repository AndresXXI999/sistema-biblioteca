package com.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.modelo.Libro;
import com.biblioteca.servicio.LibroService;

@Controller
public class LibroController {

	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}

	@GetMapping("/libros")
	public String listar(@RequestParam(required = false) String campo,
			@RequestParam(required = false) String texto, Model model) {
		model.addAttribute("libros", libroService.buscar(campo, texto));
		model.addAttribute("campo", campo);
		model.addAttribute("texto", texto);
		return "libros/lista";
	}

	@GetMapping("/admin/libros/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("libro", new Libro());
		return "libros/form";
	}

	@PostMapping("/admin/libros/guardar")
	public String guardar(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {
		if (libro.getId() == null) {
			libro.setDisponible(true);
		} else {
			// disponible solo cambia con prestamos y devoluciones, nunca desde el formulario
			Libro existente = libroService.buscarPorId(libro.getId());
			libro.setDisponible(existente.isDisponible());
		}
		libroService.guardar(libro);
		redirectAttributes.addFlashAttribute("mensaje", "Libro guardado correctamente");
		return "redirect:/libros";
	}

	@GetMapping("/admin/libros/editar/{id}")
	public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Libro libro = libroService.buscarPorId(id);
		if (libro == null) {
			redirectAttributes.addFlashAttribute("error", "El libro no existe");
			return "redirect:/libros";
		}
		model.addAttribute("libro", libro);
		return "libros/form";
	}

}
