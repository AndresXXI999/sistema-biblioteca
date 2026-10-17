package com.biblioteca.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biblioteca.modelo.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findBySocioIdOrderByFechaSalidaDesc(Long socioId);

	List<Prestamo> findByFechaDevolucionIsNull();

	List<Prestamo> findByMultaGreaterThanAndMultaPagadaFalse(double multa);

	// cuenta por registro de libro (id), así dos libros con el mismo título no se mezclan
	@Query("SELECT p.libro.titulo, COUNT(p) FROM Prestamo p GROUP BY p.libro.id, p.libro.titulo ORDER BY COUNT(p) DESC")
	List<Object[]> contarPrestamosPorLibro();

}