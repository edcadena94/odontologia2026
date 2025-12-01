package com.odontologia.controllers;

import com.odontologia.models.Medicamentos;
import com.odontologia.repositories.MedicamentosRepository;
import com.odontologia.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * SERVLET DE MEDICAMENTOS
 *
 * Controlador que maneja las peticiones HTTP para el modulo de medicamentos
 * URL: /medicamentos
 *
 * ACCIONES DISPONIBLES (GET):
 * - Sin accion o "listar" -> Muestra lista de medicamentos
 * - "nuevo" -> Muestra formulario para crear medicamento
 * - "editar" -> Muestra formulario para editar medicamento
 * - "eliminar" -> Elimina un medicamento
 *
 * ACCIONES DISPONIBLES (POST):
 * - "crear" -> Guarda nuevo medicamento
 * - "actualizar" -> Actualiza medicamento existente
 */
@WebServlet("/medicamentos")
public class MedicamentosServlet extends HttpServlet {

    // ========== METODO GET ==========

    /**
     * Maneja las peticiones GET (mostrar paginas)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtenemos la accion del parametro URL
        String accion = request.getParameter("accion");

        // Usamos try-with-resources para cerrar la conexion automaticamente
        try (Connection conn = Conexion.getConnection()) {

            // Creamos el repositorio con la conexion
            MedicamentosRepository repository = new MedicamentosRepository(conn);

            // Si no hay accion o es "listar", mostramos la lista
            if (accion == null || accion.isEmpty() || "listar".equals(accion)) {
                // Obtenemos todos los medicamentos
                List<Medicamentos> listaMedicamentos = repository.obtenerTodos();

                // Los enviamos a la vista
                request.setAttribute("listaMedicamentos", listaMedicamentos);

                // Redirigimos a la pagina de lista
                request.getRequestDispatcher("/listar-medicamentos.jsp").forward(request, response);

            } else if ("nuevo".equals(accion)) {
                // Mostramos formulario para nuevo medicamento
                request.getRequestDispatcher("/nuevo-medicamento.jsp").forward(request, response);

            } else if ("editar".equals(accion)) {
                // Obtenemos el ID del medicamento a editar
                long id = Long.parseLong(request.getParameter("id"));

                // Buscamos el medicamento
                Medicamentos medicamento = repository.buscarPorId(id);

                if (medicamento != null) {
                    // Lo enviamos a la vista
                    request.setAttribute("medicamento", medicamento);
                    request.getRequestDispatcher("/editar-medicamento.jsp").forward(request, response);
                } else {
                    // Si no existe, redirigimos con error
                    response.sendRedirect("medicamentos?error=not_found");
                }

            } else if ("eliminar".equals(accion)) {
                // Obtenemos el ID del medicamento a eliminar
                long id = Long.parseLong(request.getParameter("id"));

                // Eliminamos el medicamento
                boolean eliminado = repository.eliminar(id);

                if (eliminado) {
                    response.sendRedirect("medicamentos?success=deleted");
                } else {
                    response.sendRedirect("medicamentos?error=no_eliminado");
                }

            } else {
                // Accion desconocida, redirigimos a lista
                response.sendRedirect("medicamentos");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    // ========== METODO POST ==========

    /**
     * Maneja las peticiones POST (guardar datos)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Obtenemos la accion
        String accion = request.getParameter("accion");
        if (accion == null) accion = "crear";

        try (Connection conn = Conexion.getConnection()) {

            // Creamos el repositorio
            MedicamentosRepository repository = new MedicamentosRepository(conn);

            // Obtenemos los datos del formulario
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String stockStr = request.getParameter("stock");

            // Validamos campos obligatorios
            if (nombre == null || nombre.trim().isEmpty()) {
                response.sendRedirect("medicamentos?accion=nuevo&error=campos_vacios");
                return;
            }

            // Creamos el objeto Medicamentos
            Medicamentos medicamento = new Medicamentos();
            medicamento.setNombre(nombre.trim());
            medicamento.setDescripcion(descripcion != null ? descripcion.trim() : "");

            // Convertimos stock a long (por defecto 0)
            long stock = 0;
            if (stockStr != null && !stockStr.isEmpty()) {
                stock = Long.parseLong(stockStr);
            }
            medicamento.setStock(stock);

            if ("actualizar".equals(accion)) {
                // ACTUALIZAR medicamento existente
                long id = Long.parseLong(request.getParameter("id"));
                medicamento.setIdMedicamento(id);

                boolean actualizado = repository.actualizar(medicamento);

                if (actualizado) {
                    response.sendRedirect("medicamentos?success=updated");
                } else {
                    response.sendRedirect("medicamentos?error=no_actualizado");
                }

            } else {
                // CREAR nuevo medicamento
                boolean guardado = repository.guardar(medicamento);

                if (guardado) {
                    response.sendRedirect("medicamentos?success=created");
                } else {
                    response.sendRedirect("medicamentos?error=no_guardado");
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("medicamentos?error=datos_invalidos");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}