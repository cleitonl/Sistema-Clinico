package Fpb.SistemaClinico.Controller;

import Fpb.SistemaClinico.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Fpb.SistemaClinico.Model.Medico;
import Fpb.SistemaClinico.Model.Paciente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clickMenuCadastroPaciente() throws IOException{
        // Solicita a Pagina!
        Main.abrirAncora("./View/PacienteAnchor.fxml");
    }
    @FXML
    public void clickMenuCadastroMedico() throws IOException{
        // Solicita a Pagina!
        Main.abrirAncora("./View/MedicoAnchor.fxml");
    }
    @FXML
    public void clickMenuEspecialidade() throws IOException{
        // Solicita a Pagina!
        Main.abrirAncora("./View/EspecialidadeAnchor.fxml");
    }

}
