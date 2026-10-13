package com.biblioteca.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.biblioteca.modelo.Libro;
import com.biblioteca.repositorio.LibroRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final LibroRepository libroRepository;

	public DataInitializer(LibroRepository libroRepository) {
		this.libroRepository = libroRepository;
	}

	@Override
	public void run(String... args) {
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
