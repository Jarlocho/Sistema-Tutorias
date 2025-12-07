/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistematutorias.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistematutorias.SistemaTutorias;
import sistematutorias.dominio.AutenticacionImp;
import sistematutorias.modelo.pojo.Tutor;
import utilidad.Utilidades;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfContrasenia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnClicIniciarSesion(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String password = pfContrasenia.getText();

        if (usuario.isEmpty() || password.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos",
                    "Por favor ingresa usuario y contraseña", Alert.AlertType.WARNING);
        } else {
            // Verificamos si es Tutor (aquí podrías agregar 'else if' para coordinadores en el futuro)
            boolean esTutor = AutenticacionImp.iniciarSesionTutor(usuario, password);

            if (esTutor) {
                Utilidades.mostrarAlertaSimple("Bienvenido",
                        "Credenciales correctas", Alert.AlertType.INFORMATION);
                irMenuPrincipal();
            } else {
                Utilidades.mostrarAlertaSimple("Error",
                        "Credenciales incorrectas", Alert.AlertType.ERROR);
            }
        }
    }

    private void irMenuPrincipal() {
        try {
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistematutorias/vista/FXMLMenuPrincipal.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Menú Principal");
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
