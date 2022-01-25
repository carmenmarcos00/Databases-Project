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
public class Partida { // TODO LEER FICHERO

	private int idPartida; //el tipo de dato en la bd es entero
	private int idJugador1; //el tipo de dato en la bd es entero
	private int idJugador2; //el tipo de dato en la bd es entero
	private int numMesa; //el tipo de dato en la bd es Timestamp
	private String movimientos; //el tipo de dato en la bd es .txt
	private int resultadoJug1; //el tipo de dato en la bd es entero
	private int resultadoJug2; //el tipo de dato en la bd es entero
	private int idArbitro; //el tipo de dato en la bd es entero
	private Timestamp fechaFinPartida; //el tipo de dato en la bd es Timestamp
	private Timestamp fechaInicio; //el tipo de dato en la bd es Timestamp
	private int idronda; //el tipo de dato en la bd es entero
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, incluido el id
	 * @param idPartida
	 * @param idJugador1
	 * @param idJugador2
	 * @param numMesa
	 * @param movimientos
	 * @param resultadoJug1
	 * @param resultadoJug2
	 * @param idArbitro
	 * @param fechaFinPartida
	 * @param fechaInicio
	 * @param idronda
	 */
	public Partida(int idPartida, int idJugador1, int idJugador2, int numMesa, String movimientos, int resultadoJug1,
			int resultadoJug2, int idArbitro, Timestamp fechaFinPartida, Timestamp fechaInicio, int idronda) {
		this.idPartida = idPartida;
		this.idJugador1 = idJugador1;
		this.idJugador2 = idJugador2;
		this.numMesa = numMesa;
		this.movimientos = movimientos;
		this.resultadoJug1 = resultadoJug1;
		this.resultadoJug2 = resultadoJug2;
		this.idArbitro = idArbitro;
		this.fechaFinPartida = fechaFinPartida;
		this.fechaInicio = fechaInicio;
		this.idronda = idronda;
	} 
	
	/**
	 * Constructor de la clase. Recibe el valor de todos los atributos, sin el id
	 * @param idJugador1
	 * @param idJugador2
	 * @param numMesa
	 * @param movimientos
	 * @param resultadoJug1
	 * @param resultadoJug2
	 * @param idArbitro
	 * @param fechaFinPartida
	 * @param fechaInicio
	 * @param idronda
	 */
	public Partida(int idJugador1, int idJugador2, int numMesa, String movimientos, int resultadoJug1,
			int resultadoJug2, int idArbitro, Timestamp fechaFinPartida, Timestamp fechaInicio, int idronda) {
		this.idJugador1 = idJugador1;
		this.idJugador2 = idJugador2;
		this.numMesa = numMesa;
		this.movimientos = movimientos;
		this.resultadoJug1 = resultadoJug1;
		this.resultadoJug2 = resultadoJug2;
		this.idArbitro = idArbitro;
		this.fechaFinPartida = fechaFinPartida;
		this.fechaInicio = fechaInicio;
		this.idronda = idronda;
	}

	/*
	 * Getters y Setters de los atributos de clase
	 */
	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public int getIdJugador1() {
		return idJugador1;
	}

	public void setIdJugador1(int idJugador1) {
		this.idJugador1 = idJugador1;
	}

	public int getIdJugador2() {
		return idJugador2;
	}

	public void setIdJugador2(int idJugador2) {
		this.idJugador2 = idJugador2;
	}

	public int getNumMesa() {
		return numMesa;
	}

	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}

	public String getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(String movimientos) {
		this.movimientos = movimientos;
	}

	public int getResultadoJug1() {
		return resultadoJug1;
	}

	public void setResultadoJug1(int resultadoJug1) {
		this.resultadoJug1 = resultadoJug1;
	}

	public int getResultadoJug2() {
		return resultadoJug2;
	}

	public void setResultadoJug2(int resultadoJug2) {
		this.resultadoJug2 = resultadoJug2;
	}

	public int getIdArbitro() {
		return idArbitro;
	}

	public void setIdArbitro(int idArbitro) {
		this.idArbitro = idArbitro;
	}

	public Timestamp getFechaFinPartida() {
		return fechaFinPartida;
	}

	public void setFechaFinPartida(Timestamp fechaFinPartida) {
		this.fechaFinPartida = fechaFinPartida;
	}
	
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getIdronda() {
		return idronda;
	}

	public void setIdronda(int idronda) {
		this.idronda = idronda;
	}

	@Override
	public String toString() {
		return "Partida [idPartida=" + idPartida + ", idJugador1=" + idJugador1 + ", idJugador2=" + idJugador2
				+ ", numMesa=" + numMesa + ", movimientos=" + movimientos + ", resultadoJug1=" + resultadoJug1
				+ ", resultadoJug2=" + resultadoJug2 + ", idArbitro=" + idArbitro + ", fechaFinPartida="
				+ fechaFinPartida + ", fechaInicio=" + fechaInicio + ", idronda=" + idronda + "]";
	}
}
	
	
