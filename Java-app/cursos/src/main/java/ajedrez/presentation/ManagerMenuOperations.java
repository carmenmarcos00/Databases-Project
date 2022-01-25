package ajedrez.presentation;

import java.sql.Date;
import java.util.List;

import ajedrez.business.NacionalidadBusiness;
import ajedrez.business.TorneoBusiness;
import ajedrez.domain.Torneo;
import fundamentos.Lectura;
import fundamentos.Mensaje;

/**
 * Clase que contiene las operaciones a realizar cuando se selecciona una opción
 * en el menú de manager
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class ManagerMenuOperations {
	
	
	private ManagerMenu mm; //menu al que referencia
	
	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public ManagerMenuOperations(ManagerMenu menu) {
		
		this.mm = menu;
	}
	
	/**
	 * Muestra por pantalla los datos de todos los torneos
	 */
	public void getAllTorneos() {
		
		//Creamos una lista de torneo y usamos la capa de negocio para obtenerlos
		List<Torneo> torneos = new TorneoBusiness().getAllTorneos();
		
		//Para cada torneo retornado, guardamos sus datos en un String
		String txt = new String();
		for(Torneo t: torneos) {
			txt=txt+t.toString()+"\n";
			
		}
		
		//Mostramos por pantalla los datos
		Mensaje msg = new Mensaje();
		msg.escribe(txt);
	}
	
	/**
	 * Método para insertar un nuevo torneo
	 */
	public void newTorneo() {
		
		//Leemos los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos del torneo a crear");
		lec.creaEntrada("ID", "P01");
		lec.creaEntrada("Nombre", "PRUEBA");
		lec.creaEntrada("Fecha Limite Inscripcion", "2020-12-12");
		lec.creaEntrada("Fecha Fin de Torneo", "");
		lec.creaEntrada("Edad Minima", "12");
		lec.creaEntrada("Minimo Aforo", "1");
		lec.creaEntrada("Maximo Aforo", "8");
		lec.creaEntrada("Numero de Rondas", "4");
		lec.creaEntrada("Primer Premio", "1000");
		lec.creaEntrada("Segundo Premio", "500");
		lec.creaEntrada("Tercer Premio", "250");
		lec.esperaYCierra(); //espera que insertemos el valor
		try {

			/*
			 * Dado que las fecha limite inscripcion y fin torneo pueden ser nulas, comprobamos que
			 * se haya introducido efectivamente un valor. Si el campo está vacio, interpretamos
			 * que queremos introducir un NULL en la base de datos
			 */
			//Leemos los datos de fechas como String
			String fechaLimiteInscripcionString = lec.leeString("Fecha Limite Inscripcion");
			String fechaFinTorneoString = lec.leeString("Fecha Fin de Torneo");
			
			Date fechaLimiteInscripcion;
			Date fechaFinTorneo;
			
			//Si la fecha limite de inscripcion está vacía, guardamos un null en el campo de tipo Date
			if(fechaLimiteInscripcionString.isEmpty()) fechaLimiteInscripcion = null;
			//Si la fecha limite de inscripcion no está vacía, guardamos la fecha en la variable transformando el String en Date
			else fechaLimiteInscripcion = Date.valueOf(fechaLimiteInscripcionString);
			
			//Lo mismo hacemos con fecha fin de torneo que con fecha de limite inscripcion
			if(fechaFinTorneoString.isEmpty()) fechaFinTorneo = null;
			else fechaFinTorneo = Date.valueOf(fechaFinTorneoString);
			
			//Usamos el método de la capa de negocio para insertar el torneo. Retorna true si se insertó con éxito			
			boolean success = new TorneoBusiness().newTorneo( lec.leeString("ID"), lec.leeString("Nombre"), fechaLimiteInscripcion, fechaFinTorneo,
					lec.leeInt("Edad Minima"),lec.leeInt("Minimo Aforo"),lec.leeInt("Maximo Aforo"),lec.leeInt("Numero de Rondas"));
			
			boolean successPremios = new TorneoBusiness().asignaPremios(lec.leeString("ID"), lec.leeInt("Primer Premio"), lec.leeInt("Segundo Premio"), lec.leeInt("Tercer Premio"));
			
			String txt;
			
			//Si el método retornó true es porque el torneo se insertó correctamente
			if(success && successPremios) txt = "Éxito al insertar el torneo";
			//Si retornó false, guardamos el mensaje de error para mostrarlo
			else txt="Error al insertar el torneo. Comprueba que los datos son correctos y que se cumplen todas las restricciones";
			
			//Mostramos por pantalla el mensaje (éxito o error según el caso)
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
			
		//Controlamos que los tipos de datos se han introducido correctamente.
		//De no ser así, capturamos la excepción y mostramos el error.	
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al insertar el torneo. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
	}

	public void finalizaTorneo() {
		//Leemos los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos del torneo a crear");
		lec.creaEntrada("ID Torneo", "P01");
		lec.esperaYCierra(); //espera que insertemos el valor
		try {
			
			//Usamos el método de la capa de negocio para insertar el torneo. Retorna true si se insertó con éxito			
			String success = new TorneoBusiness().finTorneo(lec.leeString("ID Torneo"));
			
			String txt;
			
			//Si el método retornó true es porque el torneo se finalizar correctamente
			if(!success.isEmpty()) txt = success;
			//Si retornó false, guardamos el mensaje de error para mostrarlo
			else txt="Error al finalizar el torneo. Comprueba que los datos son correctos y que se cumplen todas las restricciones";
			
			//Mostramos por pantalla el mensaje (éxito o error según el caso)
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
			
		//Controlamos que los tipos de datos se han introducido correctamente.
		//De no ser así, capturamos la excepción y mostramos el error.	
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al finalizar el torneo. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método para introducir una nueva nacionalidad
	 */
	public void newNacionalidad() {
		
		// Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos de la nacionalidad a insertar");
		lec.creaEntrada("Nueva Nacionalidad", "INGLATERRA");
		lec.esperaYCierra(); // Espera a que introduzcamos el valor y pulsemos el botón
		
		try {
			
			String nac = lec.leeString("Nueva Nacionalidad");
			if(nac.isEmpty()) nac = null;
		
			// Usamos el método de la capa de negocio para insertar al participante. Retorna true si se insertó con éxito
			boolean success = new NacionalidadBusiness().newNacionalidad(nac);
			
			// String para mostrar
			String txt;
			
			//Si el método de negocio retornó true, el participante se insertó correctamente. Sino, mostramos error.
			if(success) txt = "Éxito al insertar la nacionalidad";
			else txt="Error al inserta la nacionalidad. Comprueba que los datos sean correctos y que se cumplan todas las "
					+ "restricciones de la tabla";
			
			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
			
		// Controlamos que los tipos de datos se han introducido correctamente. Por ejemplo, que el ELO se introdujo como int.
		// De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al insertar la nueva nacionalidad. Comprueba que los datos están en el formato adecuado.");
			e.printStackTrace();
		}	
}
}
