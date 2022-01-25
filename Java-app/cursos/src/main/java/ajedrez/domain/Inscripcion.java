package ajedrez.domain;

import java.sql.Timestamp;

/**
 * 
 * Clase del dominio que representa las inscripciones de los
 * participantes en el torneo de ajedrez
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Inscripcion {

	private int idInscripcion; //el tipo de dato en la bd es entero
	private int idPersona; //el tipo de dato en la bd es entero
	private String idTorneo; //el tipo de dato en la bd es char o varchar
	private Timestamp fechaInscripcion; //el tipo de dato en la bd es Timestamp
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idInscripcion
	 * @param idPersona
	 * @param idTorneo
	 * @param fechaInscripcion
	 */
	public Inscripcion(int idInscripcion, int idPersona, String idTorneo, Timestamp fechaInscripcion) {
		this.idInscripcion = idInscripcion;
		this.idPersona = idPersona;
		this.idTorneo = idTorneo;
		this.fechaInscripcion = fechaInscripcion;
	} 
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param idPersona
	 * @param idTorneo
	 * @param fechaInscripcion
	 */
	public Inscripcion(int idPersona, String idTorneo, Timestamp fechaInscripcion) {
		this.idPersona = idPersona;
		this.idTorneo = idTorneo;
		this.fechaInscripcion = fechaInscripcion;
	}

	/*
	 * Getters y Setters de los atributos de clase
	 */
	public int getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(int idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdTorneo() {
		return idTorneo;
	}

	public void setIdTorneo(String idTorneo) {
		this.idTorneo = idTorneo;
	}

	public Timestamp getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(Timestamp fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver todos los atributos de la clase Inscripcion.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		return "Inscripcion [idInscripcion=" + idInscripcion + ", idPersona=" + idPersona + ", idTorneo=" + idTorneo
				+ ", fechaInscripcion=" + fechaInscripcion + "]";
	} 
}
	
	
	