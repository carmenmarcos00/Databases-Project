package ajedrez.presentation;

import fundamentos.Menu;
/**
 * Clase para generar el menú de gestión de los torneos
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class ManagerMenu {

	ManagerMenuOperations mmo = new ManagerMenuOperations(this);  //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu
	
	/**
	 * Método run al que se llama desde el exterior para lanzar el menú
	 */
	public void run() {
		
		int option;
		
		//Menú para manejar las acciones sobre los torneos
		Menu managerMenu = new Menu("Menú de gestión de los Managers");
		managerMenu.insertaOpcion("Añade nuevo Torneo", 1);
		managerMenu.insertaOpcion("Finaliza Torneo",3);
		managerMenu.insertaOpcion("Muestra Todos los Torneos",2);
		managerMenu.insertaOpcion("Añadir nueva nacionalidad a la BD", 4);
		managerMenu.insertaOpcion("Atrás",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en el menú
		do {
			option = managerMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		managerMenu.cierra();
	}
	
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			
			mmo.newTorneo(); //opreación para insertar un nuevo torneo
			break;
			
		case 2:
			mmo.getAllTorneos(); //operación para mostrar los torneos
			break;
		
		case 3:
			mmo.finalizaTorneo(); //operación para mostrar los torneos
			break;
			
		case 4:
			mmo.newNacionalidad(); //operación para insertar un nuevo juez
			break;
			
		default:
			break;
			
		}
	}
}
