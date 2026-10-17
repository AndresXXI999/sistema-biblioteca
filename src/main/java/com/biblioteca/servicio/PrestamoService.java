package com.biblioteca.servicio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.modelo.Libro;
import com.biblioteca.modelo.Prestamo;
import com.biblioteca.modelo.Socio;
import com.biblioteca.repositorio.LibroRepository;
import com.biblioteca.repositorio.PrestamoRepository;
import com.biblioteca.repositorio.SocioRepository;

@Service
public class PrestamoService {

	private final PrestamoRepository prestamoRepository;
	private final LibroRepository libroRepository;
	private final SocioRepository socioRepository;
	private final ConfiguracionService configuracionService;

	public PrestamoService(PrestamoRepository prestamoRepository, LibroRepository libroRepository,
			SocioRepository socioRepository, ConfiguracionService configuracionService) {
		this.prestamoRepository = prestamoRepository;
		this.libroRepository = libroRepository;
		this.socioRepository = socioRepository;
		this.configuracionService = configuracionService;
	}

	// transaccional: el préstamo y el cambio de disponibilidad se guardan juntos o ninguno
	@Transactional
	public void registrarSalida(Long libroId, Long socioId) {
		Libro libro = libroRepository.findById(libroId).orElse(null);
		if (libro == null || !libro.isDisponible()) {
			throw new RuntimeException("Libro no disponible");
		}
		Socio socio = socioRepository.findById(socioId).orElse(null);
		if (socio == null) {
			throw new RuntimeException("Socio no existe");
		}
		Prestamo prestamo = new Prestamo();
		prestamo.setLibro(libro);
		prestamo.setSocio(socio);
		prestamo.setFechaSalida(LocalDate.now());
		prestamo.setFechaLimite(LocalDate.now().plusDays(7));
		libro.setDisponible(false);
		libroRepository.save(libro);
		prestamoRepository.save(prestamo);
	}

	@Transactional
	public void registrarDevolucion(Long prestamoId) {
		Prestamo prestamo = prestamoRepository.findById(prestamoId).orElse(null);
		if (prestamo == null) {
			return;
		}
		prestamo.setFechaDevolucion(LocalDate.now());
		prestamo.setMulta(calcularMulta(prestamo.getFechaLimite(), prestamo.getFechaDevolucion()));
		Libro libro = prestamo.getLibro();
		libro.setDisponible(true);
		libroRepository.save(libro);
		prestamoRepository.save(prestamo);
	}

	// la multa se calcula solo al devolver; si no hay retraso, los días quedan en 0
	public double calcularMulta(LocalDate limite, LocalDate devolucion) {
		long dias = ChronoUnit.DAYS.between(limite, devolucion);
		if (dias < 0) {
			dias = 0;
		}
		double tarifa = configuracionService.obtener().getMultaPorDia();
		return dias * tarifa;
	}

	public void marcarMultaPagada(Long prestamoId) {
		Prestamo prestamo = prestamoRepository.findById(prestamoId).orElse(null);
		if (prestamo == null) {
			return;
		}
		prestamo.setMultaPagada(true);
		prestamoRepository.save(prestamo);
	}

	public List<Prestamo> listarActivos() {
		return prestamoRepository.findByFechaDevolucionIsNull();
	}

	public List<Prestamo> listarMultasPendientes() {
		return prestamoRepository.findByMultaGreaterThanAndMultaPagadaFalse(0);
	}

	public List<Prestamo> listarPorSocio(Long socioId) {
		return prestamoRepository.findBySocioIdOrderByFechaSalidaDesc(socioId);
	}
}