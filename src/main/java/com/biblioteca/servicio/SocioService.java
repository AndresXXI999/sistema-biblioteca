package com.biblioteca.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.modelo.Socio;
import com.biblioteca.repositorio.SocioRepository;

@Service
public class SocioService {

	private final SocioRepository socioRepository;

	public SocioService(SocioRepository socioRepository) {
		this.socioRepository = socioRepository;
	}

	public List<Socio> listar() {
		return socioRepository.findAll();
	}

	// null si no existe: el controlador decide qué mostrar
	public Socio buscarPorId(Long id) {
		return socioRepository.findById(id).orElse(null);
	}

	public Socio guardar(Socio socio) {
		return socioRepository.save(socio);
	}

	public Socio buscarPorUsuarioId(Long usuarioId) {
		return socioRepository.findByUsuarioId(usuarioId).orElse(null);
	}

}