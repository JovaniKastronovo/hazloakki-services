package com.hazloakki.cuenta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hazloakki.cuenta.modelo.CuentaDto;

/**
 * @author Jovani Arzate 2018-07-01 HazloAkki para Empresas v.1
 *
 */
@Entity
@Table(name = "cuenta")
public class CuentaEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(updatable = false)
	private String id;
	private String nombre;
	private String apellidos;
	private String password;
	private String email;
	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	@Column(name = "fecha_nacimiento")
	private String fechaNacimiento;
	private String sexo;
	@Column(name = "tipo_usuario")
	private String tipoUsuario;
	@Column(name = "terminos_condiciones")
	private String terminosCondiciones;
	private String fecha;
	private boolean estatus;
	private String telefono;

	public static CuentaEntity from(CuentaDto cuentaDto) {

		CuentaEntity cuentaEntity = new CuentaEntity();
		cuentaEntity.setId(cuentaDto.getIdCuenta());
		cuentaEntity.setNombre(cuentaDto.getNombre());
		cuentaEntity.setApellidos(cuentaDto.getApellidos());
		cuentaEntity.setEmail(cuentaDto.getEmail());
		cuentaEntity.setPassword(cuentaDto.getPassword());
		cuentaEntity.setTelefono(cuentaDto.getTelefono());
		cuentaEntity.setEstatus(cuentaDto.isEstatus());
		cuentaEntity.setFecha(cuentaDto.getFecha());
		cuentaEntity.setFechaNacimiento(cuentaDto.getFechaNacimiento());
		cuentaEntity.setSexo(cuentaDto.getSexo());
		cuentaEntity.setTerminosCondiciones(cuentaDto.getTerminosCondiciones());
		cuentaEntity.setTipoUsuario(cuentaDto.getTipoUsuario());
		cuentaEntity.setNombreUsuario(cuentaDto.getNombreUsuario());

		return cuentaEntity;
	}

	public CuentaDto to() {

		CuentaDto cuentaDto = new CuentaDto();
		cuentaDto.setIdCuenta(getId());
		cuentaDto.setNombre(getNombre());
		cuentaDto.setApellidos(getApellidos());
		cuentaDto.setEmail(getEmail());
		cuentaDto.setPassword(getPassword());
		cuentaDto.setTelefono(getTelefono());
		cuentaDto.setEstatus(isEstatus());
		cuentaDto.setFecha(getFecha());
		cuentaDto.setFechaNacimiento(getFechaNacimiento());
		cuentaDto.setSexo(getSexo());
		cuentaDto.setTerminosCondiciones(getTerminosCondiciones());
		cuentaDto.setTipoUsuario(getTipoUsuario());
		cuentaDto.setNombreUsuario(getNombreUsuario());

		return cuentaDto;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean isEstatus() {
		return estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getTerminosCondiciones() {
		return terminosCondiciones;
	}

	public void setTerminosCondiciones(String terminosCondiciones) {
		this.terminosCondiciones = terminosCondiciones;
	}

}
