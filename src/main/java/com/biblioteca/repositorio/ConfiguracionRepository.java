package com.biblioteca.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.modelo.Configuracion;

public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {

}