/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidad;

/**
 *
 * @author HP
 */
import sistematutorias.modelo.pojo.Tutor;

public class Sesion {
    private static Tutor tutorSesion;
    // Aqui podrias agregar: private static Coordinador coordinadorSesion; para el futuro
    private static String rolActual; // "TUTOR", "COORDINADOR", "ADMINISTRADOR"

    public static Tutor getTutorSesion() {
        return tutorSesion;
    }

    public static void setTutorSesion(Tutor tutor) {
        tutorSesion = tutor;
        rolActual = "TUTOR"; // Definimos el rol automáticamente
    }

    public static String getRolActual() {
        return rolActual;
    }
    
    // Método para limpiar al cerrar sesión
    public static void cerrarSesion() {
        tutorSesion = null;
        rolActual = null;
    }
}