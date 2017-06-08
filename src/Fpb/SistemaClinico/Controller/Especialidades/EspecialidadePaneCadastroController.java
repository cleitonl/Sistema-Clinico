package Fpb.SistemaClinico.Controller.Especialidades;

import Fpb.SistemaClinico.DAO.EspecialidadeDAO;
import Fpb.SistemaClinico.Main;
import Fpb.SistemaClinico.Model.Especialidade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EspecialidadePaneCadastroController implements Initializable {
//Controladores da View FXML
    @FXML
    public TextField fieldNome;
    @FXML
    public Button btnLimpar;
    @FXML
    public Button btnSalvar;
    @FXML
    public Button btnAtualizar;

//Variaveis Globais
    private EspecialidadeDAO especialidadeDAO;
    private Especialidade especialidade = null;

//Construtor
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        especialidadeDAO = new EspecialidadeDAO();
    }

//Pega Valores dos Fields e atibui ao objeto
    @FXML
    private void pegaValores(Especialidade e)
    {
        e.setNome(fieldNome.getText());
    }

//Salva Objeto Criando instancia Atribuindo os Valores dos Fields e Jogando no DAO
    @FXML
    public void salvarEspecialidade() throws IOException
    {
        if (validaCampos())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avisos!");
            alert.setHeaderText(null);
            alert.setContentText("Preencha Todos os Campos!");
            alert.showAndWait();
        }
        else
        {
            Especialidade e = new Especialidade();
            pegaValores(e);
            especialidadeDAO.create(e);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Especialidade " + fieldNome.getText() + " Adicionado!");
            alert.showAndWait();
            //Limpa Campos Após a Confirmação
            limparFields();
        }
    }

//Atualiza Objeto pegando os valores dos Fields e Jogando no DAO
    @FXML
    public void atualizarEspecialidade() throws IOException
    {
        if (validaCampos())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso!");
            alert.setHeaderText(null);
            alert.setContentText("Preencha Todos os Campos!");
            alert.showAndWait();
        }
        else
        {
            pegaValores(especialidade);
            especialidadeDAO.update(especialidade);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Especialidade " + fieldNome.getText() + " Atualizada!");
            alert.showAndWait();
        }
    }

//Lipa os Fields
    @FXML
    public void limparFields() throws IOException
    {
        fieldNome.setText("");
    }

//Pega o Objeto e atribui aos Fields
    @FXML
    private void setaDetalhesEspecialidade(Especialidade especialidade) throws IOException
    {
        if (especialidade != null) {
            this.especialidade = especialidade;
            fieldNome.setText(especialidade.getNome());
        }
    }

//Abre uma Janela Abstraindo o Local do Arquivo o titulo e o objeto
    @FXML
    public void abrirJanela(String nameFile, String titlePage, Especialidade especialidade)
    {
        try {
            // Carrega o arquivo fxml e cria um novo stage para a janela popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(nameFile));
            AnchorPane page = loader.load();
            // Cria o Stage da Janela.
            Stage stageJanela = new Stage();
            stageJanela.setTitle(titlePage);
            stageJanela.getIcons().add(new Image("Fpb/SistemaClinico/View/Img/ico.png"));
            stageJanela.initModality(Modality.WINDOW_MODAL);
            stageJanela.initOwner(Main.primaryStage);
            Scene scene = new Scene(page);
            stageJanela.setScene(scene);

            // Define a pessoa no controller.
            EspecialidadePaneCadastroController controller = loader.getController();
            controller.setaDetalhesEspecialidade(especialidade);
            if (especialidade == null){
                controller.btnAtualizar.setDisable(true);
            }else {
                controller.btnSalvar.setDisable(true);
            }

            stageJanela.setOpacity(0.95);
            stageJanela.setResizable(false);
            stageJanela.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Valida os Campos
    private boolean validaCampos()
    {
        return fieldNome.getText().trim().equals("");
    }
}
