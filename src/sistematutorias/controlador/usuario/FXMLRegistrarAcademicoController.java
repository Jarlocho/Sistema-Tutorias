package sistematutorias.controlador.usuario;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FXMLRegistrarAcademicoController implements Initializable {

    @FXML private TextField txtNumeroPersonal;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidoPaterno;
    @FXML private TextField txtApellidoMaterno;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtCorreoInstitucional;

    @FXML private ComboBox<String> cbRol;
    @FXML private ComboBox<String> cbAcademia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        
    }
}
