/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistematutorias.modelo.pojo.Tutor;

/**
 *
 * @author HP
 */
public class AutenticacionDAO {

    public static ResultSet autenticarUsuario(String noPersonal, String password, Connection conexionBD) throws SQLException {
        if (conexionBD != null) {
            String consulta = "SELECT idTutor, nombre, apellidoPaterno, apellidoMaterno, " +
                              "numeroDePersonal, correo, password " +   
                              "FROM Tutor " +
                              "WHERE numeroDePersonal = ? AND password = ? " +
                              "LIMIT 1";
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, noPersonal);
            sentencia.setString(2, password);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        }
        throw new SQLException("No hay conexión a la base de datos.");
    }
}
