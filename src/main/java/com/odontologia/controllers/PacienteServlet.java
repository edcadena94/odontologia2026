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
import java.time.LocalDate;
import java.util.List;

/**
 * SERVLET PACIENTE
 * 
 * CAMBIOS IMPORTANTES:
 * - Ahora usa CÉDULA (String) en lugar de ID (int)
 * - Usa LocalDate en lugar de Date
 * - Usa GENERO (String) en lugar de SEXO (char)
 */
@WebServlet("/pacientes")
public class PacienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            PacienteServiceJdbcImplement pacienteService = new PacienteServiceJdbcImplement(conn);

            if (accion == null || accion.isEmpty() || "listar".equals(accion)) {
                // Listar todos los pacientes
                List<Paciente> pacientes = pacienteService.listarTodos();
                req.setAttribute("pacientes", pacientes);
                req.getRequestDispatcher("/verPacientes.jsp").forward(req, resp);

            } else if ("nuevo".equals(accion)) {
                // Mostrar formulario de nuevo paciente
                req.getRequestDispatcher("/registrarPaciente.jsp").forward(req, resp);

            } else if ("editar".equals(accion)) {
                // Editar paciente por CÉDULA (no por ID)
                String cedula = req.getParameter("cedula");
                if (cedula == null || cedula.isEmpty()) {
                    resp.sendRedirect("pacientes?error=cedula_requerida");
                    return;
                }
                
                Paciente paciente = pacienteService.buscarPorCedula(cedula);
                if (paciente != null) {
                    req.setAttribute("paciente", paciente);
                    req.getRequestDispatcher("/editarPaciente.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("pacientes?error=not_found");
                }

            } else if ("eliminar".equals(accion)) {
                // Eliminar paciente por CÉDULA
                String cedula = req.getParameter("cedula");
                if (cedula != null && !cedula.isEmpty()) {
                    pacienteService.eliminarPorCedula(cedula);
                    resp.sendRedirect("pacientes?success=deleted");
                } else {
                    resp.sendRedirect("pacientes?error=cedula_requerida");
                }

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
            PacienteServiceJdbcImplement pacienteService = new PacienteServiceJdbcImplement(conn);

            // Leer parámetros del formulario
            String cedula = req.getParameter("cedula");
            String nombre = req.getParameter("nombre");
            String apellido = req.getParameter("apellido");
            String email = req.getParameter("email");
            String telefono = req.getParameter("telefono");
            String direccion = req.getParameter("direccion");
            String fechaNacStr = req.getParameter("fechaNacimiento"); // O "fecha_nacimiento"
            String genero = req.getParameter("genero");
            String alergias = req.getParameter("alergias");
            String antecedentesMedicos = req.getParameter("antecedentes_medicos");

            // Crear objeto Paciente
            Paciente paciente = new Paciente();
            paciente.setCedula(cedula);
            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setEmail(email);
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setGenero(genero); // Ahora es String, no char
            paciente.setAlergias(alergias);
            paciente.setAntecedentesMedicos(antecedentesMedicos);

            // Convertir fecha de String a LocalDate
            try {
                if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                    LocalDate fechaNacimiento = LocalDate.parse(fechaNacStr);
                    paciente.setFechaNacimiento(fechaNacimiento);
                }
            } catch (Exception e) {
                req.setAttribute("error", "Fecha de nacimiento inválida");
                req.getRequestDispatcher("/registrarPaciente.jsp").forward(req, resp);
                return;
            }

            // Ejecutar acción
            if ("actualizar".equals(accion)) {
                // ACTUALIZAR paciente existente
                boolean actualizado = pacienteService.actualizar(paciente);
                if (actualizado) {
                    resp.sendRedirect("pacientes?success=updated");
                } else {
                    resp.sendRedirect("pacientes?error=no_actualizado");
                }
                
            } else {
                // CREAR nuevo paciente
                // Validar que la cédula no exista
                if (pacienteService.existeCedula(cedula)) {
                    req.setAttribute("error", "La cédula ya está registrada");
                    req.getRequestDispatcher("/registrarPaciente.jsp").forward(req, resp);
                    return;
                }
                
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
