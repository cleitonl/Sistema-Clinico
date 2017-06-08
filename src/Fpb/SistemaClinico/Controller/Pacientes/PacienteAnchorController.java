package Fpb.SistemaClinico.Controller.Pacientes;
import Fpb.SistemaClinico.DAO.PacienteDAO;
import Fpb.SistemaClinico.Model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableArrayList;

public class PacienteAnchorController implements Initializable {

    @FXML
    private TableView<Paciente> tabelaPacientes;
    @FXML
    private TableColumn<Paciente, String> pacienteNome;
    @FXML
    private TableColumn<Paciente, String> pacienteRg;
    @FXML
    private TableColumn<Paciente, String> pacienteCpf;
    @FXML
    private TableColumn<Paciente, String> PacienteEndereco;
    @FXML
    private TableColumn<Paciente, String> pacienteTelefone;
    @FXML
    private TableColumn<Paciente, String> pacienteSexo;
    @FXML
    private TableColumn<Paciente, String> pacienteConvenio;
    @FXML
    public TextField fieldSearch;

    private Paciente pacienteSelecionado;
    PacienteDAO pacienteDAO = new PacienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populaTabela();
    }

    @FXML
    public void populaTabela(){
        ObservableList<Paciente> pacientes = observableArrayList(pacienteDAO.read());
        pacienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        pacienteRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        pacienteCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        PacienteEndereco.setCellValueFactory(new PropertyValueFactory<>("end"));
        pacienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        pacienteSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        pacienteConvenio.setCellValueFactory(new PropertyValueFactory<>("nomeconvenio"));
        tabelaPacientes.setItems(FXCollections.observableArrayList(pacientes));
    }


    public void pesquisa(){
        ObservableList<Paciente> search = observableArrayList(pacienteDAO.search(fieldSearch.getText()));
        pacienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        pacienteRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        pacienteCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        PacienteEndereco.setCellValueFactory(new PropertyValueFactory<>("end"));
        pacienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        pacienteSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        pacienteConvenio.setCellValueFactory(new PropertyValueFactory<>("nomeconvenio"));
        tabelaPacientes.setItems(FXCollections.observableArrayList(search));
    }

    @FXML
    public Paciente getPacienteSelecionado() throws IOException {
        pacienteSelecionado = tabelaPacientes.getSelectionModel().getSelectedItem();
        return pacienteSelecionado;

    }

    @FXML
    public void newPaciente() {
        new PacientePaneCadastroController().abrirJanela("./View/PacientePaneCadastro.fxml", "Novo Pacientes", null);
        populaTabela();

    }

    @FXML
    public void deletePaciente() throws IOException {
        getPacienteSelecionado();
        if (pacienteSelecionado != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Pacientes");
        alert.setHeaderText("O Pacientes " + pacienteSelecionado.getNome() + " será Excluido");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            pacienteDAO.delete(pacienteSelecionado);
        } else {

        }
        }else{
            // Nada seleciondo.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Pacientes Selecionado");
            alert.setContentText("Por favor, selecione um Pacientes na tabela.");
            alert.showAndWait();
        }
        populaTabela();
    }

    @FXML
    public void editPaciente() throws IOException {
        getPacienteSelecionado();
        if (pacienteSelecionado != null) {
            new PacientePaneCadastroController().abrirJanela("./View/PacientePaneCadastro.fxml", "Editar Pacientes", pacienteSelecionado);
        }else{
            // Nada seleciondo.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Pacientes Selecionado");
            alert.setContentText("Por favor, selecione um Pacientes na tabela.");
            alert.showAndWait();
        }
        populaTabela();
    }






}