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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistematutorias.SistemaTutorias;
import utilidad.Sesion;
import utilidad.Utilidades;

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
        if (btnResponderReporteTutoria != null && btnGenerarReporteGeneral != null && btnRevisarReporteGeneral != null && btnResponderReporteGeneral != null) {
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
        try {
            FXMLLoader cargador = new FXMLLoader(
                    SistemaTutorias.class.getResource("vista/reporte/FXMLGenerarReporteTutoria.fxml")
            );
            Parent vista = cargador.load();
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Generar reporte de tutoría");
            escenario.initOwner(btnGenerarReporte.getScene().getWindow());
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple(
                    "Error",
                    "No se pudo abrir la ventana para generar el reporte de tutoría.\nVerifique la ruta del archivo FXML.",
                    Alert.AlertType.ERROR
            );
        }
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

    @FXML
    private void clicVolverMenuPrincipal(ActionEvent event) {
        try {
            Parent vista = FXMLLoader.load(SistemaTutorias.class.getResource("vista/FXMLMenuPrincipal.fxml"));
            Scene escena = new Scene(vista);
            Stage stPrincipal = (Stage) btnGenerarReporte.getScene().getWindow();
            stPrincipal.setScene(escena);
            stPrincipal.setTitle("Menú Principal");
            stPrincipal.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
