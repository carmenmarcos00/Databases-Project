package ajedrez.business;

import ajedrez.persistenceLayer.PartidaDataMapper;
import ajedrez.persistenceLayer.RondaDataMapper;

/**
 * Clase con las operaciones de negocio del Juez.
 * Es con esta clase con la que la interfaz gráfica (capa de presentación)
 * se comunica.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class JuezBusiness {
	
	public boolean inicioRonda(String idTorneo) {
		RondaDataMapper pdm = new RondaDataMapper(); // Creamos data mapper
		return pdm.iniciaRonda(idTorneo);
	}
	


	public boolean finRonda(String idTorneo, int numRonda) {
		RondaDataMapper pdm = new RondaDataMapper(); // Creamos data mapper
		return pdm.finalizaRonda(idTorneo, numRonda);
	}


	/**
	 * Inicia la partida (actualiza).
	 * @param idPartida
	 * @return
	 */
	public boolean inicioPartida(String idTorneo, int numRonda, int numMesa, String NIF1, String NIF2, String NIFjuez) {
		PartidaDataMapper pdm = new PartidaDataMapper(); // Creamos data mapper
		return pdm.inicioPartida(idTorneo, numRonda, numMesa, NIF1, NIF2, NIFjuez);
	}



	public boolean registraResultado(String idTorneo, int numRonda, int numMesa, int res1, int res2, String mov) {
		PartidaDataMapper pdm = new PartidaDataMapper(); // Creamos data mapper
		return pdm.registroResultadoPartida(idTorneo, numRonda, numMesa, res1, res2, mov);
	}
	
	
	
}
