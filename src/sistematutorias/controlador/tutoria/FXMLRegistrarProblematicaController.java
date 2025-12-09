/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistematutorias.controlador.tutoria;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistematutorias.dominio.ProblematicaImp;
import sistematutorias.modelo.pojo.Problematica;
import utilidad.Utilidades;

public class FXMLRegistrarProblematicaController implements Initializable {

    @FXML
    private Label lbNombreAlumno;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextArea taDescripcion;

    private int idTutoria;
    private int idTutorado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 120) {
                tfTitulo.setText(oldValue);
            }
        });

        taDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 500) {
                taDescripcion.setText(oldValue);
            }
        });
    }

    public void inicializarValores(int idTutoria, int idTutorado, String nombreAlumno) {
        this.idTutoria = idTutoria;
        this.idTutorado = idTutorado;
        this.lbNombreAlumno.setText(nombreAlumno);
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        String titulo = tfTitulo.getText();
        String descripcion = taDescripcion.getText();

        if (!sonDatosValidos(titulo, descripcion)) {
            return;
        }

        String tituloLimpio = titulo.trim();
        String descripcionLimpia = descripcion.trim();

        Problematica nuevaProblematica = prepararProblematica(tituloLimpio, descripcionLimpia);
        enviarAlDominio(nuevaProblematica);
    }

    private boolean sonDatosValidos(String titulo, String descripcion) {
        if (titulo == null || descripcion == null) {
            Utilidades.mostrarAlertaSimple(
                    "Campos vacíos",
                    "Por favor llena todos los campos.",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        String tituloLimpio = titulo.trim();
        String descripcionLimpia = descripcion.trim();

        if (tituloLimpio.isEmpty() || descripcionLimpia.isEmpty()) {
            Utilidades.mostrarAlertaSimple(
                    "Campos vacíos",
                    "Título y descripción no pueden estar vacíos ni contener solo espacios en blanco.",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        if (tituloLimpio.length() > 120) {
            Utilidades.mostrarAlertaSimple(
                    "Título demasiado largo",
                    "El título no puede exceder los 120 caracteres. Actualmente tiene "
                    + tituloLimpio.length() + ".",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        if (descripcionLimpia.length() > 500) {
            Utilidades.mostrarAlertaSimple(
                    "Descripción demasiado larga",
                    "La descripción no puede exceder los 500 caracteres. Actualmente tiene "
                    + descripcionLimpia.length() + ".",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        return true;
    }

    private Problematica prepararProblematica(String titulo, String descripcion) {
        Problematica p = new Problematica();
        p.setIdTutoria(this.idTutoria);
        p.setIdTutorado(this.idTutorado);
        p.setTitulo(titulo);
        p.setDescripcion(descripcion);
        p.setFecha(LocalDate.now());
        return p;
    }

    private void enviarAlDominio(Problematica problematica) {
        HashMap<String, Object> respuesta = ProblematicaImp.registrarProblematica(problematica);

        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlertaSimple("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfTitulo.getScene().getWindow();
        stage.close();
    }
}
