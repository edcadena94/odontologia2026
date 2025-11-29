package com.odontologia.controllers;

import com.odontologia.models.Paciente;
import com.odontologia.services.PacienteService;
import com.odontologia.services.PacienteServiceJdbcImplement;
import com.odontologia.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            PacienteService pacienteService = new PacienteServiceJdbcImplement(conn);

            if (accion == null || accion.isEmpty() || "listar".equals(accion)) {
                List<Paciente> pacientes = pacienteService.listarTodos();
                req.setAttribute("pacientes", pacientes);
                req.getRequestDispatcher("/verPacientes.jsp").forward(req, resp);

            } else if ("nuevo".equals(accion)) {
                req.getRequestDispatcher("/registrarPaciente.jsp").forward(req, resp);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Paciente paciente = pacienteService.buscarPorId(id);
                if (paciente != null) {
                    req.setAttribute("paciente", paciente);
                    req.getRequestDispatcher("/editarPaciente.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("pacientes?error=not_found");
                }

            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                pacienteService.eliminar(id);
                resp.sendRedirect("pacientes?success=deleted");

            } else {
                resp.sendRedirect("pacientes");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) accion = "crear";

        try (Connection conn = Conexion.getConnection()) {
            PacienteService pacienteService = new PacienteServiceJdbcImplement(conn);

            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String email = req.getParameter("email");
            String telefono = req.getParameter("telefono");
            String direccion = req.getParameter("direccion");
            String fechaNacStr = req.getParameter("fecha_nacimiento");
            String sexo = req.getParameter("sexo");

            Paciente paciente = new Paciente();
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setEmail(email);
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setSexo(sexo != null && !sexo.isEmpty() ? sexo.charAt(0) : ' ');

            try {
                if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                    Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacStr);
                    paciente.setFechaNacimiento(fechaNacimiento);
                }
            } catch (Exception e) {
                req.setAttribute("error", "Fecha de nacimiento inválida");
                req.getRequestDispatcher("/registrarPaciente.jsp").forward(req, resp);
                return;
            }

            if ("actualizar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                paciente.setIdPaciente(id);
                boolean actualizado = pacienteService.actualizar(paciente);
                if (actualizado) {
                    resp.sendRedirect("pacientes?success=updated");
                } else {
                    resp.sendRedirect("pacientes?error=no_actualizado");
                }
            } else {
                boolean guardado = pacienteService.guardar(paciente);
                if (guardado) {
                    resp.sendRedirect("pacientes?success=created");
                } else {
                    resp.sendRedirect("pacientes?error=no_guardado");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}