/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistematutorias.modelo.ConexionBD;
import sistematutorias.modelo.pojo.AsistenciaRow;
import sistematutorias.modelo.pojo.Tutoria;

public class AsistenciaDAO {

    public static ArrayList<Tutoria> obtenerSesionesPorTutor(int idTutor) throws SQLException {
        ArrayList<Tutoria> sesiones = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT idTutoria, fecha, hora_inicio FROM tutoria WHERE idTutor = ? ORDER BY fecha DESC";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutor);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Tutoria t = new Tutoria();
                    t.setIdTutoria(rs.getInt("idTutoria"));
                    t.setFecha(rs.getDate("fecha").toLocalDate());
                    t.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    sesiones.add(t);
                }
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return sesiones;
    }

    public static ArrayList<AsistenciaRow> obtenerTutoradosPorTutor(int idTutor, int idPeriodo) throws SQLException {
        ArrayList<AsistenciaRow> lista = new ArrayList<>();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT t.idTutorado, t.matricula, " +
                                  "CONCAT(t.nombre, ' ', t.apellidoPaterno, ' ', t.apellidoMaterno) as nombreC, " +
                                  "t.semestre " +
                                  "FROM tutorado t " +
                                  "INNER JOIN asignaciontutor a ON t.idTutorado = a.idTutorado " +
                                  "WHERE a.idTutor = ? AND a.idPeriodo = ?"; 

                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutor);
                ps.setInt(2, idPeriodo);                
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lista.add(new AsistenciaRow(
                        rs.getInt("idTutorado"),
                        rs.getString("matricula"),
                        rs.getString("nombreC"),
                        rs.getInt("semestre"),
                        false 
                    ));
                }
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return lista;
    }

    public static boolean registrarAsistencia(int idTutoria, int idTutorado, boolean asistio) throws SQLException {
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO asistencia (idTutoria, idTutorado, asistio) VALUES (?, ?, ?) " +
                                  "ON DUPLICATE KEY UPDATE asistio = VALUES(asistio)";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idTutoria);
                ps.setInt(2, idTutorado);
                ps.setBoolean(3, asistio);
                return ps.executeUpdate() > 0;
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return false;
    }
}