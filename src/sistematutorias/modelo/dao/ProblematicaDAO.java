/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sistematutorias.modelo.ConexionBD;
import sistematutorias.modelo.pojo.Problematica;

public class ProblematicaDAO {
    
    public static boolean registrarProblematica(Problematica problematica) throws SQLException {
        boolean resultado = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO problematica (idTutorado, idTutoria, titulo, descripcion, fecha, estatus) " +
                                  "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, problematica.getIdTutorado());
                sentencia.setInt(2, problematica.getIdTutoria());
                sentencia.setString(3, problematica.getTitulo());
                sentencia.setString(4, problematica.getDescripcion());
                // Convertimos LocalDate a java.sql.Date
                sentencia.setDate(5, Date.valueOf(problematica.getFecha())); 
                sentencia.setString(6, "ABIERTA"); // Estatus inicial por defecto
                
                int filasAfectadas = sentencia.executeUpdate();
                resultado = (filasAfectadas > 0);
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return resultado;
    }
}