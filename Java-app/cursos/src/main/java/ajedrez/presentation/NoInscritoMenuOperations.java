package ajedrez.presentation;

import java.sql.Date;

import ajedrez.business.NacionalidadBusiness;
import ajedrez.business.PersonaBusiness;
import fundamentos.Lectura;
import fundamentos.Mensaje;

/**
 * Clase que contiene las operaciones a realizar cuando se selecciona una opción
 * en el menú de los no inscritos
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class NoInscritoMenuOperations {
	
	private NoInscritoMenu nim; //menu al que referencia
	
	/**
	 * Constructor. Se le pasa el menú.
	 * @param menu
	 */
	public NoInscritoMenuOperations (NoInscritoMenu menu) {	
		this.nim = menu;
	}
	
	/**
	 * Método para introducir un nuevo participante
	 */
	public void newParticipante() {
		
		// Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos del participante a insertar");
		lec.creaEntrada("ID Torneo", "P01");
		lec.creaEntrada("Nombre", "Raul");
		lec.creaEntrada("Apellido 1", "Gonzalez");
		lec.creaEntrada("Apellido 2", "");
		lec.creaEntrada("NIF", "11111111V");
		lec.creaEntrada("ELO", "1000");
		lec.creaEntrada("Fecha de nacimiento", "1990-12-01");
		lec.creaEntrada("Nacionalidad", "ESPAÑA");
		lec.esperaYCierra(); // Espera a que introduzcamos el valor y pulsemos el botón
		
		try {
			
			/*
			 * Dado que la fecha de nacimiento puede ser nula, comprobamos que
			 * se haya introducido efectivamente un valor. Si el campo está vacio, interpretamos
			 * que queremos introducir un NULL en la base de datos
			 */
			// Leemos los datos de fechas como String
			String fechaNacimientoString = lec.leeString("Fecha de nacimiento");
			Date fechaNacimiento;
			
			// Si la fecha de nacimiento está vacía, guardamos un null en el campo de tipo Date
			if(fechaNacimientoString.isEmpty()) fechaNacimiento = null;
			//Si la fecha de nacimiento no está vacía, guardamos la fecha en la variable transformando el String en Date
			else fechaNacimiento = Date.valueOf(fechaNacimientoString);
		
			String ap2 = lec.leeString("Apellido 2");
			
			// Si el apellido 2 está vacío
			if(ap2.isEmpty()) ap2 = null;
			
			// Usamos el método de la capa de negocio para insertar al participante. Retorna true si se insertó con éxito
			boolean success = new PersonaBusiness().newParticipante(lec.leeString("Nombre"),lec.leeString("Apellido 1"),
					ap2,lec.leeString("NIF"),lec.leeInt("ELO"),fechaNacimiento,lec.leeString("Nacionalidad"), lec.leeString("ID Torneo"));
			
			// String para mostrar
			String txt;
			
			//Si el método de negocio retornó true, el participante se insertó correctamente. Sino, mostramos error.
			if(success) txt = "Éxito al insertar al participante";
			else txt="Error al insertar al participante. Comprueba que los datos sean correctos y que se cumplan todas las "
					+ "restricciones de la tabla. (Puede que se haya registrado pero no inscrito en el torneo, puesto que este no existe.)";
			
			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
			
		// Controlamos que los tipos de datos se han introducido correctamente. Por ejemplo, que el ELO se introdujo como int.
		// De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al insertar al participante. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}	
	}
	
	/**
	 * Método para introducir un nuevo juez
	 */
	public void newJuez() {
		
		// Creamos una ventana de lectura para leer los datos a insertar
		Lectura lec = new Lectura ("Proporciona los datos del juez a insertar");
		lec.creaEntrada("ID Torneo", "P01");
		lec.creaEntrada("Nombre", "Javier");
		lec.creaEntrada("Apellido 1", "Delgado");
		lec.creaEntrada("Apellido 2", "");
		lec.creaEntrada("NIF", "12121212X");
		lec.creaEntrada("ELO", "0");
		lec.creaEntrada("Fecha de nacimiento", "1990-12-01");
		lec.creaEntrada("Nacionalidad", "FRANCIA");
		lec.esperaYCierra(); // Espera a que introduzcamos el valor y pulsemos el botón
		
		try {
			
			/*
			 * Dado que la fecha de nacimiento puede ser nula, comprobamos que
			 * se haya introducido efectivamente un valor. Si el campo está vacio, interpretamos
			 * que queremos introducir un NULL en la base de datos
			 */
			// Leemos los datos de fechas como String
			String fechaNacimientoString = lec.leeString("Fecha de nacimiento");
			Date fechaNacimiento;
			
			// Si la fecha de nacimiento está vacía, guardamos un null en el campo de tipo Date
			if(fechaNacimientoString.isEmpty()) fechaNacimiento = null;
			//Si la fecha de nacimiento no está vacía, guardamos la fecha en la variable transformando el String en Date
			else fechaNacimiento = Date.valueOf(fechaNacimientoString);
			
			String ap2 = lec.leeString("Apellido 2");
			if(ap2.isEmpty()) ap2 = null;
		
			// Usamos el método de la capa de negocio para insertar al participante. Retorna true si se insertó con éxito
			boolean success = new PersonaBusiness().newJuez(lec.leeString("Nombre"),lec.leeString("Apellido 1"),
					ap2,lec.leeString("NIF"),lec.leeInt("ELO"),fechaNacimiento,lec.leeString("Nacionalidad"), lec.leeString("ID Torneo"));
			
			// String para mostrar
			String txt;
			
			//Si el método de negocio retornó true, el participante se insertó correctamente. Sino, mostramos error.
			if(success) txt = "Éxito al insertar al juez";
			else txt="Error al inserta al juez. Comprueba que los datos sean correctos y que se cumplan todas las "
					+ "restricciones de la tabla";
			
			//Escribimos el mensaje por pantalla
			Mensaje msg = new Mensaje();
			msg.escribe(txt);
			
		// Controlamos que los tipos de datos se han introducido correctamente. Por ejemplo, que el ELO se introdujo como int.
		// De no ser así, capturamos la excepción y mostramos el error.
		}catch(IllegalArgumentException e) {
			Mensaje msg = new Mensaje();
			msg.escribe("Error al insertar al juez. Comprueba que los datos están en el formato adecuado");
			e.printStackTrace();
		}
	}
}
