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
        String noPersonal = tfUsuario.getText();
        String password = pfContrasenia.getText();
        validarSesion(noPersonal, password);

    }

    private void validarSesion(String noPersonal, String password) {
    HashMap<String, Object> respuesta = AutenticacionImp.verificarSesionTutor(noPersonal, password);

    boolean error = (boolean) respuesta.get("error");
    if (!error) {
        Tutor tutorSesion = (Tutor) respuesta.get("tutor");
        Utilidades.mostrarAlertaSimple(
                "Credenciales correctas",
                "Bienvenido(a) tutor " + tutorSesion.getNombre() + ", al sistema de administración de tutorias.",
                Alert.AlertType.INFORMATION
        );
        irPantallaPrincipal(tutorSesion);
    } else {
        Utilidades.mostrarAlertaSimple(
                "Credenciales incorrectas",
                "No. de personal y/o contraseña incorrectos, por favor verifica la información",
                Alert.AlertType.ERROR
        );
    }
}


    private void irPantallaPrincipal(Tutor tutorSesion) {
        try {
            FXMLLoader cargador = new FXMLLoader(SistemaTutorias.class.getResource("vista/FXMLMenuPrincipal.fxml"));
            Parent vista = cargador.load();
            FXMLMenuPrincipalController controlador = cargador.getController();
            //Creamos escena y reutilizamos escenario
            Scene escena = new Scene(vista);
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            escenario.setScene(escena);
            escenario.setTitle("Inicio");
            escenario.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
