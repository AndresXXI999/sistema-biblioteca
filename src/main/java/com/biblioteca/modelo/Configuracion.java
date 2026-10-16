package com.biblioteca.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "configuracion")
public class Configuracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

		private double multaPorDia = 5.0;

	public Configuracion() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getMultaPorDia() {
		return multaPorDia;
	}

	public void setMultaPorDia(double multaPorDia) {
		this.multaPorDia = multaPorDia;
	}
}
