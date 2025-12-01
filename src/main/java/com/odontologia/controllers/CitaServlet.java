package com.odontologia.controllers;

import com.odontologia.models.Cita;
import com.odontologia.models.Paciente;
import com.odontologia.models.Doctor;
import com.odontologia.services.CitaService;
import com.odontologia.services.CitaServiceJdbcImplement;
import com.odontologia.repositories.PacienteRepository;
import com.odontologia.repositories.DoctorRepository;
import com.odontologia.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/cita")
public class CitaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = Conexion.getConnection()) {
            PacienteRepository pacienteRepository = new PacienteRepository(conn);
            DoctorRepository doctorRepo = new DoctorRepository(conn);

            List<Paciente> listaPacientes = pacienteRepository.obtenerTodos();
            List<Doctor> listaDoctores = doctorRepo.obtenerTodos();

            request.setAttribute("listaPacientes", listaPacientes);
            request.setAttribute("listaDoctores", listaDoctores);

            request.getRequestDispatcher("/agendar-cita.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try (Connection conn = Conexion.getConnection()) {
            // Obtener parámetros del formulario
            String idPacienteStr = request.getParameter("idPaciente");
            String idDoctorStr = request.getParameter("idDoctor");
            String fechaStr = request.getParameter("fecha");
            String horaStr = request.getParameter("hora");
            String motivo = request.getParameter("motivo");

            // Validar campos obligatorios
            if (idPacienteStr == null || idPacienteStr.isEmpty() ||
                    idDoctorStr == null || idDoctorStr.isEmpty() ||
                    fechaStr == null || fechaStr.isEmpty() ||
                    horaStr == null || horaStr.isEmpty()) {

                response.sendRedirect("cita?error=campos_vacios");
                return;
            }

            // Convertir valores
            int idPaciente = Integer.parseInt(idPacienteStr);
            int idDoctor = Integer.parseInt(idDoctorStr);
            LocalDate fecha = LocalDate.parse(fechaStr);
            LocalTime hora = LocalTime.parse(horaStr);

            // Crear objeto Cita
            Cita cita = new Cita();
            cita.setIdPaciente(idPaciente);
            cita.setIdDoctor(idDoctor);
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setMotivo(motivo != null ? motivo.trim() : "");
            cita.setEstado("Pendiente");

            // Guardar cita
            CitaService citaService = new CitaServiceJdbcImplement(conn);
            boolean guardado = citaService.agendarCita(cita);

            if (guardado) {
                response.sendRedirect("cita?success=created");
            } else {
                response.sendRedirect("cita?error=no_guardado");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("cita?error=datos_invalidos");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("cita?error=true");
        }
    }
}