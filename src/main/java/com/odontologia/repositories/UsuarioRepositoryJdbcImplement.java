package com.odontologia.repositories;

import com.odontologia.models.Usuario;
import com.odontologia.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * CLASE USUARIO REPOSITORY JDBC IMPLEMENT
 * 
 * ACTUALIZACIÓN: Ahora lee los nuevos campos de la tabla usuario:
 * - cedula_paciente (para usuarios tipo PACIENTE)
 * - id_odontologo (para usuarios tipo DOCTOR)
 * - estado (ACTIVO/INACTIVO)
 * - ultimo_acceso
 * - fecha_creacion
 */
public class UsuarioRepositoryJdbcImplement implements UsuarioRepository {

    /**
     * Busca un usuario en la base de datos por su username
     * 
     * CAMBIO: Ahora también trae los nuevos campos de la BD
     */
    @Override
    public Usuario encontrarPorUsername(String username) {
        Usuario usuario = null;

        // SQL actualizado con los nuevos campos
        String sql = "SELECT id_usuario, username, password_hash, rol, " +
                     "cedula_paciente, id_odontologo, estado, " +
                     "ultimo_acceso, fecha_creacion " +
                     "FROM usuario WHERE username = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                
                // Campos básicos (igual que antes)
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPasswordHash(rs.getString("password_hash"));
                usuario.setRol(rs.getString("rol"));
                
                // NUEVOS CAMPOS
                usuario.setCedulaPaciente(rs.getString("cedula_paciente"));
                
                // id_odontologo puede ser NULL
                int idOdontologo = rs.getInt("id_odontologo");
                if (!rs.wasNull()) {
                    usuario.setIdOdontologo(idOdontologo);
                }
                
                usuario.setEstado(rs.getString("estado"));
                
                // ultimo_acceso puede ser NULL
                Timestamp ultimoAcceso = rs.getTimestamp("ultimo_acceso");
                if (ultimoAcceso != null) {
                    usuario.setUltimoAcceso(ultimoAcceso.toLocalDateTime());
                }
                
                // fecha_creacion puede ser NULL
                Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
                if (fechaCreacion != null) {
                    usuario.setFechaCreacion(fechaCreacion.toLocalDateTime());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * MÉTODO ADICIONAL: Busca usuario por ID
     * Útil para obtener datos completos después del login
     */
    public Usuario buscarPorId(Integer idUsuario) {
        Usuario usuario = null;

        String sql = "SELECT id_usuario, username, password_hash, rol, " +
                     "cedula_paciente, id_odontologo, estado, " +
                     "ultimo_acceso, fecha_creacion " +
                     "FROM usuario WHERE id_usuario = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * MÉTODO PRIVADO: Mapea ResultSet a Usuario
     * Para evitar duplicar código
     */
    private Usuario mapearUsuario(ResultSet rs) throws Exception {
        Usuario usuario = new Usuario();
        
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPasswordHash(rs.getString("password_hash"));
        usuario.setRol(rs.getString("rol"));
        usuario.setCedulaPaciente(rs.getString("cedula_paciente"));
        
        int idOdontologo = rs.getInt("id_odontologo");
        if (!rs.wasNull()) {
            usuario.setIdOdontologo(idOdontologo);
        }
        
        usuario.setEstado(rs.getString("estado"));
        
        Timestamp ultimoAcceso = rs.getTimestamp("ultimo_acceso");
        if (ultimoAcceso != null) {
            usuario.setUltimoAcceso(ultimoAcceso.toLocalDateTime());
        }
        
        Timestamp fechaCreacion = rs.getTimestamp("fecha_creacion");
        if (fechaCreacion != null) {
            usuario.setFechaCreacion(fechaCreacion.toLocalDateTime());
        }
        
        return usuario;
    }
}
