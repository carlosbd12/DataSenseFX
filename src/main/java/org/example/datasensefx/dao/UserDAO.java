package org.example.datasensefx.dao;

import org.example.datasensefx.models.User;
import org.example.datasensefx.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la tabla users
 * Maneja todas las operaciones CRUD de usuarios
 */
public class UserDAO {

    /**
     * Buscar usuario por email
     *
     * @param email Email del usuario a buscar - NOTA: También busca por el nombre de usuario
     * @return User si existe, null si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? OR name = ?";

        System.out.println("🔍 [DAO DEBUG] Buscando usuario con email: " + email);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("✅ [DAO DEBUG] Conexión obtenida: " + (conn != null && !conn.isClosed()));
            System.out.println("📝 [DAO DEBUG] SQL: " + sql);

            stmt.setString(1, email);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ [DAO DEBUG] Usuario encontrado en la BD");
                User user = mapResultSetToUser(rs);
                System.out.println("✅ [DAO DEBUG] Usuario mapeado: " + user.getEmail());
                return user;
            } else {
                System.out.println("❌ [DAO DEBUG] No se encontró ningún usuario con ese email");
            }
        } catch (SQLException e) {
            System.out.println("💥 [DAO DEBUG] Error SQL: " + e.getMessage());
            throw e;
        }
        return null;
    }

    /**
     * Buscar usuario por ID
     *
     * @param id ID del usuario
     * @return User si existe, null si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
        return null;
    }

    /**
     * Crear nuevo usuario
     *
     * @param user Objeto User con los datos del nuevo usuario
     * @return true si se creó correctamente, false si hubo error
     * @throws SQLException Si hay error en la inserción
     */
    public boolean create(User user) throws SQLException {
    String sql = "INSERT INTO users (email, password, name, role, security_question, security_answer) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        // Establecer parámetros
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());  // Ya viene hasheada desde AuthService
        stmt.setString(3, user.getName());
        stmt.setString(4, user.getRole());
        stmt.setString(5, user.getSecurityQuestion());
        stmt.setString(6, user.getSecurityAnswer());

        // Ejecutar INSERT
        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            // Obtener el ID generado automáticamente
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            return true;
        }
    }
    return false;
}

    /**
     * Actualizar datos de usuario (excepto contraseña)
     *
     * @param user Objeto User con los datos actualizados
     * @return true si se actualizó correctamente, false si hubo error
     * @throws SQLException Si hay error en la actualización
     */// Falta por desarrollar
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, role = ?, security_question = ?, security_answer = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getSecurityQuestion());
            stmt.setString(4, user.getSecurityAnswer());
            stmt.setInt(5, user.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Actualizar contraseña de usuario
     *
     * @param userId ID del usuario
     * @param newPassword Nueva contraseña (ya hasheada)
     * @return true si se actualizó correctamente, false si hubo error
     * @throws SQLException Si hay error en la actualización
     */
    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Eliminar usuario por ID
     *
     * @param userId ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     * @throws SQLException Si hay error en la eliminación
     */// Falta por desarrollar
    public boolean delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Listar todos los usuarios
     *
     * @return Lista de todos los usuarios
     * @throws SQLException Si hay error en la consulta
     */ // Falta por desarrollar
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    /**
     * Verificar si existe un email
     *
     * @param email Email a verificar
     * @return true si existe, false si no existe
     * @throws SQLException Si hay error en la consulta
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Contar total de usuarios
     *
     * @return Número total de usuarios
     * @throws SQLException Si hay error en la consulta
     */// Falta por desarrollar
    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Buscar usuarios por rol
     *
     * @param role Rol a buscar (ADMIN o USER)
     * @return Lista de usuarios con ese rol
     * @throws SQLException Si hay error en la consulta
     */ // Falta por desarrollar
    public List<User> findByRole(String role) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY name";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    /**
     * Mapear ResultSet a objeto User
     * Método privado auxiliar para convertir filas de la BD a objetos User
     *
     * @param rs ResultSet con los datos del usuario
     * @return Objeto User con los datos mapeados
     * @throws SQLException Si hay error al leer el ResultSet
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        user.setSecurityQuestion(rs.getString("security_question"));
        user.setSecurityAnswer(rs.getString("security_answer"));

        // Mapear timestamps si existen
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return user;
    }
}