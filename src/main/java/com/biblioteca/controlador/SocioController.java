package com.biblioteca.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.modelo.Rol;
import com.biblioteca.modelo.Socio;
import com.biblioteca.modelo.Usuario;
import com.biblioteca.repositorio.UsuarioRepository;
import com.biblioteca.servicio.SocioService;

@Controller
public class SocioController {

	private final SocioService socioService;
	private final UsuarioRepository usuarioRepository;

	public SocioController(SocioService socioService, UsuarioRepository usuarioRepository) {
		this.socioService = socioService;
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping("/admin/socios")
	public String listar(Model model) {
		model.addAttribute("socios", socioService.listar());
		return "socios/lista";
	}

	@GetMapping("/admin/socios/nuevo")
	public String nuevo(Model model) {
		Socio socio = new Socio();
		// el formulario enlaza usuario.username y usuario.password, así que el objeto debe existir
		socio.setUsuario(new Usuario());
		model.addAttribute("socio", socio);
		return "socios/form";
	}

	@GetMapping("/admin/socios/editar/{id}")
	public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Socio socio = socioService.buscarPorId(id);
		if (socio == null) {
			flash.addFlashAttribute("error", "El socio no existe");
			return "redirect:/admin/socios";
		}
		model.addAttribute("socio", socio);
		return "socios/form";
	}

	@PostMapping("/admin/socios/guardar")
	public String guardar(Socio socio, RedirectAttributes flash) {
		if (socio.getId() == null) {
			String username = socio.getUsuario().getUsername();
			if (usuarioRepository.findByUsername(username).isPresent()) {
				flash.addFlashAttribute("error", "El usuario ya existe");
				return "redirect:/admin/socios/nuevo";
			}
			socio.getUsuario().setRol(Rol.SOCIO);
			socioService.guardar(socio);
		} else {
			Socio existente = socioService.buscarPorId(socio.getId());
			if (existente == null) {
				flash.addFlashAttribute("error", "El socio no existe");
				return "redirect:/admin/socios";
			}
			existente.setNombre(socio.getNombre());
			existente.setTipo(socio.getTipo());
			existente.setCorreo(socio.getCorreo());
			existente.setTelefono(socio.getTelefono());
			socioService.guardar(existente);
		}
		flash.addFlashAttribute("mensaje", "Socio guardado");
		return "redirect:/admin/socios";
	}
}