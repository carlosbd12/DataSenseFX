package org.example.datasensefx.models;

import java.time.LocalDateTime;

/**
 * Modelo de datos para la tabla users
 * Representa un usuario del sistema
 */
public class User {

    // Atributos
    private int id;
    private String email;
    private String password;
    private String name;
    private String role;
    private String securityQuestion;
    private String securityAnswer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructores

    /**
     * Constructor vacío
     */
    public User() {
    }

    /**
     * Constructor con parámetros principales
     */
    public User(int id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    /**
     * Constructor completo
     */
    public User(int id, String email, String password, String name, String role,
                String securityQuestion, String securityAnswer) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Métodos auxiliares

    /**
     * Verifica si el usuario es administrador
     */
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    /**
     * Verifica si el usuario es un usuario normal
     */
    public boolean isUser() {
        return "USER".equalsIgnoreCase(this.role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
