package com.biblioteca.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.modelo.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
