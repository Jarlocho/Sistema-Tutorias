/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.dominio;

import java.sql.SQLException;
import sistematutorias.modelo.dao.AutenticacionDAO;
import sistematutorias.modelo.pojo.Tutor;
import utilidad.Sesion;

/**
 *
 * @author HP
 */
public class AutenticacionImp {
   public static boolean iniciarSesionTutor(String numeroPersonal, String password) {
        try {
            Tutor tutor = AutenticacionDAO.verificarSesionTutor(numeroPersonal, password);
            if (tutor != null) {
                Sesion.setTutorSesion(tutor);
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
