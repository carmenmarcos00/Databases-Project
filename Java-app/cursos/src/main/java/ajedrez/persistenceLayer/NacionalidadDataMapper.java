package ajedrez.persistenceLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ajedrez.domain.Nacionalidad;


/**
 * Clase para realizar las operaciones de selección y manipulación
 * de datos concernientes a las nacionalidades.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class NacionalidadDataMapper {
	
	/**
	 * Método para obtener una nacionalidad de la base de datos.
	 * @param n
	 * @return
	 */
	public int obtenNacionalidad(String n) {
		int id = -1;
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción selct
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT idNacionalidad FROM nacionalidad WHERE pais = '" + 
					  n + "'" ;
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				id = results.getInt("idNacionalidad"); //
			}
			selectStm.close(); //Cerramos el statement
			
		} catch (SQLException e) {
			System.out.println("Excepción al obtener id nacionalidad");
			e.printStackTrace();
		}
		
		return id;
	}

	public boolean creaNacionalidad(Nacionalidad n) {
        
        String nac = "";
		Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
		try {
			//Construye la instrucción selct
			Statement selectStm = con.createStatement(); //Creamos un nuevo statement
			String insertStmText = "SELECT pais FROM nacionalidad WHERE pais = '" + n.getPais() + "'";
			ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
			if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
				nac = results.getString("pais"); //
			}
			selectStm.close(); //Cerramos el statement
		} catch (SQLException e) {
			System.out.println("Excepción al obtener la nacionalidad");
			e.printStackTrace();
		}
		
		if (nac.equals(n.getPais())) {
			return false;
		}
		
		String insertStmText = "INSERT INTO nacionalidad(pais) VALUES('" + n.getPais() + "')";
		return SqlServerConnectionManager.executeSqlStatement(insertStmText, "Excepción al añadir la nacionalidad " + n.getPais());
	}

}
