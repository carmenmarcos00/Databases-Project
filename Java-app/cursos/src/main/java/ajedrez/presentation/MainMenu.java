package ajedrez.presentation;

import fundamentos.*; //cargamos el paquete fundamentos

/**
 * Clase para la implementación del menú principal
 * de la aplicación.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class MainMenu {
	
	/**
	 * Método run al que se llama desde el exterior para lanzar el menú
	 */
	public void run() {
		
		
		int option;
		
		//Menú para manejar las acciones
		Menu mainMenu = new Menu("Menú principal");
		mainMenu.insertaOpcion("Gestión de No Inscritos", 1); // No inscrito
		mainMenu.insertaOpcion("Gestión de Jueces", 2);		// Jueces
		mainMenu.insertaOpcion("Gestión de Managers", 3);			// Manager
		mainMenu.insertaOpcion("Consultas", 4); // Común a todas las vistas
		mainMenu.insertaOpcion("Salir",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en el menú
		do {
			option = mainMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		mainMenu.cierra();
		
	}
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			new NoInscritoMenu().run(); //lanzamos el menú de no inscritos
			break;
			
		case 2:
			new JuezMenu().run(); //lanzamos el menú de jueces
			break;
		
		case 3:
			new ManagerMenu().run(); //lanzamos el menú de manager
			break;
		case 4:
			new ConsultaMenu().run();
			//mmo.getParticipante();
			break;
			
		default:
			break;
			
		
		}
	}
}
