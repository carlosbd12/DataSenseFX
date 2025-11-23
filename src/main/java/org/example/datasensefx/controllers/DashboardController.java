package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

public class DashboardController {

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnDispositivos;

    @FXML
    private Button btnInformes;

    @FXML
    private Button btnConfiguracion;

    @FXML
    private Label lblUserName;

    /**
     * Método que se ejecuta automáticamente después de cargar el FXML
     * Aquí puedes inicializar datos, configurar eventos, etc.
     */
    @FXML
    public void initialize() {
        // Configurar eventos de los botones del sidebar
        btnInicio.setOnAction(event -> handleInicio());
        btnDispositivos.setOnAction(event -> handleDispositivos());
        btnInformes.setOnAction(event -> handleInformes());
        btnConfiguracion.setOnAction(event -> handleConfiguracion());

        // Opcional: Cargar nombre de usuario
        String email = UserSession.getInstance().getUserEmail();
        lblUserName.setText(email + " ▼");
    }

    /**
     * Maneja el click en el botón "Inicio"
     * Ya estamos en inicio, así que solo mostramos un mensaje
     */
    @FXML
    private void handleInicio() {
        System.out.println("Ya estás en Inicio");
        // Opcional: Podrías recargar datos del dashboard
        // loadDashboardData();
    }

    /**
     * Maneja el click en el botón "Dispositivos"
     * Navega a la vista de dispositivos
     */
    @FXML
    private void handleDispositivos() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/devices-view.fxml",
                    "DataSense - Dispositivos", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de dispositivos");
        }
    }

    /**
     * Maneja el click en el botón "Informes"
     * Navega a la vista de informes
     */
    @FXML
    private void handleInformes() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/reports-view.fxml",
                    "DataSense - Informes", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de informes");
        }
    }

    /**
     * Maneja el click en el botón "Configuración"
     * Navega a la vista de configuración
     */
    @FXML
    private void handleConfiguracion() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/config-view.fxml",
                    "DataSense - Configuración", 1000, 700);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de configuración");
        }
    }

    /**
     * Maneja el click en el botón "Cerrar Sesión"
     * Regresa a la pantalla de login
     */
    @FXML
    private void handleLogout() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/login-view.fxml",
                    "DataSense - Iniciar Sesión", 500, 650);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cerrar sesión");
        }
    }

    /**
     * Método auxiliar para mostrar alertas
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Ejemplo: Método para cargar datos del dashboard
     * Aquí podrías hacer llamadas a servicios, bases de datos, APIs, etc.
     */
    private void loadDashboardData() {
        // TODO: Implementar carga de datos reales
        // Ejemplo:
        // - Obtener consumo en tiempo real
        // - Calcular KPIs
        // - Cargar alertas activas
        // - Generar gráficos
    }
}