// Clase SceneManager para administrar el cambio de escenas
package org.example.datasensefx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile, String title) throws IOException {
        // Construir la ruta completa del archivo FXML
        String fxmlPath = fxmlFile.startsWith("/") ? fxmlFile : "/org/example/datasensefx/views/" + fxmlFile;

        System.out.println("🔍 [SceneManager] Intentando cargar: " + fxmlPath);

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

        if (loader.getLocation() == null) {
            System.out.println("❌ [SceneManager] No se encontró el archivo: " + fxmlPath);
            throw new IOException("No se encontró el archivo FXML: " + fxmlPath);
        }

        System.out.println("✅ [SceneManager] Archivo encontrado: " + loader.getLocation());
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void switchScene(String fxmlFile, String title, int width, int height) throws IOException {
        // Construir la ruta completa del archivo FXML
        String fxmlPath = fxmlFile.startsWith("/") ? fxmlFile : "/org/example/datasensefx/views/" + fxmlFile;

        System.out.println("🔍 [SceneManager] Intentando cargar: " + fxmlPath);

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

        if (loader.getLocation() == null) {
            System.out.println("❌ [SceneManager] No se encontró el archivo: " + fxmlPath);
            throw new IOException("No se encontró el archivo FXML: " + fxmlPath);
        }

        System.out.println("✅ [SceneManager] Archivo encontrado: " + loader.getLocation());
        Parent root = loader.load();

        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static <T> T switchSceneWithController(String fxmlFile, String title) throws IOException {
        // Construir la ruta completa del archivo FXML
        String fxmlPath = fxmlFile.startsWith("/") ? fxmlFile : "/org/example/datasensefx/views/" + fxmlFile;

        System.out.println("🔍 [SceneManager] Intentando cargar: " + fxmlPath);

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

        if (loader.getLocation() == null) {
            System.out.println("❌ [SceneManager] No se encontró el archivo: " + fxmlPath);
            throw new IOException("No se encontró el archivo FXML: " + fxmlPath);
        }

        System.out.println("✅ [SceneManager] Archivo encontrado: " + loader.getLocation());
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();

        return loader.getController();
    }
}