package Fpb.SistemaClinico.Controller.Medicos;
import Fpb.SistemaClinico.DAO.MedicoDAO;
import Fpb.SistemaClinico.Model.Medico;
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

public class MedicoAnchorController implements Initializable {
//Controladores da View FXML
    @FXML
    private TableView<Medico> tabelaMedicos;
    @FXML
    private TableColumn<Medico, String> medicoNome;
    @FXML
    private TableColumn<Medico, String> medicoCrm;
    @FXML
    private TableColumn<Medico, String> medicoEspecialidade;
    @FXML
    public TextField fieldSearch;

//Variaveis Globais
    private Medico medicoSelecionado;
    private MedicoDAO medicoDAO = new MedicoDAO();

//Construtor
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populaTabela();
    }

//Atribui na Table o Arraylist de Objetos do DAO
    @FXML
    private void populaTabela(){
        ObservableList<Medico> medicos = observableArrayList(medicoDAO.read());
        medicoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        medicoCrm.setCellValueFactory(new PropertyValueFactory<>("crm"));
        medicoEspecialidade.setCellValueFactory(new PropertyValueFactory<>("nomeEspecialidade"));
        tabelaMedicos.setItems(FXCollections.observableArrayList(medicos));
    }

//Atribui na Table o Arraylist de resultados do DAO - Metodo Chamado a cada tecla digitada
    public void pesquisa(){
        ObservableList<Medico> search = observableArrayList(medicoDAO.search(fieldSearch.getText()));
        medicoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        medicoCrm.setCellValueFactory(new PropertyValueFactory<>("crm"));
        medicoEspecialidade.setCellValueFactory(new PropertyValueFactory<>("nomeEspecialidade"));
        tabelaMedicos.setItems(FXCollections.observableArrayList(search));
    }

//Pega o objeto selecionado na ObservableList"Tabela"
    @FXML
    private void getMedicoSelecionado() throws IOException {
        medicoSelecionado = tabelaMedicos.getSelectionModel().getSelectedItem();
    }

//Abre o Painel de cadastro abtraido, passando o caminho o titulo com um objeto Nulo
    @FXML
    public void newMedico() {
        new MedicoPaneCadastroController().abrirJanela("./View/MedicoPaneCadastro.fxml", "Novo Médico", null);
        populaTabela();

    }

//Exclui um Objeto com confirmação previa
    @FXML
    public void deleteMedico() throws IOException {
        getMedicoSelecionado();
        if (medicoSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Pacientes");
            alert.setHeaderText("O Pacientes " + medicoSelecionado.getNome() + " será Excluido");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                medicoDAO.delete(medicoSelecionado);
            }
        }else{
            // Nada seleciondo.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Pacientes Selecionado");
            alert.setContentText("Por favor, selecione um Pacientes na tabela.");
            alert.showAndWait();
        }
        populaTabela(); //atualiza tabela
    }

//Abre o Painel de Edição abtraido, passando o caminho o titulo com um objeto Selecionado na variavel Global
    @FXML
    public void editMedico() throws IOException {
        getMedicoSelecionado();
        if (medicoSelecionado != null) {
            new MedicoPaneCadastroController().abrirJanela("./View/MedicoPaneCadastro.fxml", "Editar Médico", medicoSelecionado);
        }else{
            // Nada seleciondo.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhum Pacientes Selecionado");
            alert.setContentText("Por favor, selecione um Pacientes na tabela.");
            alert.showAndWait();
        }
        populaTabela();//Atualiza Tabela
    }
}