package ajedrez.persistenceLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import ajedrez.domain.Persona;

/**
 * Clase para realizar las operaciones de selección y manipulación
 * de datos concernientes a las personas.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class PersonaDataMapper {
	
	/**
	 * Método para insertar una persona en la base de datos (en caso de ser juez se realizará una inscripcióna adicional).
	 * @param p
	 * @return
	 */
	public boolean registraPersona(Persona p, String idTorneo) {

		String apellido2Str = null;
        if(p.getApellido2()!=null) apellido2Str = "'"+p.getApellido2()+"'";
        
        String nif = "";
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción select
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT NIF FROM persona WHERE NIF = '" + p.getNIF() + "'";
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				nif = results.getString("NIF"); //
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener el NIF de la persona.");
			e.printStackTrace();
		}
		
		if (nif.equals(p.getNIF())) { //Ya existe esta persona
			return inscribeEnTorneo(p, idTorneo);
		}
		
		//Construye la instrucción insert
		String insertStmText = "INSERT INTO persona(nombre, apellido1, apellido2, NIF, ELO, fechaNacimiento, idNacionalidad) VALUES ("+ 
				 "'"+p.getNombre() + "', '"+p.getApellido1()+"', "+apellido2Str+", '"+p.getNIF()+"', '"+p.getELO()+"', '"+p.getFechaNacimiento()+"','"+p.getIdNacionalidad()+"')";
		//Usa el método estático "executeSqlStatement" para ejecutar la instrucción
		//Si retorna true, es que la ejecución fue correcta (sin errores). Si retorna false, es que sucedió algún error.
		if (SqlServerConnectionManager.executeSqlStatement(insertStmText, "Excepción al añadir la persona llamada " + p.getNombre() + " " +p.getApellido1() + " con NIF: " + p.getNIF())) {
			return inscribeEnTorneo(p, idTorneo);
		}
		return false;
	}
	
	private boolean inscribeEnTorneo(Persona p, String idTorneo) {
		int idPersona = obtenIdPersona(p.getNIF());
		
		String idTor = "";
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción select
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT idTorneo FROM torneo WHERE idTorneo = '" + idTorneo + "'";
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				idTor = results.getString("idTorneo"); //
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener el id del torneo.");
			e.printStackTrace();
		}
		
		if (!idTor.equals(idTorneo)) {return false;}
		
		String insertStmText = "INSERT INTO inscripcion(idPersona, idTorneo) VALUES ('" + idPersona + "','" + idTorneo + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertStmText, "Excepción al inscribir en el torneo " + idTorneo + " al jugador con NIF: " + p.getNIF());
	}

	/**
	 * Método para retornar (seleccionar) a una persona, dado su DNI.
	 * @param DNI
	 * @return
	 */
	public Persona selectPersona(String DNI) {
		Persona result = null; //Inicializamos la variable a retornar a null
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT * FROM persona WHERE NIF = '" + DNI + "'"; //Construimos el SELECT
			ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra a la perosna)
				result = this.processPersona(results); //Guardamos en result un objeto de persona con los resultados del SELECT
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener a la persona con NIF '"+DNI+"'");
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * Método privado de apoyo. Recibe un ResultSet de una persona
	 * y devuelve un objeto persona con los datos del ResultSet
	 * @param results
	 * @return
	 */
	private Persona processPersona(ResultSet results) {
		
		Persona result = null;
		try {
			int idPersona = results.getInt("idPersona"); 
			String nombre = results.getString("nombre"); 
			String apellido1 = results.getString("apellido1");
			String apellido2 = results.getString("apellido2");
			String NIF = results.getString("NIF");
			Date fechaNacimiento = results.getDate("fechaNacimiento"); 
			int ELO = results.getInt("ELO");
			int idNacionalidad = results.getInt("idNacionalidad");
			//Construimos un nuevo objeto de la Clase Persona
			result = new Persona(idPersona, nombre, apellido1, apellido2, NIF, fechaNacimiento, ELO, idNacionalidad);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	} 
	
	
	// TODO: USELESS
	/**
	 * Método para obtener id persona de la base de datos.
	 * @param n
	 * @return
	 */
	public int obtenIdPersona(String n) {
		int id = -1;
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción selct
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT idPersona FROM persona WHERE NIF = '" + 
					  n + "'" ;
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				id = results.getInt("idPersona"); //
			}
			selectStm.close(); //Cerramos el statement
			
		} catch (SQLException e) {
			System.out.println("Excepción al obtener id persona");
			e.printStackTrace();
		}
		
		return id;
	}

	public String obtieneStatsJugador(String NIF, String idTorneo) {
		String str = "";
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción selct
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT * FROM dbo.estadisticas_jugador_en_torneo('"+ NIF +"', '" + idTorneo + "')" ;
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				str = this.processPersonaToString(results);
			}
			selectStm.close(); //Cerramos el statement
			
		} catch (SQLException e) {
			System.out.println("Excepción al obtener id persona");
			e.printStackTrace();
		}
		
		return str;
	}
	
private String processPersonaToString(ResultSet results) {
		
		String str ="";
		Persona result = null;
		try {
			int idPersona = results.getInt("idPersona"); 
			String nombre = results.getString("nombre"); 
			String apellido1 = results.getString("apellido1");
			String apellido2 = results.getString("apellido2");
			String NIF = results.getString("NIF");
			Date fechaNacimiento = results.getDate("fechaNacimiento"); 
			int ELO = results.getInt("ELO");
			int idNacionalidad = results.getInt("idNacionalidad");
			//Construimos un nuevo objeto de la Clase Persona
			result = new Persona(idPersona, nombre, apellido1, apellido2, NIF, fechaNacimiento, ELO, idNacionalidad);
			str += "Pos: " + results.getInt("Pos") + ", puntuacion: " + results.getInt("puntuacionEnTorneo") + ", "; 
			str = str + result.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return str;
	} 
}
