package ajedrez.presentation;

import fundamentos.Menu;
/**
 * Clase para generar el menú de gestión de los no inscritos
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class NoInscritoMenu {
	
	NoInscritoMenuOperations nimo = new NoInscritoMenuOperations(this); //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu
	
	/**
	 * Método run al que se llama desde el exterior para lanzar el menú
	 */
	public void run() {
		
		int option;
		//Crenimos un menú e insertamos las opciones
		Menu noInscritoMenu = new Menu("Menú de gestión de los participantes");
		noInscritoMenu.insertaOpcion("Añadir participante a la BD", 1);
		noInscritoMenu.insertaOpcion("Añadir juez a la BD", 2);
		noInscritoMenu.insertaOpcion("Atrás",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en el menú
		do {
			option = noInscritoMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamnimos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		noInscritoMenu.cierra();
		
	}
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			nimo.newParticipante(); //operación para insertar un nuevo participante
			break;
		
		case 2:
			nimo.newJuez(); //operación para insertar un nuevo juez
			break;
		
		default:
			break;
		}
	}
}
