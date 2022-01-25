package ajedrez.presentation;

import java.util.List;

import ajedrez.business.PersonaBusiness;
import ajedrez.business.TorneoBusiness;
import ajedrez.domain.Persona;
import fundamentos.Lectura;
import fundamentos.Mensaje;

/**
 * Clase que contiene las operaciones a realizar cuando se selecciona una opción
 * en el menú estándar
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class ConsultaMenuOperations {
	
	
	private ConsultaMenu cm; //menu al que referencia
	
	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public ConsultaMenuOperations(ConsultaMenu menu) {
		
		this.cm = menu;
	}

	public void datosJugador() {
		//Leemos los datos a insertar
		Lectura lec = new Lectura ("Consulta los datos de un jugador");
		lec.creaEntrada("NIF", "11111111V");
		lec.esperaYCierra(); //espera que insertemos el valor
		
		try {
			//Leemos el DNI introducido
			String DNI = lec.leeString("NIF");
			
			//Usamos el método de la capa de negocio para obtener el participante y lo guardamos en un objeto		
			Persona p = new PersonaBusiness().getParticipante(DNI);
			
			//String para mostrar
			String txt;
			
			//Si la persona retornado tenía valor null es que algo falló en la operación, y mostramos mensaje de error
			if(p == null) txt = "No existe persona con DNI: "+DNI;
			else txt=p.toString(); //si la persona retornado no era null, guardamos en txt su datos
			
			//Escribimos por pantalla los datos de la persona retornada
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		//Controlamos que los tipos de datos se han introducido correctamente..
		//De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al buscar persona. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
		
	}

	public void estadisticasEnTorneo() {
		//Leemos los datos a insertar
		Lectura lec = new Lectura ("Consulta las estadísticas de un jugador en un torneo");
		lec.creaEntrada("NIF", "11111111V");
		lec.creaEntrada("ID Torneo", "P01");
		lec.esperaYCierra(); //espera que insertemos el valor
		
		try {
			//Leemos el DNI introducido
			String DNI = lec.leeString("NIF");
			
			//Usamos el método de la capa de negocio para obtener el participante y lo guardamos en un objeto		
			String p = new PersonaBusiness().getStats(DNI, lec.leeString("ID Torneo"));
			
			//String para mostrar
			String txt;
			
			//Si la persona retornado tenía valor null es que algo falló en la operación, y mostramos mensaje de error
			if(p == null) {txt = "No existe persona con NIF: "+ DNI; return;}
			else txt=p; //si la persona retornado no era null, guardamos en txt su datos
			
			//Escribimos por pantalla los datos de la persona retornada
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		//Controlamos que los tipos de datos se han introducido correctamente..
		//De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al buscar persona. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
		
	}

	public void rankingInicial() {
		//Leemos los datos a insertar
		Lectura lec = new Lectura ("Consulta el ranking inicial de un torneo");
		lec.creaEntrada("ID Torneo", "P01");
		lec.esperaYCierra(); //espera que insertemos el valor
		
		try {
			//Leemos el DNI introducido
			String idTorneo = lec.leeString("ID Torneo");
			
			//Usamos el método de la capa de negocio para obtener el participante y lo guardamos en un objeto		
			List<String> p = new TorneoBusiness().rankingInicial(idTorneo);
			
			//String para mostrar
			String txt = "";
			
			//Si la persona retornado tenía valor null es que algo falló en la operación, y mostramos mensaje de error
			if(p.isEmpty()) txt = "Error en ranking inicial.";
			else {
				for (String per: p) {
					txt += per + "\n";
				}
			}
			
			//Escribimos por pantalla los datos de la persona retornada
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		//Controlamos que los tipos de datos se han introducido correctamente..
		//De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al obtener el ranking inicial. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
	}

	public void rankingTorneo() {
		//Leemos los datos a insertar
		Lectura lec = new Lectura ("Consulta el ranking inicial de un torneo");
		lec.creaEntrada("ID Torneo", "P01");
		lec.esperaYCierra(); //espera que insertemos el valor
		
		try {
			//Leemos el DNI introducido
			String idTorneo = lec.leeString("ID Torneo");
			
			//Usamos el método de la capa de negocio para obtener el participante y lo guardamos en un objeto		
			List<String> p = new TorneoBusiness().rankingTorneo(idTorneo);
			
			//String para mostrar
			String txt = "";
			
			//Si la persona retornado tenía valor null es que algo falló en la operación, y mostramos mensaje de error
			if(p.isEmpty()) txt = "Error en ranking torneo.";
			else {
				for (String per: p) {
					txt += per + "\n";
				}
			}
			
			//Escribimos por pantalla los datos de la persona retornada
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
		
		//Controlamos que los tipos de datos se han introducido correctamente..
		//De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al obtener el ranking del torneo. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
	}
}
