package com.odontologia.controllers;

import com.odontologia.models.Usuario;
import com.odontologia.util.Conexion;
import com.odontologia.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SERVLET DE LOGIN
 * Maneja la autenticación de usuarios y redirección según rol
 * 
 * URL: /login
 * Método: POST
 * 
 * ROLES Y REDIRECCIÓN:
 * - ADMIN → panelAdmin.jsp
 * - DOCTOR → panelDoctor.jsp
 * - SECRETARIA → panelSecretaria.jsp
 * - PACIENTE → panelPaciente.jsp
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // PASO 1: Obtener datos del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validar que no vengan vacíos
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor complete todos los campos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try (Connection conn = Conexion.getConnection()) {
            
            // PASO 2: Buscar usuario en la base de datos
            String sql = "SELECT id_usuario, username, password_hash, rol, " +
                         "cedula_paciente, id_odontologo, estado " +
                         "FROM usuario WHERE username = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Usuario existe
                String hashedPassword = rs.getString("password_hash");
                String rol = rs.getString("rol");
                String estado = rs.getString("estado");
                
                // PASO 3: Verificar si el usuario está activo
                if (!"ACTIVO".equals(estado)) {
                    request.setAttribute("error", "Usuario inactivo. Contacte al administrador");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
                
                // PASO 4: Verificar contraseña con BCrypt
                if (PasswordUtil.checkPassword(password, hashedPassword)) {
                    
                    // ✅ Login exitoso
                    
                    // PASO 5: Crear objeto Usuario completo
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setRol(rol);
                    usuario.setCedulaPaciente(rs.getString("cedula_paciente"));
                    
                    // getInt retorna 0 si es NULL, entonces verificamos wasNull()
                    int idOdontologo = rs.getInt("id_odontologo");
                    if (!rs.wasNull()) {
                        usuario.setIdOdontologo(idOdontologo);
                    }
                    
                    // PASO 6: Guardar en sesión
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("username", username);
                    session.setAttribute("rol", rol);
                    
                    // Si es PACIENTE, guardar también la cédula
                    if ("PACIENTE".equals(rol)) {
                        session.setAttribute("cedulaPaciente", usuario.getCedulaPaciente());
                    }
                    
                    // Si es DOCTOR, guardar también el idOdontologo
                    if ("DOCTOR".equals(rol)) {
                        session.setAttribute("idOdontologo", usuario.getIdOdontologo());
                    }
                    
                    // PASO 7: Actualizar último acceso
                    actualizarUltimoAcceso(conn, usuario.getIdUsuario());
                    
                    // PASO 8: Redirigir según el rol
                    String paginaInicio = usuario.getPaginaInicio();
                    response.sendRedirect(paginaInicio);
                    return;
                    
                } else {
                    // ❌ Contraseña incorrecta
                    request.setAttribute("error", "Contraseña incorrecta");
                }
                
            } else {
                // ❌ Usuario no encontrado
                // Simulación de hash para prevenir timing attacks
                PasswordUtil.checkPassword("dummy", BCrypt.gensalt());
                request.setAttribute("error", "Usuario no encontrado");
            }

            // Si llegamos aquí, el login falló
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al conectar con la base de datos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Actualiza la fecha del último acceso del usuario
     */
    private void actualizarUltimoAcceso(Connection conn, Integer idUsuario) {
        try {
            String sql = "UPDATE usuario SET ultimo_acceso = NOW() WHERE id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // No lanzar excepción, solo logear
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si acceden con GET, mostrar el formulario de login
        response.sendRedirect("login.jsp");
    }
}
