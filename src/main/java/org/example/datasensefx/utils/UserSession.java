package org.example.datasensefx.utils;

import org.example.datasensefx.models.User;

/**
 * Singleton para gestionar la sesión del usuario actual
 * Mantiene la información del usuario logueado durante toda la sesión
 */
public class UserSession {

    private static UserSession instance;
    private User currentUser;

    /**
     * Constructor privado para patrón Singleton
     */
    private UserSession() {}

    /**
     * Obtiene la instancia única de UserSession
     *
     * @return Instancia de UserSession
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Inicia sesión con un usuario
     *
     * @param user Usuario que inicia sesión
     */
    public void login(User user) {
        this.currentUser = user;
    }

    /**
     * Cierra la sesión actual
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Verifica si hay una sesión activa
     *
     * @return true si hay un usuario logueado, false en caso contrario
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Obtiene el usuario actual
     *
     * @return Usuario actual o null si no hay sesión
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Obtiene el email del usuario actual
     *
     * @return Email del usuario o null si no hay sesión
     */
    public String getUserEmail() {
        return currentUser != null ? currentUser.getEmail() : null;
    }

    /**
     * Obtiene el nombre del usuario actual
     *
     * @return Nombre del usuario o null si no hay sesión
     */
    public String getUserName() {
        return currentUser != null ? currentUser.getName() : null;
    }

    /**
     * Obtiene el rol del usuario actual
     *
     * @return Rol del usuario (ADMIN/USER) o null si no hay sesión
     */
    public String getUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    /**
     * Obtiene el ID del usuario actual
     *
     * @return ID del usuario o 0 si no hay sesión
     */
    public int getUserId() {
        return currentUser != null ? currentUser.getId() : 0;
    }

    /**
     * Verifica si el usuario actual es administrador
     *
     * @return true si el usuario es ADMIN, false en caso contrario
     */
    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }

    /**
     * Verifica si el usuario actual es un usuario normal
     *
     * @return true si el usuario es USER, false en caso contrario
     */
    public boolean isUser() {
        return currentUser != null && "USER".equalsIgnoreCase(currentUser.getRole());
    }

    /**
     * Limpia la sesión (alias de logout para compatibilidad)
     */
    public void clearSession() {
        logout();
    }

    @Override
    public String toString() {
        if (currentUser != null) {
            return "UserSession{" +
                    "user=" + currentUser.getName() +
                    ", email=" + currentUser.getEmail() +
                    ", role=" + currentUser.getRole() +
                    '}';
        }
        return "UserSession{no active session}";
    }
}