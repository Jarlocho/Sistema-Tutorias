/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.dominio;

import java.sql.SQLException;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import sistematutorias.modelo.dao.ProblematicaDAO;
import sistematutorias.modelo.pojo.Problematica;

public class ProblematicaImpTest {

    @Test
    public void testRegistrarProblematica_Exito() {
        System.out.println("Prueba: registrarProblematica éxito");

        Problematica problematica = new Problematica();

        try (MockedStatic<ProblematicaDAO> daoMock = Mockito.mockStatic(ProblematicaDAO.class)) {
            daoMock.when(() -> ProblematicaDAO.registrarProblematica(problematica))
                   .thenReturn(true);

            HashMap<String, Object> respuesta = ProblematicaImp.registrarProblematica(problematica);

            assertFalse((boolean) respuesta.get("error"));
            assertEquals("Problemática registrada correctamente.", respuesta.get("mensaje"));
        }
    }

    @Test
    public void testRegistrarProblematica_SinExito() {
        System.out.println("Prueba: registrarProblematica sin éxito (DAO devuelve false)");

        Problematica problematica = new Problematica();

        try (MockedStatic<ProblematicaDAO> daoMock = Mockito.mockStatic(ProblematicaDAO.class)) {
            daoMock.when(() -> ProblematicaDAO.registrarProblematica(problematica))
                   .thenReturn(false);

            HashMap<String, Object> respuesta = ProblematicaImp.registrarProblematica(problematica);

            assertTrue((boolean) respuesta.get("error"));
            assertEquals("No se pudo registrar la problemática.", respuesta.get("mensaje"));
        }
    }

    @Test
    public void testRegistrarProblematica_ErrorBD() {
        System.out.println("Prueba: registrarProblematica con SQLException");

        Problematica problematica = new Problematica();

        try (MockedStatic<ProblematicaDAO> daoMock = Mockito.mockStatic(ProblematicaDAO.class)) {
            daoMock.when(() -> ProblematicaDAO.registrarProblematica(problematica))
                   .thenThrow(new SQLException("Fallo en BD"));

            HashMap<String, Object> respuesta = ProblematicaImp.registrarProblematica(problematica);

            assertTrue((boolean) respuesta.get("error"));
            String mensaje = (String) respuesta.get("mensaje");
            assertTrue(mensaje.contains("Error de base de datos:"));
            assertTrue(mensaje.contains("Fallo en BD"));
        }
    }
}
