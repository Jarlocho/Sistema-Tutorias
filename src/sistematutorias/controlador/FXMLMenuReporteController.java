/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistematutorias.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import utilidad.Sesion;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLMenuReporteController implements Initializable {

    @FXML
    private Button btnGenerarReporte;
    @FXML
    private Button btnEnviarReporte;
    @FXML
    private Button btnResponderReporteTutoria;
    @FXML
    private Button btnGenerarReporteGeneral;
    @FXML
    private Button btnRevisarReporteGeneral;
    @FXML
    private Button btnResponderReporteGeneral;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       configurarVistaPorRol();
    }    

    private void configurarVistaPorRol() {
        String rol = Sesion.getRolActual();
        
        // Ocultar todo
        btnGenerarReporte.setVisible(false);
        btnEnviarReporte.setVisible(false);
        if(btnResponderReporteTutoria != null && btnGenerarReporteGeneral != null && btnRevisarReporteGeneral != null && btnResponderReporteGeneral != null){ 
            btnResponderReporteTutoria.setVisible(false);
            btnGenerarReporteGeneral.setVisible(false);
            btnRevisarReporteGeneral.setVisible(false);
            btnResponderReporteGeneral.setVisible(false);
        }
        
        if ("TUTOR".equals(rol)) {
            btnGenerarReporte.setVisible(true);
            btnEnviarReporte.setVisible(true);
        }
    }
    
    @FXML
    private void clicGenerarReporteTutoria(ActionEvent event) {
    }

    @FXML
    private void clicEnviarReporteTutoria(ActionEvent event) {
    }

    @FXML
    private void clicResponderReporteTutoria(ActionEvent event) {
    }

    @FXML
    private void clicGenerarReporteGeneral(ActionEvent event) {
    }

    @FXML
    private void clicRevisarReporteGeneral(ActionEvent event) {
    }

    @FXML
    private void clicResponderReporteGeneral(ActionEvent event) {
    }
    
}
