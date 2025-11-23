package org.example.datasensefx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.collections.FXCollections;
import org.example.datasensefx.services.AuthService;
import org.example.datasensefx.utils.SceneManager;

import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> securityQuestionCombo;

    @FXML
    private TextField securityAnswerField;

    @FXML
    private Button registerButton;

    private AuthService authService;

    // Patrón para validar email
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public RegisterController() {
        this.authService = new AuthService();
    }

    @FXML
    public void initialize() {
        // Cargar preguntas de seguridad en el ComboBox
        securityQuestionCombo.setItems(FXCollections.observableArrayList(
                "¿Cuál es el nombre de tu primera mascota?",
                "¿En qué ciudad naciste?",
                "¿Cuál es tu comida favorita?",
                "¿Nombre de tu mejor amigo de la infancia?",
                "¿Cuál fue tu primer trabajo?",
                "¿Marca de tu primer coche?"
        ));

        // Capturar ENTER para navegar entre campos
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                emailField.requestFocus();
            }
        });

        emailField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirmPasswordField.requestFocus();
            }
        });

        confirmPasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleRegister();
            }
        });
    }

    @FXML
    private void handleRegister() {
        // 1. Obtener valores de los campos
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String securityQuestion = securityQuestionCombo.getValue();
        String securityAnswer = securityAnswerField.getText().trim();

        // 2. VALIDACIONES

        // Validar campos obligatorios
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor completa todos los campos obligatorios (*)");
            return;
        }

        // Validar formato de email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            showAlert(Alert.AlertType.ERROR, "Error", "El formato del correo electrónico no es válido");
            emailField.requestFocus();
            return;
        }

        // Validar longitud de contraseña
        if (password.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Error", "La contraseña debe tener al menos 6 caracteres");
            passwordField.requestFocus();
            return;
        }

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden");
            confirmPasswordField.clear();
            confirmPasswordField.requestFocus();
            return;
        }

        // Validar pregunta y respuesta de seguridad (si se proporcionó una)
        if (securityQuestion != null && !securityQuestion.isEmpty()) {
            if (securityAnswer.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Si seleccionas una pregunta de seguridad, debes proporcionar una respuesta");
                securityAnswerField.requestFocus();
                return;
            }
        }

        // 3. REGISTRAR USUARIO
        try {
            boolean success = authService.register(
                    email,
                    password,
                    name,
                    "USER",  // Rol por defecto
                    securityQuestion,
                    securityAnswer
            );

            if (success) {
                // Registro exitoso
                showAlert(Alert.AlertType.INFORMATION, "Éxito",
                        "Cuenta creada correctamente. Ya puedes iniciar sesión.");

                // Volver al login
                handleBackToLogin();

            } else {
                // El email ya existe
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Ya existe una cuenta con ese correo electrónico");
                emailField.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Ocurrió un error al crear la cuenta. Inténtalo de nuevo.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            SceneManager.switchScene("/org/example/datasensefx/views/login-view.fxml",
                    "DataSense - Iniciar Sesión", 500, 650);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}