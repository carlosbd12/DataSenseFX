package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.example.datasensefx.models.User;
import org.example.datasensefx.services.AuthService;
import org.example.datasensefx.utils.SceneManager;
import org.example.datasensefx.utils.UserSession;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberMeCheckBox; // Falta por implementar persistencia de sesión

    @FXML
    public void initialize() {
        // ENTER en email → mover foco a password
        emailField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        // ENTER en password → hacer login
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });

    }

    @FXML
    private void handleLogin() {

        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Por favor completa todos los campos");
            return;
        }

        /* Conexion con la BBDD
        if (authenticate(email, password)) {
            try {
                UserSession.getInstance().setUserEmail(email);
                SceneManager.switchScene("/org/example/datasensefx/views/dashboard-view.fxml",
                        "DataSense - Dashboard", 1000, 700);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo cargar el dashboard");
            }
        } else {
            showAlert("Error", "Credenciales incorrectas");
        }*/
        AuthService authService = new AuthService();
        User user = authService.authenticate(email, password);

        if (user != null) {
            // Login exitoso
            UserSession.getInstance().login(user);
            try {
                SceneManager.switchScene("dashboard-view.fxml", "DataSense FX - Dashboard");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Credenciales incorrectas
            showAlert("Error", "Email o contraseña incorrectos");
        }
    }

    //Autenticación temporal
    /*private boolean authenticate(String email, String password) {
        // Conexion con la BBDD a desarrollar
        return email.equals("admin@admin.com") && password.equals("admin");
    }*/


    @FXML
    private void handleForgotPassword() {
        showAlert("Información", "Función de recuperación de contraseña en desarrollo");
    }

    @FXML
    private void handleCreateAccount() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/register-view.fxml",
                    "DataSense - Crear Cuenta", 500, 750);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la pantalla de registro");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}