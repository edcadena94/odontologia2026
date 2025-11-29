package com.odontologia.controllers;

import com.odontologia.models.Doctor;
import com.odontologia.services.DoctorService;
import com.odontologia.services.DoctorServiceJdbcImplement;
import com.odontologia.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/doctor")
public class DoctorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            DoctorService doctorService = new DoctorServiceJdbcImplement(conn);

            if ("listar".equals(accion) || accion == null) {
                List<Doctor> listaDoctores = doctorService.listarTodos();
                request.setAttribute("listaDoctores", listaDoctores);
                request.getRequestDispatcher("/listar-doctores.jsp").forward(request, response);

            } else if ("nuevo".equals(accion)) {
                request.getRequestDispatcher("/nuevo-doctor.jsp").forward(request, response);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Doctor doctor = doctorService.buscarPorId(id);
                if (doctor != null) {
                    request.setAttribute("doctor", doctor);
                    request.getRequestDispatcher("/editar-doctor.jsp").forward(request, response);
                } else {
                    response.sendRedirect("doctor?error=not_found");
                }

            } else if ("ver".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Doctor doctor = doctorService.buscarPorId(id);
                if (doctor != null) {
                    request.setAttribute("doctor", doctor);
                    request.getRequestDispatcher("/ver-doctor.jsp").forward(request, response);
                } else {
                    response.sendRedirect("doctor?error=not_found");
                }

            } else if ("buscar".equals(accion)) {
                String nombre = request.getParameter("nombre");
                List<Doctor> listaDoctores = doctorService.buscarPorNombre(nombre);
                request.setAttribute("listaDoctores", listaDoctores);
                request.setAttribute("termino", nombre);
                request.getRequestDispatcher("/listar-doctores.jsp").forward(request, response);

            } else if ("por-especialidad".equals(accion)) {
                String especialidad = request.getParameter("especialidad");
                List<Doctor> listaDoctores = doctorService.buscarPorEspecialidad(especialidad);
                request.setAttribute("listaDoctores", listaDoctores);
                request.setAttribute("especialidadFiltro", especialidad);
                request.getRequestDispatcher("/listar-doctores.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String accion = request.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            DoctorService doctorService = new DoctorServiceJdbcImplement(conn);

            if ("crear".equals(accion)) {
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String especialidad = request.getParameter("especialidad");
                String telefono = request.getParameter("telefono");
                String email = request.getParameter("email");

                if (nombre == null || nombre.trim().isEmpty() ||
                        apellido == null || apellido.trim().isEmpty() ||
                        especialidad == null || especialidad.trim().isEmpty()) {
                    response.sendRedirect("doctor?accion=nuevo&error=campos_vacios");
                    return;
                }

                if (email != null && !email.trim().isEmpty() && doctorService.existeEmail(email)) {
                    response.sendRedirect("doctor?accion=nuevo&error=email_existe");
                    return;
                }

                Doctor doctor = new Doctor();
                doctor.setNombre(nombre.trim());
                doctor.setApellido(apellido.trim());
                doctor.setEspecialidad(especialidad.trim());
                doctor.setTelefono(telefono != null ? telefono.trim() : "");
                doctor.setEmail(email != null ? email.trim() : "");

                boolean guardado = doctorService.guardar(doctor);

                if (guardado) {
                    response.sendRedirect("doctor?success=created");
                } else {
                    response.sendRedirect("doctor?accion=nuevo&error=no_guardado");
                }

            } else if ("actualizar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String especialidad = request.getParameter("especialidad");
                String telefono = request.getParameter("telefono");
                String email = request.getParameter("email");

                if (nombre == null || nombre.trim().isEmpty() ||
                        apellido == null || apellido.trim().isEmpty() ||
                        especialidad == null || especialidad.trim().isEmpty()) {
                    response.sendRedirect("doctor?accion=editar&id=" + id + "&error=campos_vacios");
                    return;
                }

                Doctor doctor = new Doctor();
                doctor.setIdDoctor(id);
                doctor.setNombre(nombre.trim());
                doctor.setApellido(apellido.trim());
                doctor.setEspecialidad(especialidad.trim());
                doctor.setTelefono(telefono != null ? telefono.trim() : "");
                doctor.setEmail(email != null ? email.trim() : "");

                boolean actualizado = doctorService.actualizar(doctor);

                if (actualizado) {
                    response.sendRedirect("doctor?success=updated");
                } else {
                    response.sendRedirect("doctor?accion=editar&id=" + id + "&error=no_actualizado");
                }

            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean eliminado = doctorService.eliminar(id);

                if (eliminado) {
                    response.sendRedirect("doctor?success=deleted");
                } else {
                    response.sendRedirect("doctor?error=no_eliminado");
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("doctor?error=datos_invalidos");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("doctor?error=error_interno");
        }
    }
}