package ajedrez.domain;

import java.sql.Date;

/**
 * 
 * Clase del dominio que representa a las personas
 * participantes en el torneo de ajedrez
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Persona {

	private int idPersona; //el tipo de dato en la bd es entero
	private String nombre; //el tipo de dato en la bd es char o varchar
	private String apellido1; //el tipo de dato en la bd es char o varchar
	private String apellido2; //el tipo de dato en la bd es char o varchar
	private String NIF; //el tipo de dato en la bd es char o varchar
	private Date fechaNacimiento; //el tipo de dato en la bd es Date
	private int ELO; //el tipo de dato en la bd es entero
	private int idNacionalidad; //el tipo de dato en la bd es entero
	
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idPersona
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param nIF
	 * @param fechaNacimiento
	 * @param eLO
	 * @param idNacionalidad
	 */
	public Persona(int idPersona, String nombre, String apellido1, String apellido2, String NIF, Date fechaNacimiento,
			int ELO, int idNacionalidad) {
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.NIF = NIF;
		this.fechaNacimiento = fechaNacimiento;
		this.ELO = ELO;
		this.idNacionalidad = idNacionalidad;
	}

	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param nIF
	 * @param fechaNacimiento
	 * @param eLO
	 * @param idNacionalidad
	 */
	public Persona(String nombre, String apellido1, String apellido2, String NIF, Date fechaNacimiento,
			int ELO, int idNacionalidad) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.NIF = NIF;
		this.fechaNacimiento = fechaNacimiento;
		this.ELO = ELO;
		this.idNacionalidad = idNacionalidad;
	}

	/*
	 * Getters y Setters de los atributos de clase
	 */
	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nIF) {
		NIF = nIF;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getELO() {
		return ELO;
	}

	public void setELO(int eLO) {
		ELO = eLO;
	}

	public int getIdNacionalidad() {
		return idNacionalidad;
	}

	public void setIdNacionalidad(int idNacionalidad) {
		this.idNacionalidad = idNacionalidad;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver todos los atributos de la clase Persona.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2="
				+ apellido2 + ", NIF=" + NIF + ", fechaNacimiento=" + fechaNacimiento + ", ELO=" + ELO
				+ ", idNacionalidad=" + idNacionalidad + "]";
	}
	
	


	
}