package ajedrez.presentation;

import fundamentos.Menu;
/**
 * Clase para generar el menú de consultas
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 30/04/2020
 *
 */
public class ConsultaMenu {

	ConsultaMenuOperations cmo = new ConsultaMenuOperations(this);  //objeto de la clase que contiene las operaciones para manejar las acciones seleccionadas en el menu

	
	/**
	 * Método run al que se llama desde el exterior para lanzar el menú
	 */
	public void run() {
		
		int option;
		
		//Menú para manejar las acciones de los Jueces
		Menu consultaMenu = new Menu("Menú de Consultas");
		consultaMenu.insertaOpcion("Consulta Datos Jugador", 1);
		consultaMenu.insertaOpcion("Consulta Estadísticas Jugador En Torneo", 2);
		consultaMenu.insertaOpcion("Consulta Ranking Inicial (por ELO)", 3);
		consultaMenu.insertaOpcion("Consulta Ranking Torneo (por punt.)",4);
		consultaMenu.insertaOpcion("Atrás",99);
		
		//Mientras no se seleccione la opción de salir del menú, se continúa en el menú
		do {
			option = consultaMenu.leeOpcion(); //se lee la opción seleccionada del menú
			this.optionAction(option); //llamamos al método para ejecutar la opción seleccionada
		}while(option!=99);
		
		//Al salir del bucle se cierra el menú
		consultaMenu.cierra();
		
	}
	
	
	/**
	 * Método que maneja con un switch la opción seleccionada en el menú
	 * @param option
	 */
	private void optionAction(int option) {
		switch(option) {
		case 1:
			cmo.datosJugador(); 
			break;
			
		case 2:
			cmo.estadisticasEnTorneo();
			break;
			
		case 3:
			cmo.rankingInicial(); 
			break;
			
		case 4:
			cmo.rankingTorneo();
			break;
			
		default:
			break;
			
		
		}
	}
}