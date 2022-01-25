package ajedrez.business;

import java.sql.Date;
import java.util.List;

import ajedrez.domain.Torneo;
import ajedrez.persistenceLayer.TorneoDataMapper;

/**
 * Clase con las operaciones de negocio del Torneo.
 * Es con esta clase con la que la interfaz gráfica (capa de presentación)
 * se comunica.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class TorneoBusiness {
	
	/**
	 * Método que inserta un nuevo torneo en la base de datos 
	 * @param id
	 * @param nombre
	 * @param fechaLimiteInscripcion
	 * @param fechaFinTorneo
	 * @param edadMinima
	 * @param maxAforo
	 * @param minAforo
	 * @param numRondas
	 * @return
	 */
	public boolean newTorneo(String id, String nombre, Date fechaLimiteInscripcion, 
			Date fechaFinTorneo, int edadMinima, int minAforo, int maxAforo, int numRondas) {
		
		Torneo t = new Torneo(id, nombre, fechaLimiteInscripcion,
				fechaFinTorneo, edadMinima, minAforo, maxAforo, numRondas); //creo el objeto torneo
		//Inserto al torneo haciendo uso del DataMapper
		return new TorneoDataMapper().creaTorneo(t);
	}
	
	
	/**
	 * Método para devolver una lista de todos los torneos.
	 * @return
	 */
	public List<Torneo> getAllTorneos() {
		
		//Retorna con el data mapper a todos los torneos
		return (new TorneoDataMapper().selectAllTorneos());
	}


	public List<String> rankingInicial(String idTorneo) {
		return new TorneoDataMapper().rankingInicial(idTorneo);
	}

	public List<String> rankingTorneo(String idTorneo) {
		return new TorneoDataMapper().rankingTorneo(idTorneo);
	}

	public String finTorneo(String idTorneo) {
		return new TorneoDataMapper().finalizaTorneo(idTorneo);
	}


	public boolean asignaPremios(String idTorneo, int p1, int p2, int p3) {
		return new TorneoDataMapper().creaPremios(idTorneo, p1, p2, p3);
	}
}
