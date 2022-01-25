package ajedrez.persistenceLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ajedrez.domain.Persona;

/**
 * Clase para realizar las operaciones de selección y manipulación
 * de datos concernientes a las personas.
 * 
 * @author Pablo Almohalla Gómez, Susana Rebolledo, Álvaro López García, Carmen Marcos
 * @lastmodified 24/03/2020
 *
 */
public class JuezDataMapper {
	
		
	/**
	 * Método para insertar una persona en la base de datos (en caso de ser juez se realizará una inscripcióna adicional).
	 * @param p
	 * @return
	 */
	public boolean inscribeJuez(Persona p, String idTorneo) {
		
		PersonaDataMapper pdm = new PersonaDataMapper();
		
 
		//Si retorna true, es que la ejecución fue correcta (sin errores). Si retorna false, es que sucedió algún error.
		if ( pdm.registraPersona(p, idTorneo)) {
			
			//Hacer consulta
			int id = -1;
			Connection con = SqlServerConnectionManager.getConnection(); //creamos una nueva conexión con la BD
			try {
				//Construye la instrucción selct
				Statement selectStm = con.createStatement(); //Creamos un nuevo statement
				String insertStmText = "SELECT idPersona FROM persona WHERE NIF = '" +p.getNIF()+"'";
				ResultSet results = selectStm.executeQuery(insertStmText); //Le proporcionamos como parámetro al statement el SELECT y lo ejecutamos
				if(results.next()) { //Con next, ponemos el puntero en el primer y único resultado. Si retorna false es que no hay resultados 
					id = results.getInt("idPersona"); //
				}
				selectStm.close(); //Cerramos el statement
				
			} catch (SQLException e) {
				System.out.println("Excepción al obtener id persona");
				e.printStackTrace();
			}
			
            String insertText = "INSERT INTO juez(idPersona) VALUES ('"+id+"')";
            return SqlServerConnectionManager.executeSqlStatement(insertText, "Excepción al añadir el juez llamada " + p.getNombre() + " " +p.getApellido1() + " con NIF: " + p.getNIF());
        } else {
            return false;
        }

	}
		
	
	

}