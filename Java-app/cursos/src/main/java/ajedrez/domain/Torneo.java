package ajedrez.domain;

import java.sql.Date;

/**
 * 
 * Clase del dominio que representa a los torneos
 * de ajedrez
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class Torneo {
	
	private static final int NUM_RONDAS_DEFECTO = 8;
	// Se lleva la cuenta de la ronda en la que se encuentra el torneo
	private static int numRondaActual = 0;

	private String idTorneo; //el tipo de dato en la bd es char o varchar
	private String nombre; //el tipo de dato en la bd es char o varchar
	private Date fechaLimiteInscripcion; //el tipo de dato en la bd es Timestamp
	private Date fechaFinTorneo; //el tipo de dato en la bd es Timestamp
	private int edadMinima; //el tipo de dato en la bd es entero
	private int minAforo; //el tipo de dato en la bd es entero
	private int maxAforo; //el tipo de dato en la bd es entero
	private int numRondas; //el tipo de dato en la bd es entero
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idTorneo
	 * @param nombre
	 * @param fechaLimiteInscripcion
	 * @param fechaFinTorneo
	 * @param edadMinima
	 * @param maxAforo
	 * @param minAforo
	 * @param numRondas
	 * @return
	 */
	public Torneo(String idTorneo, String nombre, Date fechaLimiteInscripcion, 
			Date fechaFinTorneo, int edadMinima, int minAforo, int maxAforo, int numRondas) {
		
		this.idTorneo = idTorneo;
		this.nombre = nombre;
		this.fechaLimiteInscripcion = fechaLimiteInscripcion;
		this.fechaFinTorneo = fechaFinTorneo;
		this.edadMinima = edadMinima;
		this.minAforo = minAforo;
		this.maxAforo = maxAforo;
		this.numRondas = numRondas;
	}
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id y con default de numRondas
	 * @param idTorneo
	 * @param nombre
	 * @param fechaLimiteInscripcion
	 * @param fechaFinTorneo
	 * @param edadMinima
	 * @param maxAforo
	 * @param minAforo
	 * @return
	 */
	public Torneo(String idTorneo, String nombre, Date fechaLimiteInscripcion, 
			Date fechaFinTorneo, int edadMinima, int minAforo, int maxAforo) {
		
		this.idTorneo = idTorneo;
		this.nombre = nombre;
		this.fechaLimiteInscripcion = fechaLimiteInscripcion;
		this.fechaFinTorneo = fechaFinTorneo;
		this.edadMinima = edadMinima;
		this.minAforo = minAforo;
		this.maxAforo = maxAforo;
		this.numRondas = NUM_RONDAS_DEFECTO;
	}

	/*
	 * Getters y Setters de los atributos de clase
	 */
	public String getIdTorneo() {
		return idTorneo;
	}

	public void setIdTorneo(String idTorneo) {
		this.idTorneo = idTorneo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaLimiteInscripcion() {
		return fechaLimiteInscripcion;
	}

	public void setFechaLimiteInscripcion(Date fechaLimiteInscripcion) {
		this.fechaLimiteInscripcion = fechaLimiteInscripcion;
	}

	public Date getFechaFinTorneo() {
		return fechaFinTorneo;
	}

	public void setFechaFinTorneo(Date fechaFinTorneo) {
		this.fechaFinTorneo = fechaFinTorneo;
	}

	public int getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(int edadMinima) {
		this.edadMinima = edadMinima;
	}

	public int getMinAforo() {
		return minAforo;
	}

	public void setMinAforo(int minAforo) {
		this.minAforo = minAforo;
	}

	public int getMaxAforo() {
		return maxAforo;
	}

	public void setMaxAforo(int maxAforo) {
		this.maxAforo = maxAforo;
	}

	public int getNumRondas() {
		return numRondas;
	}

	public void setNumRondas(int numRondas) {
		this.numRondas = numRondas;
	}
	
	public int getNumRondaActual() {
		return numRondaActual;
	}

	public boolean setNumRondaActual() {
		if (numRondaActual < numRondas) {
			numRondaActual++;
			return true;
		}
		return false;
	}

	/**
	 * Sobre escribe el método toString() para
	 * devolver el idTorneo, el nombre, el aforo mínimo y máximo,
	 * la fecha límite de inscripción y fin de torneo, la edad mínima
	 * y el número de rondas.
	 * Podría modificarse para mostrar más o menos información.
	 * 
	 */
	@Override
	public String toString() {
		return ("Torneo con id: "+this.idTorneo+", nombre: "+this.nombre+"."
				+ " Fecha límite de inscripción: "+this.getFechaLimiteInscripcion()+
				". Fecha fin de torneo: " + this.getFechaFinTorneo() +
				". Edad mínima: "+this.getEdadMinima()+
				". Aforo mínimo: "+this.getMinAforo() + " y máximo: "+this.getMaxAforo()+
				". Número Rondas: "+ this.getNumRondas());
	}
	
}