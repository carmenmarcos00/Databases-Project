package ajedrez.domain;

import java.sql.Timestamp;

/**
 * 
 * Clase del dominio que representa las rondas
 * del torneo de ajedrez
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Ronda {
	
	private int idRonda; //el tipo de dato en la bd es entero
	private String idTorneo; //el tipo de dato en la bd es char o varchar
	private int numPartidas; //el tipo de dato en la bd es entero
	private Timestamp fechaInicioRondaPrevista; //el tipo de dato en la bd es Timestamp
	private Timestamp fechaFinRonda; //el tipo de dato en la bd es Timestamp
	private int numRonda; //el tipo de dato en la bd es entero
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idRonda
	 * @param idTorneo
	 * @param numPartidas
	 * @param fechaIniciorondaPrevista
	 * @param fechaFinRonda
	 * @param numRonda
	 * @return
	 */
	public Ronda(int idRonda, String idTorneo, int numPartidas, Timestamp fechaInicioRondaPrevista, 
			Timestamp fechaFinRonda, int numRonda) {
		
		this.idTorneo = idTorneo;
		this.idRonda = idRonda;
		this.fechaInicioRondaPrevista = fechaInicioRondaPrevista;
		this.fechaFinRonda = fechaFinRonda;
		this.numPartidas = numPartidas;
		this.numRonda = numRonda;
	}
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param idTorneo
	 * @param numPartidas
	 * @param fechaIniciorondaPrevista
	 * @param fechaFinRonda
	 * @param numRonda
	 * @return
	 */
	public Ronda(String idTorneo, int numPartidas, Timestamp fechaInicioRondaPrevista, 
			Timestamp fechaFinRonda, int numRonda) {
		
		this.idTorneo = idTorneo;
		this.fechaInicioRondaPrevista = fechaInicioRondaPrevista;
		this.fechaFinRonda = fechaFinRonda;
		this.numPartidas = numPartidas;
		this.numRonda = numRonda;
	}
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idRonda
	 * @param idTorneo
	 * @param numPartidas
	 * @param fechaIniciorondaPrevista
	 * @param fechaFinRonda
	 * @param numRonda
	 * @return
	 */
	public Ronda(int idRonda, String idTorneo, int numPartidas, 
			Timestamp fechaFinRonda, int numRonda) {
		
		this.idTorneo = idTorneo;
		this.idRonda = idRonda;
		this.fechaInicioRondaPrevista = new Timestamp(System.currentTimeMillis());
		this.fechaFinRonda = fechaFinRonda;
		this.numPartidas = numPartidas;
		this.numRonda = numRonda;
	}
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param idTorneo
	 * @param numPartidas
	 * @param fechaIniciorondaPrevista
	 * @param fechaFinRonda
	 * @param numRonda
	 * @return
	 */
	public Ronda(String idTorneo, int numPartidas, 
			Timestamp fechaFinRonda, int numRonda) {
		
		this.idTorneo = idTorneo;
		this.fechaInicioRondaPrevista = new Timestamp(System.currentTimeMillis());
		this.fechaFinRonda = fechaFinRonda;
		this.numPartidas = numPartidas;
		this.numRonda = numRonda;
	}

	/*
	 * Getters y Setters de los atributos de clase
	 */
	public int getIdRonda() {
		return idRonda;
	}

	public void setIdRonda(int idRonda) {
		this.idRonda = idRonda;
	}

	public String getIdTorneo() {
		return idTorneo;
	}

	public void setIdTorneo(String idTorneo) {
		this.idTorneo = idTorneo;
	}

	public int getNumPartidas() {
		return numPartidas;
	}

	public void setNumPartidas(int numPartidas) {
		this.numPartidas = numPartidas;
	}

	public Timestamp getFechaInicioRondaPrevista() {
		return fechaInicioRondaPrevista;
	}

	public void setFechaInicioRondaPrevista(Timestamp fechaInicioRondaPrevista) {
		this.fechaInicioRondaPrevista = fechaInicioRondaPrevista;
	}

	public Timestamp getFechaFinRonda() {
		return fechaFinRonda;
	}

	public void setFechaFinRonda(Timestamp fechaFinRonda) {
		this.fechaFinRonda = fechaFinRonda;
	}

	public int getNumRonda() {
		return numRonda;
	}

	public void setNumRonda(int numRonda) {
		this.numRonda = numRonda;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver todos los atributos de la clase Ronda.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		return "Ronda [idRonda=" + idRonda + ", idTorneo=" + idTorneo + ", numPartidas=" + numPartidas
				+ ", fechaInicioRondaPrevista=" + fechaInicioRondaPrevista + ", fechaFinRonda=" + fechaFinRonda
				+ ", numRonda=" + numRonda + "]";
	}
	
	
	
}