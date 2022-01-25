package ajedrez.domain;


/**
 * 
 * Clase del dominio que representa los premios
 * del torneo de ajedrez
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Premio {

	private int idPremio; //el tipo de dato en la bd es entero
	private String idTorneo;//el tipo de dato en la bd es char
	private float cantidad; //el tipo de dato en la bd es smallmoney  // TODO REVISAR
	private int idGanador;//el tipo de dato en la bd es entero
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idPremio
	 * @param idTorneo
	 * @param cantidad
	 * @param idGanador
	 */
	public Premio(int idPremio, String idTorneo, float cantidad, int idGanador) {
		this.idPremio = idPremio;
		this.idTorneo = idTorneo;
		this.cantidad = cantidad;
		this.idGanador = idGanador;
	} 
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param idTorneo
	 * @param cantidad
	 * @param idGanador
	 */
	public Premio(String idTorneo, float cantidad, int idGanador) {
		this.idTorneo = idTorneo;
		this.cantidad = cantidad;
		this.idGanador = idGanador;
	}

	/*
	 * Getters y Setters de los atributos de clase
	 */
	public int getIdPremio() {
		return idPremio;
	}

	public void setIdPremio(int idPremio) {
		this.idPremio = idPremio;
	}

	public String getIdTorneo() {
		return idTorneo;
	}

	public void setIdTorneo(String idTorneo) {
		this.idTorneo = idTorneo;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdGanador() {
		return idGanador;
	}

	public void setIdGanador(int idGanador) {
		this.idGanador = idGanador;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver todos los atributos de la clase Premio.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		return "Premio [idPremio=" + idPremio + ", idTorneo=" + idTorneo + ", cantidad=" + cantidad + ", idGanador="
				+ idGanador + "]";
	} 
	
}


	