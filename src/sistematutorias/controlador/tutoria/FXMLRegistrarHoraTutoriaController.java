/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistematutorias.controlador.tutoria;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import sistematutorias.dominio.TutoriaImp;
import sistematutorias.modelo.pojo.FechaTutoria;
import sistematutorias.modelo.pojo.Tutoria;
import utilidad.Sesion;
import utilidad.Utilidades;

public class FXMLRegistrarHoraTutoriaController implements Initializable {

    @FXML
    private ComboBox<FechaTutoria> cbFechas; 
    @FXML
    private Spinner<Integer> spHora;
    @FXML
    private Spinner<Integer> spMinuto;
    @FXML
    private Label lbErrorFecha;
    @FXML
    private Label lbErrorHora;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarSpinners();
        cargarFechas();
    }    

    private void configurarSpinners() {
        // Horas de 7 a 20
        SpinnerValueFactory<Integer> horasFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 20, 10);
        spHora.setValueFactory(horasFactory);
        
        // Minutos de 0 a 59
        SpinnerValueFactory<Integer> minutosFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        spMinuto.setValueFactory(minutosFactory);
    }

    private void cargarFechas() {
        HashMap<String, Object> respuesta = TutoriaImp.obtenerFechasPeriodoActual();
        if (!(boolean) respuesta.get("error")) {
            ArrayList<FechaTutoria> lista = (ArrayList<FechaTutoria>) respuesta.get("fechas");
            ObservableList<FechaTutoria> fechasObs = FXCollections.observableArrayList(lista);
            cbFechas.setItems(fechasObs);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        lbErrorFecha.setText("");
        lbErrorHora.setText("");
        
        FechaTutoria fechaSeleccionada = cbFechas.getValue();
        Integer hora = spHora.getValue();
        Integer minuto = spMinuto.getValue();

        if (fechaSeleccionada == null) {
            lbErrorFecha.setText("Selecciona una fecha");
            return;
        }

        // Construir objeto Tutoria
        Tutoria nuevaTutoria = new Tutoria();
        nuevaTutoria.setIdTutor(Sesion.getTutorSesion().getIdTutor()); // Sacamos ID de la sesión
        // nuevaTutoria.setIdPeriodo(Sesion.getIdPeriodoActual()); // Implementar si tienes lógica de periodos
        nuevaTutoria.setFecha(fechaSeleccionada.getFecha());
        nuevaTutoria.setHoraInicio(LocalTime.of(hora, minuto));
        
        guardarTutoria(nuevaTutoria);
    }

    private void guardarTutoria(Tutoria tutoria) {
        HashMap<String, Object> respuesta = TutoriaImp.registrarHorarioTutoria(tutoria);
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlertaSimple("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
            clicVolver(null);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicCancelar(ActionEvent event) {
        cbFechas.getSelectionModel().clearSelection();
        spHora.getValueFactory().setValue(10);
        spMinuto.getValueFactory().setValue(0);
        lbErrorFecha.setText("");
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        try {
            Stage escenario = (Stage) cbFechas.getScene().getWindow();
            URL url = getClass().getResource("/sistematutorias/vista/FXMLMenuTutoria.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Menú Tutoría");
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo volver al menú.", Alert.AlertType.ERROR);
        }
    }
}