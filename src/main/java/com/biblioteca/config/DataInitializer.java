package com.biblioteca.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.biblioteca.modelo.Configuracion;
import com.biblioteca.modelo.Libro;
import com.biblioteca.modelo.Rol;
import com.biblioteca.modelo.Socio;
import com.biblioteca.modelo.TipoSocio;
import com.biblioteca.modelo.Usuario;
import com.biblioteca.repositorio.ConfiguracionRepository;
import com.biblioteca.repositorio.LibroRepository;
import com.biblioteca.repositorio.SocioRepository;
import com.biblioteca.repositorio.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final LibroRepository libroRepository;
	private final UsuarioRepository usuarioRepository;
	private final SocioRepository socioRepository;
	private final ConfiguracionRepository configuracionRepository;

	public DataInitializer(LibroRepository libroRepository, UsuarioRepository usuarioRepository,
			SocioRepository socioRepository, ConfiguracionRepository configuracionRepository) {
		this.libroRepository = libroRepository;
		this.usuarioRepository = usuarioRepository;
		this.socioRepository = socioRepository;
		this.configuracionRepository = configuracionRepository;
	}

	@Override
	public void run(String... args) {
		if (usuarioRepository.findByUsername("admin").isEmpty()) {
			Usuario admin = new Usuario();
			admin.setUsername("admin");
			admin.setPassword("admin123");
			admin.setRol(Rol.ADMIN);
			usuarioRepository.save(admin);
		}

		if (usuarioRepository.findByUsername("socio1").isEmpty()) {
			Usuario usuarioSocio = new Usuario();
			usuarioSocio.setUsername("socio1");
			usuarioSocio.setPassword("socio123");
			usuarioSocio.setRol(Rol.SOCIO);
			// el cascade de Socio guarda también el usuario
			Socio socio = new Socio();
			socio.setNombre("Socio Prueba");
			socio.setTipo(TipoSocio.ESTUDIANTE);
			socio.setCorreo("socio1@correo.com");
			socio.setTelefono("5555-0001");
			socio.setUsuario(usuarioSocio);
			socioRepository.save(socio);
		}

		if (configuracionRepository.count() == 0) {
			Configuracion configuracion = new Configuracion();
			configuracion.setMultaPorDia(5.0);
			configuracionRepository.save(configuracion);
		}

		if (libroRepository.count() == 0) {
			crearLibro("Cien años de soledad", "Gabriel García Márquez", "978-0307474728", "Novela");
			crearLibro("El principito", "Antoine de Saint-Exupéry", "978-0156012195", "Infantil");
			crearLibro("Don Quijote de la Mancha", "Miguel de Cervantes", "978-0060934347", "Clásico");
			crearLibro("1984", "George Orwell", "978-0451524935", "Distopía");
			crearLibro("Introducción a los algoritmos", "Thomas H. Cormen", "978-0262033848", "Académico");
		}
	}

	private void crearLibro(String titulo, String autor, String isbn, String categoria) {
		Libro libro = new Libro();
		libro.setTitulo(titulo);
		libro.setAutor(autor);
		libro.setIsbn(isbn);
		libro.setCategoria(categoria);
		libro.setDisponible(true);
		libroRepository.save(libro);
	}

}