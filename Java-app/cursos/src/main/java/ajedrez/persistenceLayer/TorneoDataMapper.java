package ajedrez.persistenceLayer;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ajedrez.domain.*;

/**
 * Clase para realizar las operaciones de selección y manipulación
 * de datos concernientes a las personas.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class TorneoDataMapper {


	public boolean creaTorneo(Torneo t) {
		
		String fechaLimiteInscripcion = null;
        if(t.getFechaLimiteInscripcion()!=null) fechaLimiteInscripcion = "'"+t.getFechaLimiteInscripcion()+"'";
        String fechaFinTorneo = null;
        if(t.getFechaFinTorneo()!=null) fechaFinTorneo = "'"+t.getFechaFinTorneo()+"'";
        
        String idTorneo = "";
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción selct
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT idTorneo FROM torneo WHERE idTorneo = '" + t.getIdTorneo() + "'";
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				idTorneo = results.getString("idTorneo"); //
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener id Torneo");
			e.printStackTrace();
		}
		
		if (idTorneo.equals(t.getIdTorneo())) {
			return false;
		}
		
		String insertStmText = "INSERT INTO torneo(idTorneo, nombre, fechaLimiteInscripcion, fechaFinTorneo, edadMinima, minimoAforo, maximoAforo, numRondas) VALUES"
				+ " ('"+t.getIdTorneo()+"', '"+t.getNombre()+"', "+fechaLimiteInscripcion+", "+fechaFinTorneo+", '"+t.getEdadMinima()+"', '"+t.getMinAforo()+"',"
						+ " '"+t.getMaxAforo()+"', '"+t.getNumRondas()+"')";
		return SqlServerConnectionManager.executeSqlStatement(insertStmText, "Excepción al añadir el torneo " +
         t.getNombre() + "con ID: " + t.getIdTorneo());
	}
	

	/**
	 * Método para retornar (seleccionar) a todos los torneos.
	 * @return
	 */
	public List<Torneo> selectAllTorneos() {
		List<Torneo> torneos = null; //Lista para retornar a los torneos
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT * FROM torneo"; //Seleccionamos a todos los torneos
			ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			torneos = resultSet2torneos(results); //Utilizamos el método auxiliar para guardar a los torneos retornados en una lista
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener el listado de todos los alumnos");
			e.printStackTrace();
		} // try 
		return torneos;
	}
	
	/**
	 * Método auxiliar para extraer de un resultSet todos los torneos.
	 * resultantes de una consulta.
	 * @param results
	 * @return
	 */
	private List<Torneo> resultSet2torneos(ResultSet results) {
		
		List<Torneo> torneos = new LinkedList<Torneo>(); //Lista de torneos a retornar
		
		//Variables del método para almacenar los datos de cada alumno
		String idTorneo;String nombre;Date fechaLimiteInscripcion;
		Date fechaFinTorneo; int edadMinima, minimoAforo, maximoAforo, numRondas;
		
		try {
			//Recorremos las filas retornadas en el resultset
			while (results.next()) { //Cuando retorne false significa que no hay más elementos
				idTorneo = results.getString("idTorneo"); //Sacamos el idtorneo. Como es entero, usarmo el método getInt.
				nombre = results.getString("nombre"); //Sacamos el nombre. Para datos varchar o char usamos el método getString.
				fechaLimiteInscripcion = results.getDate("fechaLimiteInscripcion");
				fechaFinTorneo = results.getDate("fechaFinTorneo");
				edadMinima = results.getInt("edadMinima");
				minimoAforo = results.getInt("minimoAforo");
				maximoAforo = results.getInt("maximoAforo");
				numRondas = results.getInt("numRondas");
				//Construimos un nuevo objeto de la Clase Torneo y lo añadimos a la lista
				torneos.add(new Torneo(idTorneo, nombre, fechaLimiteInscripcion, fechaFinTorneo, edadMinima,
						minimoAforo, maximoAforo, numRondas));
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return torneos;
	}


	public String finalizaTorneo(String idTorneo) {
		int error = 0; //variable para guardar la salida del procedimiento. Tendrá valor -1 si hay error
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			CallableStatement cstmt = null; //Creamos un nuevo callable statement
			//prepara el String con la llamada al método, usando call
	    	cstmt = con.prepareCall(
	                "{call dbo.finalizaTorneo(?, ?)}"); //tantas ? como parámetros de entrada y/o salida tiene el procedimiento
	    	cstmt.setString("idTorneo", idTorneo); //damos valor al parámetro @idTorneo del procedimiento
	    	cstmt.registerOutParameter("error", java.sql.Types.INTEGER); //indicamos que hay una variable de salida llamada error de tipo entero
	        cstmt.execute(); //ejecutamos el procedimiento
	        error = cstmt.getInt("error"); //guardamos el resultado en la variable llamada "error"   
		} catch (SQLException e) {
			System.out.println("Error al finalizar el torneo");
			e.printStackTrace();
			return ""; //si se produce cualquier tipo de excepción SQL, retornamos directamente false
		}
		String stringTexto = "";
	    //si la variable error tiene valor -1 es que se produjo algún error en el procedimiento y retornamos false
	    if(error==-1) return "";
	    //en caso contrario, retornamos true
	    else {
	    	try {
				Statement selectStm = con.createStatement(); //Creamos un nuevo statement
				String selectStmText = "SELECT * FROM premio p WHERE idTorneo='" + idTorneo + "'"; //Construimos el SELECT
				ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
				while(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra a la perosna)
					stringTexto += this.processPremio(results) + "\n";
				}
				selectStm.close(); //Cerramos el statement
			} catch (SQLException e) {
				System.out.println("Excepción al obtener los premios de los ganadores");
				e.printStackTrace();
			}
	    }
	    return stringTexto;
	}
	
	public List<String> rankingTorneo(String idTorneo) {
		List<String> result = new ArrayList<String>();
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT * FROM dbo.ranking_torneo('"+ idTorneo +"')"; //Construimos el SELECT
			ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			while(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra a la perosna)
				result.add(this.processPersonaRankingTorneo(results));
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener el ranking inicial");
			e.printStackTrace();
		}
		return result;
	}
	
	private String processPersonaRankingTorneo(ResultSet results) {
		Persona result = null;
		String str = "";
		try {
			int pos = results.getInt("Pos");
			int idPersona = results.getInt("idPersona"); 
			String nombre = results.getString("nombre"); 
			String apellido1 = results.getString("apellido1");
			String apellido2 = results.getString("apellido2");
			String NIF = results.getString("NIF");
			Date fechaNacimiento = results.getDate("fechaNacimiento"); 
			int ELO = results.getInt("ELO");
			int idNacionalidad = results.getInt("idNacionalidad");
			float punt = results.getFloat("puntuacionTorneo");
			//Construimos un nuevo objeto de la Clase Persona
			result = new Persona(idPersona, nombre, apellido1, apellido2, NIF, fechaNacimiento, ELO, idNacionalidad);
			str = "[ Posicion: " + pos + ", puntuacion: "+ punt + ", " + result.toString() + " ]";
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return str;
	}
	
	public List<String> rankingInicial(String idTorneo) {
		List<String> result = new ArrayList<String>();
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT * FROM dbo.ranking_inicial('"+ idTorneo +"')"; //Construimos el SELECT
			ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			while(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra a la perosna)
				result.add(this.processPersona(results));
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener el ranking inicial");
			e.printStackTrace();
		}
		return result;
	}
	
	private String processPersona(ResultSet results) {
		Persona result = null;
		String str = "";
		try {
			int pos = results.getInt("Pos");
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
			str = "[ Posicion: " + pos + ", " + result.toString() + " ]";
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return str;
	}


	public boolean creaPremios(String idTorneo, int p1, int p2, int p3) {
		String insertStmText = "INSERT INTO premio(idTorneo, cantidad) VALUES('"+ idTorneo +"', '"+ p1 +"')";
		if (SqlServerConnectionManager.executeSqlStatement(insertStmText, "Excepción al añadir el primer premio")) {
			String insertStmText2 = "INSERT INTO premio(idTorneo, cantidad) VALUES('"+ idTorneo +"', '"+ p2 +"')";
			if (SqlServerConnectionManager.executeSqlStatement(insertStmText2, "Excepción al añadir el segundo premio")) {
				String insertStmText3 = "INSERT INTO premio(idTorneo, cantidad) VALUES('"+ idTorneo +"', '"+ p3 +"')";
				if (SqlServerConnectionManager.executeSqlStatement(insertStmText3, "Excepción al añadir el tercer premio")) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String processPremio(ResultSet results) {
		Premio result = null;
		String str = "";
		try {
			int idPremio = results.getInt("idPremio");
			String idTorneo = results.getString("idTorneo"); 
			float cantidad = results.getFloat("cantidad"); 
			int idWinner = results.getInt("idGanador");
			//Construimos un nuevo objeto de la Clase Persona
			result = new Premio(idPremio, idTorneo, cantidad, idWinner);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT NIF FROM persona where idPersona=" + result.getIdGanador(); //Construimos el SELECT
			ResultSet resultado = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			while(resultado.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra a la perosna)
				str += "El jugador con NIF: " + resultado.getString("NIF") + " ha ganado " + result.getCantidad();
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción el NIF del ganador");
			e.printStackTrace();
		}
		
		return str;
	}
}




