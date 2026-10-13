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

}
