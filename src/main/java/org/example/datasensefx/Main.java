package org.example.datasensefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.datasensefx.utils.SceneManager;

import java.io.IOException;

public class Main extends Application {
    @Override
    /*public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("DataSense v1.0");
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }*/

    public void start(Stage stage) throws IOException {
        SceneManager.setPrimaryStage(stage);
        SceneManager.switchScene("/org/example/datasensefx/views/login-view.fxml", "DataSense - Iniciar Sesión", 500, 650);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
