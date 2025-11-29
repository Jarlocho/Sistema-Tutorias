/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.dominio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import sistematutorias.modelo.ConexionBD;
import sistematutorias.modelo.dao.AutenticacionDAO;
import sistematutorias.modelo.pojo.Tutor;

/**
 *
 * @author HP
 */
public class AutenticacionImp {
    public static HashMap<String, Object> verificarSesionTutor(String noPersonal, String password) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        try {
            ResultSet resultado = AutenticacionDAO.autenticarUsuario(noPersonal, password, ConexionBD.abrirConexionBD());
            if (resultado.next()) {
                Tutor tutorSesion = new Tutor();
                tutorSesion.setIdTutor(resultado.getInt("idTutor"));
                tutorSesion.setNumeroDePersonal(resultado.getString("numeroDePersonal"));
                tutorSesion.setNombre(resultado.getString("nombre"));
                tutorSesion.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                tutorSesion.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                tutorSesion.setCorreo(resultado.getString("correo"));
                tutorSesion.setPassword(resultado.getString("password"));

                respuesta.put("error", false);
                respuesta.put("mensaje", "Credenciales correctas");
                respuesta.put("tutor", tutorSesion);
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "Las credenciales proporcionadas son incorrectas, por favor verifica la información.");
            }
            ConexionBD.cerrarConexionBD();
        } catch (SQLException ex) {
            respuesta.put("error", true);
            respuesta.put("mensaje", ex.getMessage());
        }
        return respuesta;
    }
}
