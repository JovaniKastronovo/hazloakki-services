package com.hazloakki.negocio.modelo;

public class HorarioNegocioDto {

	private String id_negocio;
	private String nombre;
	private String descripcion;
	private boolean estatus;
	private String horarioInicial;
	private String horarioFinal;

	public String getId_negocio() {
		return id_negocio;
	}

	public void setId_negocio(String id_negocio) {
		this.id_negocio = id_negocio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	public String getHorarioInicial() {
		return horarioInicial;
	}

	public void setHorarioInicial(String horarioInicial) {
		this.horarioInicial = horarioInicial;
	}

	public String getHorarioFinal() {
		return horarioFinal;
	}

	public void setHorarioFinal(String horarioFinal) {
		this.horarioFinal = horarioFinal;
	}

}
