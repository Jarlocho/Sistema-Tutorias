/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistematutorias.modelo.ConexionBD;

public class PeriodoDAO {

    public static int obtenerIdPeriodoActual() throws SQLException {
        int idPeriodo = -1; 
        Connection conexion = ConexionBD.abrirConexionBD();

        if (conexion != null) {
            try {
                String consulta = "SELECT idPeriodo FROM periodo WHERE esActual = 1";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                ResultSet resultado = sentencia.executeQuery();

                if (resultado.next()) {
                    idPeriodo = resultado.getInt("idPeriodo");
                }
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return idPeriodo;
    }
}
