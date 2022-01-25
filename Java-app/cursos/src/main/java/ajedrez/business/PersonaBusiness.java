package ajedrez.business;

import java.sql.Date;

import ajedrez.domain.Persona;
import ajedrez.persistenceLayer.JuezDataMapper;
import ajedrez.persistenceLayer.NacionalidadDataMapper;
import ajedrez.persistenceLayer.PersonaDataMapper;

/**
 * Clase con las operaciones de negocio del Persona.
 * Es con esta clase con la que la interfaz gráfica (capa de presentación)
 * se comunica.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 23/03/2020
 *
 */
public class PersonaBusiness {
	
	/**
	 * 
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param NIF
	 * @param ELO
	 * @param fechaNacimiento
	 * @param nacionalidad
	 * @param idTorneo
	 * @return
	 */
	public boolean newParticipante(String nombre, String apellido1, 
			String apellido2, String NIF, int ELO, Date fechaNacimiento, String nacionalidad, String idTorneo) {
		int idNacionalidad = new NacionalidadDataMapper().obtenNacionalidad(nacionalidad);
		
		Persona a = new Persona(nombre, apellido1, apellido2, NIF,
				fechaNacimiento, ELO, idNacionalidad); //creo el objeto persona
		//Inserto a la persona haciendo uso del DataMapper
		return new PersonaDataMapper().registraPersona(a, idTorneo);
	}
	
	/**
	 * Inscribe un juez
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param NIF
	 * @param ELO
	 * @param fechaNacimiento
	 * @param nacionalidad
	 * @param idTorneo
	 * @return
	 */
	public boolean newJuez(String nombre, String apellido1, 
			String apellido2, String NIF, int ELO, Date fechaNacimiento, String nacionalidad, String idTorneo) {
		int idNacionalidad = new NacionalidadDataMapper().obtenNacionalidad(nacionalidad);
		
		Persona a = new Persona(nombre, apellido1, apellido2, NIF,
				fechaNacimiento, ELO, idNacionalidad); //creo el objeto persona
		
		return new JuezDataMapper().inscribeJuez(a, idTorneo);
	}
	
	
	
	/**
	 * Método para devolver a una perosna, dado su DNI
	 * @param DNI
	 * @return
	 */
	public Persona getParticipante(String DNI) {
		
		//Retorna con el data mapper a la persona con el valor de DNI dado
		return (new PersonaDataMapper().selectPersona(DNI));
	}
	
	public String getStats(String NIF, String idTorneo) {
		return (new PersonaDataMapper().obtieneStatsJugador(NIF, idTorneo));
	}
	
	
	// TODO ??? con imports
	/**
	 * Método para devolver una lista de todos los alumnos
	 * @return
	 *
	public List<Persona> getAllPersonas() {
		
		//Retorna con el data mapper a todos los alumnos
		return (new PersonaDataMapper().selectAllPersonas());
	}
	
	
	/**
	 * Método que retorna una lista de los cursos de un alumno
	 * @param idalumno
	 * @return
	 *
	public List<PersonaCurso> getCursosOfPersonaById(int idalumno) {
		
		//Retorna con el data mapper
		return (new PersonaCursoDataMapper().selectPersonaWithCursos(idalumno));
	}
	
	
	/**
	 * Método para matricular a un alumno en un curso
	 * @param idalumno
	 * @return
	 *
	public boolean insertPersonaInCurso(int idalumno, int idcurso) {
		//Usa el data mapper para matricular a un alumno en un curso
		return new PersonaCursoDataMapper().insertaPersonaCurso(idalumno, idcurso);
	}
	
	*/
	
}
