package ajedrez.persistenceLayer;


import java.sql.Date;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ajedrez.domain.*;

/**
 * Clase para realizar las operaciones de selección y manipulación
 * de datos concernientes a las personas.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class RondaDataMapper {


	public boolean iniciaRonda(String idTorneo) {
		
		int error = 0; //variable para guardar la salida del procedimiento. Tendrá valor -1 si hay error
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			CallableStatement cstmt = null; //Creamos un nuevo callable statement
			//prepara el String con la llamada al método, usando call
	    	cstmt = con.prepareCall(
	                "{call dbo.iniciaRonda(?, ?, ?)}"); //tantas ? como parámetros de entrada y/o salida tiene el procedimiento
	    	cstmt.setString("idTorneo", idTorneo); //damos valor al parámetro @idTorneo del procedimiento
	    	
	    	Torneo t = obtieneTorneo(idTorneo);
	    	if (t == null) {return false;}
	    	t.setNumRondaActual();
	    	
	    	cstmt.setInt("numRonda", t.getNumRondaActual()); //damos valor al parámetro @numRonda del procedimiento
	    	cstmt.registerOutParameter("error", java.sql.Types.INTEGER); //indicamos que hay una variable de salida llamada error de tipo entero
	        cstmt.execute(); //ejecutamos el procedimiento
	        error = cstmt.getInt("error"); //guardamos el resultado en la variable llamada "error"   
		} catch (SQLException e) {
			System.out.println("Error al iniciar ronda");
			e.printStackTrace();
			return false; //si se produce cualquier tipo de excepción SQL, retornamos directamente false
		}
		
	    //si la variable error tiene valor -1 es que se produjo algún error en el procedimiento y retornamos false
	    if(error==-1) return false;
	    //en caso contrario, retornamos true
	    else return true;

	}
	
	public boolean finalizaRonda(String idTorneo, int numRonda) {
		
		int error = 0; //variable para guardar la salida del procedimiento. Tendrá valor -1 si hay error
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			CallableStatement cstmt = null; //Creamos un nuevo callable statement
			//prepara el String con la llamada al método, usando call
	    	cstmt = con.prepareCall(
	                "{call dbo.finalizaRonda(?, ?, ?)}"); //tantas ? como parámetros de entrada y/o salida tiene el procedimiento
	    	cstmt.setString("idTorneo", idTorneo); //damos valor al parámetro @idTorneo del procedimiento
	    	cstmt.setInt("numRonda", numRonda); //damos valor al parámetro @numRonda del procedimiento
	    	cstmt.registerOutParameter("error", java.sql.Types.INTEGER); //indicamos que hay una variable de salida llamada error de tipo entero
	        cstmt.execute(); //ejecutamos el procedimiento
	        error = cstmt.getInt("error"); //guardamos el resultado en la variable llamada "error"   
		} catch (SQLException e) {
			System.out.println("Error al iniciar ronda");
			e.printStackTrace();
			return false; //si se produce cualquier tipo de excepción SQL, retornamos directamente false
		}
		
	    //si la variable error tiene valor -1 es que se produjo algún error en el procedimiento y retornamos false
	    if(error==-1) return false;
	    //en caso contrario, retornamos true
	    else return true;
	}
	
	private Torneo obtieneTorneo(String idTorneo) {
		Torneo result = null; //Inicializamos la variable a retornar a null
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String selectStmText = "SELECT * FROM torneo t WHERE idTorneo = '" + idTorneo + "'"; //Construimos el SELECT
			ResultSet results = selectStm.executeQuery(selectStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados (no se encuentra al estudiante)
				result = this.processTorneo(results); //Guardamos en result un objeto de Alumno con los resultados del SELECT
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener al torneo con id: '"+result.getIdTorneo()+"'");
			e.printStackTrace();
		}
		return result;
	}
	
private Torneo processTorneo(ResultSet results) {
		
		Torneo result = null;
		try {
			String idTorneo = results.getString("idTorneo");
			String nombre = results.getString("nombre");
			Date fechaLimiteInscripcion = results.getDate("fechaLimiteInscripcion");
			Date fechaFinTorneo = results.getDate("fechaFinTorneo");
			int edadMinima = results.getInt("edadMinima");
			int minimoAforo = results.getInt("minimoAforo");
			int maximoAforo = results.getInt("maximoAforo");
			int numRondas = results.getInt("numRondas");
			//Construimos un nuevo objeto de la Clase Torneo
			result = new Torneo(idTorneo, nombre, fechaLimiteInscripcion, fechaFinTorneo, edadMinima, minimoAforo, maximoAforo, numRondas);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	} 
}
