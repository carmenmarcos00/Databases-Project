package ajedrez.presentation;

import ajedrez.business.JuezBusiness;
import fundamentos.Lectura;
import fundamentos.Mensaje;

/**
 * Clase que contiene las operaciones a realizar cuando se selecciona una opción
 * en el menú de jueces
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class JuezMenuOperations {
	
	
	private JuezMenu jm; //menu al que referencia
	
	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public JuezMenuOperations(JuezMenu menu) {
		
		this.jm = menu;
	}

	/**
	 * Método para iniciar (insertar) una nueva ronda.
	 */
	public void inicioRonda() {
		
		try {
			
			//Leemos los datos a insertar
			Lectura lec = new Lectura ("Proporciona los datos de la ronda a iniciar:");
			lec.creaEntrada("ID Torneo", "P01");
			lec.esperaYCierra(); //espera que insertemos el valor
			
			boolean success = new JuezBusiness().inicioRonda(lec.leeString("ID Torneo"));
			
			String txt;
			
			if(success) txt = "Éxito al iniciar la ronda.";
			//Si retornó false, guardamos el mensaje de error para mostrarlo
			else txt="Error al iniciar la ronda. Comprueba que los datos son correctos y que se cumplen todas las restricciones";
			
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		}catch(Exception e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al iniciar la ronda.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para finalizar (actualizar) una ronda
	 */
	public void finRonda() {
		
		try {
			
			//Leemos los datos a insertar
			Lectura lec = new Lectura ("Proporciona los datos de la ronda a finalizar:");
			lec.creaEntrada("ID Torneo", "P01");
			lec.creaEntrada("Numero de Ronda", "1");
			lec.esperaYCierra(); //espera que insertemos el valor
			
			boolean success = new JuezBusiness().finRonda(lec.leeString("ID Torneo"), lec.leeInt("Numero de Ronda"));
			
			String txt;
			
			if(success) txt = "Éxito al finalizar la ronda.";
			//Si retornó false, guardamos el mensaje de error para mostrarlo
			else txt="Error al finalizar la ronda. Comprueba que los datos son correctos y que se cumplen todas las restricciones";
			
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		}catch(Exception e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al finalizar la ronda.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para iniciar (actualizar) una partida.
	 */
	public void inicioPartida() {
		
		try {
			//Leemos los datos a insertar
			Lectura lec = new Lectura ("Proporciona los datos de la partida a iniciar:");
			lec.creaEntrada("ID Torneo", "P01");
			lec.creaEntrada("Numero de Ronda", "1");
			lec.creaEntrada("Numero de Mesa", "1");
			lec.creaEntrada("NIF Jugador 1", "11111111V");
			lec.creaEntrada("NIF Jugador 2", "22222222V");
			lec.creaEntrada("NIF Juez", "12121212X");
			lec.esperaYCierra(); //espera que insertemos el valor
			
			boolean success = new JuezBusiness().inicioPartida(lec.leeString("ID Torneo"), lec.leeInt("Numero de Ronda"), lec.leeInt("Numero de Mesa"),
					lec.leeString("NIF Jugador 1"), lec.leeString("NIF Jugador 2"), lec.leeString("NIF Juez"));
			
			String txt;
			
			//Si el método retornó true es porque el torneo se insertó correctamente
			if(success) txt = "Éxito al iniciar la partida";
			//Si retornó false, guardamos el mensaje de error para mostrarlo
			else txt="Error al iniciar la partida. Comprueba que los datos son correctos y que se cumplen todas las restricciones";
			
			//Mostramos por pantalla el mensaje (éxito o error según el caso)
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		}catch(Exception e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al iniciar la partida.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para registrar (actualizar) el resultado de una partida.
	 */
	public void registraResultado() {
		
		try {
			//@idTorneo char(3), @numMesa int, @numRonda int, @resultadoJug1 int, @resultadoJug2 int, @ficheroTxt text, @error
			//Leemos los datos a insertar
			Lectura lec = new Lectura ("Proporciona los datos de la partida a iniciar:");
			lec.creaEntrada("ID Torneo", "P01");
			lec.creaEntrada("Numero de Ronda", "1");
			lec.creaEntrada("Numero de Mesa", "1");
			lec.creaEntrada("Resultado jugador 1 (1 gana o 0 pierde/empata)", "1");
			lec.creaEntrada("Resultado jugador 2 (1 gana o 0 pierde/empata)", "0");
			lec.creaEntrada("Movimientos", "A3,B4,...");
			lec.esperaYCierra(); //espera que insertemos el valor
			
			boolean success = new JuezBusiness().registraResultado(lec.leeString("ID Torneo"),lec.leeInt("Numero de Ronda"),
					lec.leeInt("Numero de Mesa"),lec.leeInt("Resultado jugador 1 (1 gana o 0 pierde/empata)"),lec.leeInt("Resultado jugador 2 (1 gana o 0 pierde/empata)"),
					lec.leeString("Movimientos"));
			
			String txt;
			
			//Si el método retornó true es porque el torneo se insertó correctamente
			if(success) txt = "Éxito al registrar el resultado";
			//Si retornó false, guardamos el mensaje de error para mostrarlo
			else txt="Error al registrar el resultado. Comprueba que los datos son correctos y que se cumplen todas las restrcciones";
			
			//Mostramos por pantalla el mensaje (éxito o error según el caso)
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		}catch(Exception e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al registrar resultado.");
			e.printStackTrace();
		}
	}
}
