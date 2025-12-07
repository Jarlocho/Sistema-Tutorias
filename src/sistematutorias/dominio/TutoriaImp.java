/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.dominio;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import sistematutorias.modelo.ConexionBD;
import sistematutorias.modelo.dao.TutoriaDAO;
import sistematutorias.modelo.pojo.FechaTutoria;
import sistematutorias.modelo.pojo.Tutoria;

public class TutoriaImp {

    // (El método obtenerFechasPeriodoActual queda igual porque es simulado)
    public static HashMap<String, Object> obtenerFechasPeriodoActual() {
        HashMap<String, Object> respuesta = new HashMap<>();
        ArrayList<FechaTutoria> fechas = new ArrayList<>();

        fechas.add(new FechaTutoria(1, 1, 1, LocalDate.now().plusDays(5)));
        fechas.add(new FechaTutoria(2, 1, 2, LocalDate.now().plusDays(20)));
        fechas.add(new FechaTutoria(3, 1, 3, LocalDate.now().plusDays(45)));

        if (!fechas.isEmpty()) {
            respuesta.put("error", false);
            respuesta.put("fechas", fechas);
        } else {
            respuesta.put("error", true);
            respuesta.put("mensaje", "No hay fechas de tutoría definidas por el coordinador.");
        }
        return respuesta;
    }

    public static HashMap<String, Object> registrarHorarioTutoria(Tutoria tutoria) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("error", true);
        
        try {
            // 1. Validar si ya registró esta fecha (Llamada limpia al DAO)
            if (TutoriaDAO.comprobarTutoriaRegistrada(tutoria.getIdTutor(), tutoria.getFecha())) {
                respuesta.put("mensaje", "Ya has registrado un horario para esta fecha de tutoría.");
                return respuesta;
            }

            // 2. Insertar (Llamada limpia al DAO)
            int filas = TutoriaDAO.registrarTutoria(tutoria);
            if (filas > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "Horario registrado correctamente.");
            } else {
                respuesta.put("mensaje", "No se pudo registrar el horario.");
            }
        } catch (SQLException ex) {
            respuesta.put("mensaje", "Error de base de datos: " + ex.getMessage());
        }
        
        return respuesta;
    }
}