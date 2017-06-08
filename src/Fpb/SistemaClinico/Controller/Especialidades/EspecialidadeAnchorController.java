package Fpb.SistemaClinico.Controller.Especialidades;

import Fpb.SistemaClinico.DAO.EspecialidadeDAO;
import Fpb.SistemaClinico.Model.Especialidade;
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

public class EspecialidadeAnchorController implements Initializable {
//Controladores da View FXML
    @FXML
    private TableView<Especialidade> tabelaEspecialidades;
    @FXML
    private TableColumn<Especialidade, String> especialidadeNome;
    @FXML
    public TextField fieldSearch;

//Variaveis Globais
    private Especialidade especialidadeSelecionado;
    private EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

//Construtor
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        populaTabela();
    }

//Atribui na Table o Arraylist de Objetos do DAO
    @FXML
    private void populaTabela()
    {
        ObservableList<Especialidade> especialidades = observableArrayList(especialidadeDAO.read());
        especialidadeNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabelaEspecialidades.setItems(FXCollections.observableArrayList(especialidades));
    }

//Atribui na Table o Arraylist de resultados do DAO - Metodo Chamado a cada tecla digitada
    @FXML
    public void pesquisa()
    {
        ObservableList<Especialidade> search = observableArrayList(especialidadeDAO.search(fieldSearch.getText()));
        especialidadeNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabelaEspecialidades.setItems(FXCollections.observableArrayList(search));
    }

//Pega o objeto selecionado na ObservableList"Tabela"
    @FXML
    private void getEspecialidadeSelecionado() throws IOException
    {
        especialidadeSelecionado = tabelaEspecialidades.getSelectionModel().getSelectedItem();
    }

//Abre o Painel de cadastro abtraido, passando o caminho o titulo com um objeto Nulo
    @FXML
    public void newEspecialidade()
    {
        new EspecialidadePaneCadastroController().abrirJanela("./View/EspecialidadePaneCadastro.fxml", "Nova Especialidade", null);
        //Atualiza Tabela
        populaTabela();

    }

//Exclui um Objeto com confirmação previa
    @FXML
    public void deleteEspecialidade() throws IOException
    {
        getEspecialidadeSelecionado();
        if (especialidadeSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Pacientes");
            alert.setHeaderText("O Pacientes " + especialidadeSelecionado.getNome() + " será Excluido");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                especialidadeDAO.delete(especialidadeSelecionado);
            }
        } else {
            // Nada seleciondo.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhuma Especialidade Selecionado");
            alert.setContentText("Por favor, selecione uma Especialidade na tabela.");
            alert.showAndWait();
        }
        //Atualiza Tabela
        populaTabela();
    }

//Abre o Painel de Edição abtraido, passando o caminho o titulo com um objeto Selecionado na variavel Global
    @FXML
    public void editEspecialidade() throws IOException
    {
        getEspecialidadeSelecionado();
        if (especialidadeSelecionado != null) {
            new EspecialidadePaneCadastroController().abrirJanela("./View/EspecialidadePaneCadastro.fxml", "Editar Especialidade", especialidadeSelecionado);
        } else {
            // Nada seleciondo.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText("Nenhuma Especialidade Selecionada");
            alert.setContentText("Por favor, selecione uma Especialidade na tabela.");
            alert.showAndWait();
        }
        //Atualiza Tabela
        populaTabela();
    }
}