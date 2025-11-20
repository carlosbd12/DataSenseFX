package org.example.datasensefx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.datasensefx.utils.SceneManager;

import java.io.IOException;

public class Main extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        SceneManager.setPrimaryStage(stage);
        SceneManager.switchScene("/org/example/datasensefx/views/login-view.fxml", "DataSense - Iniciar Sesión", 500, 650);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
