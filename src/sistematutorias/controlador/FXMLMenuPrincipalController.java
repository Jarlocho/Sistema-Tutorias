/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistematutorias.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sistematutorias.SistemaTutorias;
import utilidad.Sesion;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Button btnTutoria;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnPersonal;
    @FXML
    private Label lbNombreTutor;
    @FXML
    private Label lbInfoExtra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarPermisos();
    }    

    private void configurarPermisos() {
        String rol = Sesion.getRolActual();
        
        // Primero ocultamos todo por seguridad o lo dejamos visible y desactivamos
        btnTutoria.setVisible(false);
        btnReporte.setVisible(false);
        btnPersonal.setVisible(false);
        
        if ("TUTOR".equals(rol)) {
            // El tutor solo ve Tutoría y Reporte
            btnTutoria.setVisible(true);
            btnReporte.setVisible(true);
        } 
        // else if ("COORDINADOR".equals(rol)) { ... logica futuro }
    }
    
    @FXML
    private void btnClicTutoria(ActionEvent event) {
        irPantalla("/sistematutorias/vista/FXMLMenuTutoria.fxml", "Menú Tutoría");
    }

    @FXML
    private void btnClicReporte(ActionEvent event) {
        irPantalla("/sistematutorias/vista/FXMLMenuReporte.fxml", "Menú Reporte");
    }

    @FXML
    private void btnClicPersonal(ActionEvent event) {
    }
    
    private void irPantalla(String ruta, String titulo) {
        try {
            Stage escenario = (Stage) btnTutoria.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle(titulo);
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnClicSalir(ActionEvent event) {
         try {
            Parent vista = FXMLLoader.load(SistemaTutorias.class.getResource("vista/FXMLInicioSesion.fxml"));
            Scene escena = new Scene(vista);
            Stage stPrincipal = (Stage) btnTutoria.getScene().getWindow();
            stPrincipal.setScene(escena);
            stPrincipal.setTitle("Iniciar sesión");
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
