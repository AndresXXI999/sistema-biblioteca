package com.biblioteca.controlador;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biblioteca.modelo.Usuario;
import com.biblioteca.repositorio.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	private final UsuarioRepository usuarioRepository;

	public LoginController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String iniciarSesion(@RequestParam String username, @RequestParam String password,
			HttpSession session, Model model) {
		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);

		if (usuario.isPresent() && contrasenaCoincide(usuario.get(), password)) {
			Usuario u = usuario.get();
			session.setAttribute("usuarioId", u.getId());
			session.setAttribute("username", u.getUsername());
			session.setAttribute("rol", u.getRol().name());
			return "redirect:/";
		}

		model.addAttribute("error", "Usuario o contraseña incorrectos");
		return "login";
	}

	private boolean contrasenaCoincide(Usuario usuario, String password) {
		// en un sistema real se cifraria (BCrypt)
		return usuario.getPassword().equals(password);
	}

}
