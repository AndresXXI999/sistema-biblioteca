package com.biblioteca.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.modelo.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findBySocioIdOrderByFechaSalidaDesc(Long socioId);

	List<Prestamo> findByFechaDevolucionIsNull();

	List<Prestamo> findByMultaGreaterThanAndMultaPagadaFalse(double multa);

}
