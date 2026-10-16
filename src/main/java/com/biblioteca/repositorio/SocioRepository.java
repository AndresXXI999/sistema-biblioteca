package com.biblioteca.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.modelo.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long> {

	Optional<Socio> findByUsuarioId(Long usuarioId);

}