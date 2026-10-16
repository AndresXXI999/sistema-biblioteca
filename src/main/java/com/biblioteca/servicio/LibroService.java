package com.biblioteca.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.modelo.Libro;
import com.biblioteca.repositorio.LibroRepository;

@Service
public class LibroService {

	private final LibroRepository libroRepository;

	public LibroService(LibroRepository libroRepository) {
		this.libroRepository = libroRepository;
	}

	public List<Libro> listar() {
		return libroRepository.findAll();
	}

	public Libro buscarPorId(Long id) {
		return libroRepository.findById(id).orElse(null);
	}

	public Libro guardar(Libro libro) {
		return libroRepository.save(libro);
	}

	public List<Libro> buscar(String campo, String texto) {
		if (campo == null || texto == null || texto.isBlank()) {
			return listar();
		}
		return switch (campo) {
			case "titulo" -> libroRepository.findByTituloContainingIgnoreCase(texto);
			case "autor" -> libroRepository.findByAutorContainingIgnoreCase(texto);
			case "isbn" -> libroRepository.findByIsbnContainingIgnoreCase(texto);
			case "categoria" -> libroRepository.findByCategoriaContainingIgnoreCase(texto);
			default -> listar();
		};
	}

}