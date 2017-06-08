package Fpb.SistemaClinico.Controller.Medicos;

import Fpb.SistemaClinico.DAO.EspecialidadeDAO;
import Fpb.SistemaClinico.DAO.MedicoDAO;
import Fpb.SistemaClinico.Main;
import Fpb.SistemaClinico.Model.Medico;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class MedicoPaneCadastroController implements Initializable {
//Controladores da View FXML
    @FXML
    public ComboBox comboEspecialidade;
    @FXML
    public TextField fieldNome;
    @FXML
    public TextField fieldCrm;
    @FXML
    public Button btnLimpar;
    @FXML
    public Button btnSalvar;
    @FXML
    public Button btnAtualizar;

//Variaveis Globais
    private MedicoDAO medicoDAO;
    private Medico medico = null;

//Construtor
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        try {
            populaComboboxEspecialidade();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        medicoDAO = new MedicoDAO();

    }

//Pega Valores dos Fields e atibui ao objeto
    @FXML
    private void pegaValores(Medico m)
    {
        m.setNome(fieldNome.getText());
        m.setCrm(fieldCrm.getText());
        m.setEspecialidade(EspecialidadeDAO.selectIdByName(String.valueOf(comboEspecialidade.getValue())));
    }

//Salva Objeto Criando instancia Atribuindo os Valores dos Fields e Jogando no DAO
    @FXML
    public void salvarMedico() throws IOException
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
            Medico m = new Medico();
            pegaValores(m);
            medicoDAO.create(m);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Medico " + fieldNome.getText() + " Adicionado!");
            alert.showAndWait();
            //Limpa Campos Após a Confirmação
            limparFields();
        }
    }

//Atualiza Objeto pegando os valores dos Fields e Jogando no DAO
    @FXML
    public void atualizarMedico() throws IOException
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
            pegaValores(medico);
            medicoDAO.update(medico);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Médico " + fieldNome.getText() + " Atualizado!");
            alert.showAndWait();
        }
    }

//Lipa os Fields
    @FXML
    public void limparFields() throws IOException
    {
        fieldNome.setText("");
        fieldCrm.setText("");
    }

//Popula o Combobox com ObservableList do DAO
    @FXML
    private void populaComboboxEspecialidade() throws ParseException
    {
         ObservableList<String> options =
                 observableArrayList(EspecialidadeDAO.selectNames());
         this.comboEspecialidade.setItems(options);
    }

//Pega o Objeto e atribui aos Fields
    @FXML
    public void setaDetalhesMedico(Medico medico) throws IOException
    {
        if (medico != null) {
            this.medico = medico;
            fieldNome.setText(medico.getNome());
            fieldCrm.setText(medico.getCrm());
            comboEspecialidade.setValue(medico.getNomeEspecialidade());
        }
    }

//Abre uma Janela Abstraindo o Local do Arquivo o titulo e o objeto
    @FXML
    public void abrirJanela(String nameFile, String titlePage, Medico medico)
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
            MedicoPaneCadastroController controller = loader.getController();
            controller.setaDetalhesMedico(medico);
            if (medico == null){
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
    public boolean validaCampos()
    {
        if (fieldNome.getText().trim().equals("") || fieldCrm.getText().trim().equals("") || comboEspecialidade.getValue() == null){
            return true;
        }else{
            return false;
        }

    }
}
