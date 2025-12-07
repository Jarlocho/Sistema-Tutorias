/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import sistematutorias.modelo.ConexionBD;
import sistematutorias.modelo.pojo.Tutoria;

public class TutoriaDAO {

    public static int registrarTutoria(Tutoria tutoria) throws SQLException {
        int resultado = 0;
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "INSERT INTO tutoria (idTutor, idPeriodo, fecha, hora_inicio) VALUES (?, ?, ?, ?)";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, tutoria.getIdTutor());
                sentencia.setInt(2, 1); // Hardcodeo idPeriodo = 1 temporalmente // IMPORTANTE: Aquí deberías usar tutoria.getIdPeriodo(). Hardcodeo 1 por ahora si no tienes gestión de periodos.
                sentencia.setDate(3, Date.valueOf(tutoria.getFecha()));
                sentencia.setTime(4, Time.valueOf(tutoria.getHoraInicio()));
                resultado = sentencia.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // Opcional: imprimir error en consola para debug
                throw e; // Re-lanzamos para que el Imp sepa que hubo error
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return resultado;
    }
// Para verificar si el tutor ya agendó esa fecha específica

    public static boolean comprobarTutoriaRegistrada(int idTutor, java.time.LocalDate fecha) throws SQLException {
        boolean registrada = false;
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null) {
            try {
                String consulta = "SELECT idTutoria FROM tutoria WHERE idTutor = ? AND fecha = ?";
                PreparedStatement sentencia = conexion.prepareStatement(consulta);
                sentencia.setInt(1, idTutor);
                sentencia.setDate(2, Date.valueOf(fecha));
                ResultSet resultado = sentencia.executeQuery();
                registrada = resultado.next();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                ConexionBD.cerrarConexionBD();
            }
        }
        return registrada;
    }
}
