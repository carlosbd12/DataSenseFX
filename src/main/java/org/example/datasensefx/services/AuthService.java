package org.example.datasensefx.services;

import org.example.datasensefx.dao.UserDAO;
import org.example.datasensefx.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Registrar nuevo usuario
     *
     * @param email Email del usuario
     * @param password Contraseña en texto plano
     * @param name Nombre completo
     * @param role Rol (ADMIN o USER)
     * @param securityQuestion Pregunta de seguridad (opcional)
     * @param securityAnswer Respuesta de seguridad (opcional)
     * @return true si se creó correctamente, false si el email ya existe
     */
    public boolean register(String email, String password, String name, String role,
                            String securityQuestion, String securityAnswer) {
        try {
            // 1. Verificar si el email ya existe
            if (userDAO.emailExists(email)) {
                return false;
            }

            // 2. Crear objeto User
            User user = new User();
            user.setEmail(email);
            user.setPassword(hashPassword(password));  // Hash de la contraseña
            user.setName(name);
            user.setRole(role != null ? role : "USER");
            user.setSecurityQuestion(securityQuestion);
            user.setSecurityAnswer(securityAnswer);

            // 3. Insertar en la base de datos
            return userDAO.create(user);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sobrecarga sin pregunta de seguridad
     */
    public boolean register(String email, String password, String name, String role) {
        return register(email, password, name, role, null, null);
    }

    /**
     * Autenticar usuario
     *
     * @param email Email del usuario
     * @param password Contraseña en texto plano
     * @return User si las credenciales son correctas, null si son incorrectas
     */
    public User authenticate(String email, String password) {
        try {
            System.out.println("🔍 [DEBUG] Intentando autenticar: " + email);
            User user = userDAO.findByEmail(email);

            if (user == null) {
                System.out.println("❌ [DEBUG] Usuario no encontrado en la base de datos");
                return null;
            }

            System.out.println("✅ [DEBUG] Usuario encontrado: " + user.getName());
            System.out.println("🔑 [DEBUG] Password ingresado: " + password);
            System.out.println("🔑 [DEBUG] Password en BD (primeros 20 chars): " + user.getPassword().substring(0, Math.min(20, user.getPassword().length())));
            System.out.println("🔑 [DEBUG] Longitud password en BD: " + user.getPassword().length());

            boolean passwordMatch = verifyPassword(password, user.getPassword());
            System.out.println("🔐 [DEBUG] Verificación de contraseña: " + (passwordMatch ? "✅ CORRECTA" : "❌ INCORRECTA"));

            if (passwordMatch) {
                return user;
            }
        } catch (SQLException e) {
            System.out.println("💥 [DEBUG] Error SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cambiar contraseña
     *
     * @param userId ID del usuario
     * @param oldPassword Contraseña actual
     * @param newPassword Nueva contraseña
     * @return true si se cambió correctamente, false si la contraseña actual es incorrecta
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        try {
            User user = userDAO.findById(userId);

            if (user != null && verifyPassword(oldPassword, user.getPassword())) {
                String hashedNewPassword = hashPassword(newPassword);
                return userDAO.updatePassword(userId, hashedNewPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hash de contraseña usando BCrypt
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * Verificar contraseña
     * Compara la contraseña en texto plano con el hash almacenado
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            System.out.println("🔐 [DEBUG] Verificando con BCrypt...");
            boolean result = BCrypt.checkpw(plainPassword, hashedPassword);
            System.out.println("🔐 [DEBUG] Resultado BCrypt: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("⚠️ [DEBUG] BCrypt falló, intentando comparación directa...");
            System.out.println("⚠️ [DEBUG] Excepción: " + e.getMessage());
            // Si la contraseña no está hasheada (datos de prueba sin hash)
            boolean result = plainPassword.equals(hashedPassword);
            System.out.println("🔐 [DEBUG] Resultado comparación directa: " + result);
            return result;
        }
    }
}