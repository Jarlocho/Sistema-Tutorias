/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistematutorias.controlador.tutoria;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import sistematutorias.dominio.AsistenciaImp;
import sistematutorias.dominio.TutoriaImp;
import sistematutorias.modelo.dao.TutoriaDAO;
import sistematutorias.modelo.pojo.AsistenciaRow;
import sistematutorias.modelo.pojo.Tutoria;
import utilidad.Sesion;
import utilidad.Utilidades;

public class FXMLRegistrarAsistenciaTutoradoController implements Initializable {

    @FXML
    private ComboBox<Tutoria> cbSesiones;
    @FXML
    private TableView<AsistenciaRow> tvAsistencia;
    @FXML
    private TableColumn<AsistenciaRow, String> colMatricula;
    @FXML
    private TableColumn<AsistenciaRow, String> colNombre;
    @FXML
    private TableColumn<AsistenciaRow, Integer> colSemestre;
    @FXML
    private TableColumn<AsistenciaRow, Boolean> colAsistio;
    @FXML
    private TableColumn<AsistenciaRow, Void> colAcciones;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnSubirEvidencia;
    @FXML
    private Label lbErrorSesion;
    @FXML
    private Label lbMensajeInfo;

    private ObservableList<AsistenciaRow> listaAlumnos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarSesiones();
    }

    private void configurarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));

        colAsistio.setCellValueFactory(cellData -> cellData.getValue().asistioProperty());
        colAsistio.setCellFactory(CheckBoxTableCell.forTableColumn(colAsistio));

        addButtonToTable();

        tvAsistencia.setEditable(true);
    }

    private void addButtonToTable() {
        Callback<TableColumn<AsistenciaRow, Void>, TableCell<AsistenciaRow, Void>> cellFactory = new Callback<TableColumn<AsistenciaRow, Void>, TableCell<AsistenciaRow, Void>>() {
            @Override
            public TableCell<AsistenciaRow, Void> call(final TableColumn<AsistenciaRow, Void> param) {
                final TableCell<AsistenciaRow, Void> cell = new TableCell<AsistenciaRow, Void>() {
                    private final Button btn = new Button("Problemática");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            AsistenciaRow data = getTableView().getItems().get(getIndex());
                            if (data.isAsistio()) {
                                abrirVentanaProblematica(data.getIdTutorado(), data.getNombreCompleto());
                            } else {
                                Utilidades.mostrarAlertaSimple("Aviso", "El alumno debe tener asistencia para registrar problemática.", Alert.AlertType.WARNING);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colAcciones.setCellFactory(cellFactory);
    }

    private void cargarSesiones() {
        int idTutor = Sesion.getTutorSesion().getIdTutor();
        HashMap<String, Object> respuesta = AsistenciaImp.obtenerSesionesTutor(idTutor);

        if (!(boolean) respuesta.get("error")) {
            ArrayList<Tutoria> sesiones = (ArrayList<Tutoria>) respuesta.get("sesiones");
            cbSesiones.setItems(FXCollections.observableArrayList(sesiones));
            cbSesiones.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    cargarAlumnos();
                    cargarEstadoEvidencia(newVal.getIdTutoria());
                }
            });
        } else {
            Utilidades.mostrarAlertaSimple("Sin sesiones", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
        }
    }

    private void cargarEstadoEvidencia(int idTutoria) {
        try {
            boolean tieneEvidencia = TutoriaDAO.comprobarExistenciaEvidencia(idTutoria);

            if (tieneEvidencia) {
                btnSubirEvidencia.setDisable(true);
                lbMensajeInfo.setText("Ya se ha subido evidencia para esta sesión.");
                lbMensajeInfo.setStyle("-fx-text-fill: #2e7d32;");
                lbMensajeInfo.setVisible(true);
                lbMensajeInfo.setManaged(true);
            } else {
                btnSubirEvidencia.setDisable(true);
                lbMensajeInfo.setVisible(false);
                lbMensajeInfo.setManaged(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaProblematica(int idTutorado, String nombreAlumno) {
        try {
            Tutoria sesionActual = cbSesiones.getValue();
            if (sesionActual == null) {
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistematutorias/vista/tutoria/FXMLRegistrarProblematica.fxml"));
            Parent root = loader.load();
            FXMLRegistrarProblematicaController controlador = loader.getController();
            controlador.inicializarValores(sesionActual.getIdTutoria(), idTutorado, nombreAlumno);
            Stage escenario = new Stage();
            escenario.setScene(new Scene(root));
            escenario.setTitle("Registrar Problemática");
            escenario.initModality(javafx.stage.Modality.APPLICATION_MODAL); // Bloquea la ventana de atrás
            escenario.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cargarAlumnos() {
        int idTutor = Sesion.getTutorSesion().getIdTutor();
        HashMap<String, Object> respuesta = AsistenciaImp.obtenerListaAsistencia(idTutor);
        if (!(boolean) respuesta.get("error")) {
            listaAlumnos = FXCollections.observableArrayList((ArrayList<AsistenciaRow>) respuesta.get("tutorados"));
            tvAsistencia.setItems(listaAlumnos);
        }
    }

    @FXML
    private void clicRegistrar(ActionEvent event) {
        Tutoria sesion = cbSesiones.getValue();
        if (sesion == null) {
            lbErrorSesion.setVisible(true);
            lbErrorSesion.setText("Selecciona una sesión");
            return;
        }

        HashMap<String, Object> res = AsistenciaImp.guardarListaAsistencia(sesion.getIdTutoria(), new ArrayList<>(listaAlumnos));
        if (!(boolean) res.get("error")) {
            Utilidades.mostrarAlertaSimple("Éxito", (String) res.get("mensaje"), Alert.AlertType.INFORMATION);
            btnSubirEvidencia.setDisable(false); // Habilitar subir evidencia
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) res.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicSubirEvidencia(ActionEvent event) {
        Tutoria sesion = cbSesiones.getValue();
        if (sesion == null) {
            return;
        }

        // 1. Configurar FileChooser
        FileChooser dialogo = new FileChooser();
        dialogo.setTitle("Seleccionar evidencia (PDF)");
        dialogo.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

        // 2. Abrir diálogo
        File archivo = dialogo.showOpenDialog(btnSubirEvidencia.getScene().getWindow());

        if (archivo != null) {
            // 3. Validar tamaño (Máximo 5MB = 5 * 1024 * 1024 bytes)
            if (archivo.length() > 5 * 1024 * 1024) {
                Utilidades.mostrarAlertaSimple("Archivo muy pesado",
                        "El archivo debe pesar menos de 5MB.", Alert.AlertType.WARNING);
                return;
            }

            try {
                // 4. Leer bytes y enviar al Dominio
                byte[] datosArchivo = Files.readAllBytes(archivo.toPath());

                HashMap<String, Object> respuesta = TutoriaImp.subirEvidencia(sesion.getIdTutoria(), datosArchivo);

                if (!(boolean) respuesta.get("error")) {
                    Utilidades.mostrarAlertaSimple("Éxito", (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);
                    btnSubirEvidencia.setDisable(true); // Bloquear botón para no subir dos veces
                } else {
                    Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
                }
            } catch (Exception ex) {
                Utilidades.mostrarAlertaSimple("Error", "Error al leer el archivo: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        try {
            Stage escenario = (Stage) tvAsistencia.getScene().getWindow();
            URL url = getClass().getResource("/sistematutorias/vista/FXMLMenuTutoria.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Menú Tutoría");
            escenario.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error de navegación",
                    "No se pudo cargar el menú principal.",
                    Alert.AlertType.ERROR);
        }
    }
}
