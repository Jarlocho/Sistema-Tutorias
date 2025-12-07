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
public class FXMLMenuTutoriaController implements Initializable {

    @FXML
    private Button btnRegistrarHorario;
    @FXML
    private Button btnRegistrarAsistencia;
    @FXML
    private Button btnRegistrarFechaTutoria;
    @FXML
    private Button btnAsignarTutorado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarVistaPorRol();
    }

    private void configurarVistaPorRol() {
        String rol = Sesion.getRolActual();

        // Ocultar todo por defecto
        btnRegistrarHorario.setVisible(false);
        btnRegistrarAsistencia.setVisible(false);
        if (btnRegistrarFechaTutoria != null && btnAsignarTutorado != null) {
            btnRegistrarFechaTutoria.setVisible(false);
            btnAsignarTutorado.setVisible(false);

        }

        if ("TUTOR".equals(rol)) {
            // Mostrar solo lo que pediste
            btnRegistrarHorario.setVisible(true);
            btnRegistrarAsistencia.setVisible(true);
        }
    }

    @FXML
    private void clicRegistrarHoraTutoria(ActionEvent event) {
    }

    @FXML
    private void clicRegistrarAsistenciaTutorado(ActionEvent event) {
    }

    @FXML
    private void clicRegistrarFechaTutoria(ActionEvent event) {
    }

    @FXML
    private void clicAsignarTutorado(ActionEvent event) {
    }

}
