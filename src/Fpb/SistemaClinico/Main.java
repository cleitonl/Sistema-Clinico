package Fpb.SistemaClinico;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        principalStage();
    }

    @FXML
    public void principalStage(){
        try {
            // Carrega o arquivo fxml e cria a Scene Principal.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Main.fxml"));
            rootLayout = loader.load();

            // Cria o palco para Aplicação.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("Sistema Clinico");
            primaryStage.getIcons().add(new Image("Fpb/SistemaClinico/View/Img/ico.png"));
            primaryStage.setResizable(true);
            primaryStage.show();
            // Carrega o root layout do arquivo fxml.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public static void abrirAncora(String nameFile) {
        try {
            // Carrega o person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(nameFile));
            AnchorPane personOverview = loader.load();

            // Define o person overview dentro do root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    //Retorna o palco principal.
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    @FXML
    public static void main(String[] args) {
        launch(args);

    }



}