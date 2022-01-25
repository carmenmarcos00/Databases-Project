package ajedrez.domain;


/**
 * 
 * Clase del dominio que representa las nacionalidades
 * de los participantes.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Nacionalidad {

	private int idNacionalidad; //el tipo de dato en la bd es entero
	private String pais; //el tipo de dato en la bd es char o varchar

	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idNacionalidad
	 * @param pais
	 * @return
	 */
	public Nacionalidad(int idNacionalidad, String pais) {
		
		this.idNacionalidad = idNacionalidad;
		this.pais = pais;
	}


	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param pais
	 * @return
	 */
	public Nacionalidad(String pais) {
		
		this.pais = pais;
	}


	/*
	 * Getters y Setters de los atributos de clase
	 */
	
	public int getIdNacionalidad() {
		return idNacionalidad;
	}


	public void setIdNacionalidad(int idNacionalidad) {
		this.idNacionalidad = idNacionalidad;
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver el idNacionalidad y el país.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		
		return ("Nacionalidad con id: "+this.idNacionalidad+", país: i" + this.getPais());
	}
}
	

	