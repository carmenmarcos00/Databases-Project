package ajedrez.persistenceLayer;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import ajedrez.domain.*;

/**
 * Clase para realizar las operaciones de selección y manipulación
 * de datos concernientes a las personas.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class PartidaDataMapper {
	
	public boolean registroResultadoPartida(String idTorneo, int numRonda, int numMesa, int res1, int res2, String mov) {
		int error = 0; //variable para guardar la salida del procedimiento. Tendrá valor -1 si hay error
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			CallableStatement cstmt = null; //Creamos un nuevo callable statement
			//prepara el String con la llamada al método, usando call
	    	cstmt = con.prepareCall(
	                "{call dbo.registraResultado(?, ?, ?, ?, ?, ?, ?)}"); //tantas ? como parámetros de entrada y/o salida tiene el procedimiento
	    	cstmt.setString("idTorneo", idTorneo);
	    	cstmt.setInt("numRonda", numRonda);
	    	cstmt.setInt("numMesa", numMesa);
	    	cstmt.setInt("resultadoJug1", res1);
	    	cstmt.setInt("resultadoJug2", res2);
	    	cstmt.setString("movimientos", mov);
	    	cstmt.registerOutParameter("error", java.sql.Types.INTEGER); //indicamos que hay una variable de salida llamada error de tipo entero
	        cstmt.execute(); //ejecutamos el procedimiento
	        error = cstmt.getInt("error"); //guardamos el resultado en la variable llamada "error"   
		} catch (SQLException e) {
			System.out.println("Error al finalizar partida");
			e.printStackTrace();
			return false; //si se produce cualquier tipo de excepción SQL, retornamos directamente false
		}
		
	    //si la variable error tiene valor -1 es que se produjo algún error en el procedimiento y retornamos false
	    if(error==-1) return false;
	    //en caso contrario, retornamos true
	    else return true;
	}
	
	/**
	 * Método para retornar (seleccionar) a una partida, dado su id.
	 * @param idPartida
	 * @return
	 */
	public Partida selectPartidaById(int id) {
		Partida result = null; //Inicializamos la variable a retornar a null
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT * FROM partida WHERE idPartida = " + id + ""; //Construimos el SELECT
			ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra a la perosna)
				result = this.processPartida(results); //Guardamos en result un objeto de partida con los resultados del SELECT
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener a la partida con id '"+id+"'");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Método privado de apoyo. Recibe un ResultSet de una partida
	 * y devuelve un objeto partida con los datos del ResultSet
	 * @param results
	 * @return
	 */
	private Partida processPartida(ResultSet results) {
		
		Partida result = null;
		try {
			int idPartida = results.getInt("idPartida"); 
			int idJugador1 = results.getInt("idJugador1"); 
			int idJugador2 = results.getInt("idJugador2"); 
			int numMesa = results.getInt("numeroMesa"); 
			String movimientos = results.getString("movimientos"); 
			int ResultadoJug1 = results.getInt("ResultadoJug1"); 
			int ResultadoJug2 = results.getInt("ResultadoJug2");
			int idArbitro = results.getInt("idArbitro");
			Timestamp fechaFinPartida = results.getTimestamp("fechaFinPartida"); 
			Timestamp fechaInicio = results.getTimestamp("fechaInicio"); 
			int idRonda = results.getInt("idRonda");
			//Construimos un nuevo objeto de la Clase Partida
			result = new Partida(idPartida, idJugador1, idJugador2, numMesa, movimientos, 
					ResultadoJug1, ResultadoJug2, idArbitro, fechaFinPartida, fechaInicio, idRonda);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	} 
	
	/**
	 * Inicia partida (actualiza fecha).
	 * @param idPartida
	 * @return
	 */
	public boolean inicioPartida(String idTorneo, int numRonda, int numMesa, String NIF1, String NIF2, String NIFjuez) {
		int error = 0; //variable para guardar la salida del procedimiento. Tendrá valor -1 si hay error
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			CallableStatement cstmt = null; //Creamos un nuevo callable statement
			//prepara el String con la llamada al método, usando call
	    	cstmt = con.prepareCall(
	                "{call dbo.iniciaPartida(?, ?, ?, ?, ?, ?, ?)}"); //tantas ? como parámetros de entrada y/o salida tiene el procedimiento
	    	cstmt.setString("idTorneo", idTorneo);
	    	cstmt.setInt("numRonda", numRonda);
	    	cstmt.setInt("numMesa", numMesa);
	    	cstmt.setString("NIFjug1", NIF1);
	    	cstmt.setString("NIFjug2", NIF2);
	    	cstmt.setString("NIFjuez", NIFjuez);
	    	cstmt.registerOutParameter("error", java.sql.Types.INTEGER); //indicamos que hay una variable de salida llamada error de tipo entero
	        cstmt.execute(); //ejecutamos el procedimiento
	        error = cstmt.getInt("error"); //guardamos el resultado en la variable llamada "error"   
		} catch (SQLException e) {
			System.out.println("Error al iniciar partida");
			e.printStackTrace();
			return false; //si se produce cualquier tipo de excepción SQL, retornamos directamente false
		}
		
	    //si la variable error tiene valor -1 es que se produjo algún error en el procedimiento y retornamos false
	    if(error==-1) return false;
	    //en caso contrario, retornamos true
	    else return true;
	
	}

}