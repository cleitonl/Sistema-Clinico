package Fpb.SistemaClinico.Controller.Pacientes;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import Fpb.SistemaClinico.DAO.ConvenioDAO;
import Fpb.SistemaClinico.DAO.PacienteDAO;
import Fpb.SistemaClinico.Main;
import Fpb.SistemaClinico.Model.Paciente;
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

import static javafx.collections.FXCollections.observableArrayList;

public class PacientePaneCadastroController implements Initializable {

    @FXML
    public ComboBox comboConvenio;
    @FXML
    public ComboBox comboSexo;
    @FXML
    public TextField fieldNome;
    @FXML
    public TextField fieldRg;
    @FXML
    public TextField fieldEndereco;
    @FXML
    public TextField fieldTelefone;
    @FXML
    public TextField fieldCpf;
    @FXML
    public Button btnLimpar;
    @FXML
    public Button btnSalvar;
    @FXML
    public Button btnAtualizar;

    PacienteDAO pacienteDAO;

    private Paciente paciente = null;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        try {
            populaComboboxSexo();
            populaComboboxConvenio();
            pacienteDAO = new PacienteDAO();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void pegaValores(Paciente p)
    {
        p.setNome(fieldNome.getText());
        p.setRg(fieldRg.getText());
        p.setCpf(fieldCpf.getText());
        p.setEnd(fieldEndereco.getText());
        p.setTelefone(fieldTelefone.getText());
        p.setSexo(String.valueOf(comboSexo.getValue()));
        p.setConveniopaciente(ConvenioDAO.selectIdByName(String.valueOf(comboConvenio.getValue())));
    }
    @FXML
    public void salvarPaciente() throws IOException
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
            Paciente p = new Paciente();
            pegaValores(p);
            pacienteDAO.create(p);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Pacientes " + fieldNome.getText() + " Adicionado!");
            alert.showAndWait();
            //Limpa Campos Após a Confirmação
            limparFields();
        }
    }
    @FXML
    public void atualizarPaciente() throws IOException
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
            pegaValores(paciente);
            pacienteDAO.update(paciente);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText(null);
            alert.setContentText("Pacientes " + fieldNome.getText() + " Atualizado!");
            alert.showAndWait();
        }
    }
    @FXML
    public void limparFields() throws IOException
    {
        fieldNome.setText("");
        fieldRg.setText("");
        fieldCpf.setText("");
        fieldEndereco.setText("");
        fieldTelefone.setText("");
    }
    @FXML
    public void populaComboboxSexo() throws ParseException
    {
        ObservableList<String> options =
                observableArrayList("Masculino","Feminino");
        this.comboSexo.setItems(options);
    }
    @FXML
    public void populaComboboxConvenio() throws ParseException
    {
        ObservableList<String> options =
                observableArrayList(ConvenioDAO.selectNames());
                this.comboConvenio.setItems(options);
        //System.out.println(ConvenioDAO.readNames());
        //this.comboConvenio.getItems().addAll(ConvenioDAO.readNames());
    }
    @FXML
    public void setaDetalhesPaciente(Paciente paciente) throws IOException
    {
        if (paciente != null) {
            this.paciente = paciente;
            fieldNome.setText(paciente.getNome());
            fieldRg.setText(paciente.getRg());
            fieldCpf.setText(paciente.getCpf());
            fieldEndereco.setText(paciente.getEnd());
            fieldTelefone.setText(paciente.getTelefone());
            comboSexo.setValue(paciente.getSexo());
            comboConvenio.setValue(paciente.getNomeconvenio());
        }
    }
    @FXML
    public void abrirJanela(String nameFile, String titlePage, Paciente paciente)
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
            PacientePaneCadastroController controller = loader.getController();
            controller.setaDetalhesPaciente(paciente);
            if (paciente == null){
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
    public boolean validaCampos()
    {
        if (fieldNome.getText().trim().equals("") || fieldRg.getText().trim().equals("")
                || fieldCpf.getText().trim().equals("") || fieldEndereco.getText().trim().equals("")
                || fieldTelefone.getText().trim().equals("") || comboConvenio.getValue() == null
                || comboSexo.getValue() == null ){
            return true;
        }else{
            return false;
        }

    }

}



