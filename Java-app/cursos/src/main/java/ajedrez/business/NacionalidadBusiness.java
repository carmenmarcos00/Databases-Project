package ajedrez.business;

import ajedrez.domain.Nacionalidad;
import ajedrez.persistenceLayer.NacionalidadDataMapper;

/**
 * Clase con las operaciones de negocio de Nacionalidad.
 * Es con esta clase con la que la interfaz gráfica (capa de presentación)
 * se comunica.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class NacionalidadBusiness {
	
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
	public boolean newNacionalidad(String nombreNacionalidad) {
		
		Nacionalidad n = new Nacionalidad(nombreNacionalidad); //creo el objeto torneo
		return new NacionalidadDataMapper().creaNacionalidad(n);
	}
}
