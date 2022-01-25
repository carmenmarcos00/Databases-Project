package ajedrez.domain;


/**
 * 
 * Clase del dominio que representa a los jueces
 * participantes en el torneo de ajedrez
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Juez {

	private int idPersona; //el tipo de dato en la bd es entero
	private int idJuez;//el tipo de dato en la bd es entero
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idPersona
	 * @param idJuez
	 */
	public Juez(int idPersona, int idJuez) {
		this.idPersona = idPersona;
		this.idJuez = idJuez;
	} 
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param idPersona
	 */
	public Juez(int idPersona) {
		this.idPersona = idPersona;
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

	public int getIdJuez() {
		return idJuez;
	}

	public void setIdJuez(int idJuez) {
		this.idJuez = idJuez;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver todos los atributos de la clase Juez.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		return "Juez [idPersona=" + idPersona + ", idJuez=" + idJuez + "]";
	} 
	
}
	
