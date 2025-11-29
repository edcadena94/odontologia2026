package com.odontologia.controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "AgendarController", urlPatterns = {"/citas/agendar"})
public class AgendarController extends HttpServlet {

    public static HashMap<String, Map<String, String>> citas = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errores = new HashMap<>();
        Map<String, String> cita = new HashMap<>();

        String nombre = request.getParameter("nombre");
        String cedula = request.getParameter("cedula");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");
        String motivo = request.getParameter("motivo");

        cita.put("nombre", nombre != null ? nombre : "");
        cita.put("cedula", cedula != null ? cedula : "");
        cita.put("correo", correo != null ? correo : "");
        cita.put("telefono", telefono != null ? telefono : "");
        cita.put("fecha", fecha != null ? fecha : "");
        cita.put("hora", hora != null ? hora : "");
        cita.put("motivo", motivo != null ? motivo : "");

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "Ingrese el nombre del paciente.");
        }
        if (cedula == null || !cedula.matches("\\d{1,10}")) {
            errores.put("cedula", "La cedula solo debe contener numeros (max. 10 digitos).");
        }
        if (correo == null || !correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            errores.put("correo", "Ingrese un correo electronico valido.");
        }
        if (telefono == null || !telefono.matches("\\d{10}")) {
            errores.put("telefono", "El numero de telefono debe contener exactamente 10 numeros.");
        }
        if (fecha == null || fecha.isEmpty()) {
            errores.put("fecha", "Seleccione una fecha.");
        }
        if (hora == null || hora.isEmpty()) {
            errores.put("hora", "Seleccione una hora.");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            errores.put("motivo", "Ingrese el motivo de la consulta.");
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("cita", cita);
            request.getRequestDispatcher("/WEB-INF/agendar-cita.jsp").forward(request, response);
            return;
        }

        String id = UUID.randomUUID().toString();
        citas.put(id, cita);

        request.getSession().setAttribute("citaAgendada", id);

        response.sendRedirect(request.getContextPath() + "/index.jsp?exito=1");
    }
}