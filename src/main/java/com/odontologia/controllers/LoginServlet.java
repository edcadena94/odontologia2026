package com.odontologia.controllers;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT password_hash, rol FROM Usuarios WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password_hash");
                String role = rs.getString("rol");

                if (PasswordUtil.checkPassword(password, hashedPassword)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", username);
                    session.setAttribute("rol", role);

                    String redirectPage = "recepcionista".equals(role) ? "cabecero-recep.jsp" : "panelDoctor.jsp";
                    response.sendRedirect(redirectPage);
                    return;
                } else {
                    request.setAttribute("error", "Contraseña incorrecta");
                }
            } else {
                PasswordUtil.checkPassword("dummy", BCrypt.gensalt());
                request.setAttribute("error", "Usuario no encontrado");
            }

            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al conectar a la base de datos", e);
        }
    }
}