package com.biblioteca.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.modelo.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro>findByTituloContainingIgnoreCase(String texto);
    List<Libro>findByAutorContainingIgnoreCase(String texto);
    List<Libro>findByIsbnContainingIgnoreCase(String texto);
    List<Libro>findByCategoriaContainingIgnoreCase(String texto);

}
