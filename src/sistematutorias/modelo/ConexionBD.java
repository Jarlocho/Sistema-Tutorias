/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NOMBRE_BD = "tutoria";
    private static final String IP = "localhost";
    private static final String PUERTO = "3310";
    private static Connection CONEXION = null;
    private static final String USUARIO = "app_tutoria";
    private static final String PASSWORD = "Contr4$Tut0r1a";
    private static final String URL_CONEXION
            = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + NOMBRE_BD
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static Connection abrirConexionBD() {
        try {
            Class.forName(DRIVER);
            if (CONEXION == null || CONEXION.isClosed()) {
                CONEXION = DriverManager.getConnection(URL_CONEXION, USUARIO, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return CONEXION;
    }

    public static void cerrarConexionBD() {
        try {
            if (CONEXION != null && !CONEXION.isClosed()) {
                CONEXION.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CONEXION = null; // <- importantísimo
        }
    }
}
