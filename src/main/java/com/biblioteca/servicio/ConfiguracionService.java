package com.biblioteca.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.modelo.Configuracion;
import com.biblioteca.repositorio.ConfiguracionRepository;

@Service
public class ConfiguracionService {

	private final ConfiguracionRepository configuracionRepository;

	public ConfiguracionService(ConfiguracionRepository configuracionRepository) {
		this.configuracionRepository = configuracionRepository;
	}

	// la tabla guarda una sola fila de configuración global
	public Configuracion obtener() {
		List<Configuracion> configuraciones = configuracionRepository.findAll();
		if (configuraciones.isEmpty()) {
			Configuracion nueva = new Configuracion();
			nueva.setMultaPorDia(5.0);
			return configuracionRepository.save(nueva);
		}
		return configuraciones.get(0);
	}

	public Configuracion guardar(Configuracion c) {
		if (c.getMultaPorDia() <= 0) {
			throw new IllegalArgumentException("La multa por día debe ser mayor que 0");
		}
		return configuracionRepository.save(c);
	}

}