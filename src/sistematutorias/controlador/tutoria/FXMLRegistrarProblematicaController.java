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
    }

    // Método para recibir datos desde la ventana de Asistencia
    public void inicializarValores(int idTutoria, int idTutorado, String nombreAlumno) {
        this.idTutoria = idTutoria;
        this.idTutorado = idTutorado;
        this.lbNombreAlumno.setText(nombreAlumno);
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        String titulo = tfTitulo.getText();
        String descripcion = taDescripcion.getText();

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor llena todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        // Crear el objeto
        Problematica nuevaProblematica = new Problematica();
        nuevaProblematica.setIdTutoria(this.idTutoria);
        nuevaProblematica.setIdTutorado(this.idTutorado);
        nuevaProblematica.setTitulo(titulo);
        nuevaProblematica.setDescripcion(descripcion);
        nuevaProblematica.setFecha(LocalDate.now()); // Fecha de hoy

        // Guardar
        HashMap<String, Object> respuesta = ProblematicaImp.registrarProblematica(nuevaProblematica);

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