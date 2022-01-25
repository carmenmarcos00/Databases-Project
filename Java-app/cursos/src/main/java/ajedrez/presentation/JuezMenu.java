package ajedrez.presentation;

import fundamentos.Menu;
/**
 * Clase para generar el menú de gestión de los jueces
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class JuezMenu {

	JuezMenuOperations jmo = new JuezMenuOperations(this);  //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu

	
	/**
	 * Método run al que se llama desde el exterior para lanzar el menú
	 */
	public void run() {
		
		int option;
		
		//Menú para manejar las acciones de los Jueces
		Menu juezMenu = new Menu("Menú de gestión los Jueces");
		juezMenu.insertaOpcion("Iniciar Ronda", 1);
		juezMenu.insertaOpcion("Finalizar Ronda", 2);
		juezMenu.insertaOpcion("Iniciar Partida",3);
		juezMenu.insertaOpcion("Registrar Resultados Partida",4);
		juezMenu.insertaOpcion("Atrás",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en el menú
		do {
			option = juezMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		juezMenu.cierra();
		
	}
	
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			
			jmo.inicioRonda(); //opreación para insertar una nueva ronda
			break;
			
		case 2:
			jmo.finRonda(); //operación para actualizar una ronda
			break;
		
		
		case 3:
			jmo.inicioPartida(); //operación para actualizar la fecha de inicio de una partida
			break;
			
		case 4:
			jmo.registraResultado(); //operación para actualizar el resultado de una partida
			break;
		
		default:
			break;
			
		
		}
	}
}
