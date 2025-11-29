package com.odontologia.controllers;

import com.odontologia.models.Medicamentos;
import com.odontologia.services.MedicamentosService;
import com.odontologia.services.MedicamentosServiceJdbcImplement;
import com.odontologia.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/medicamentos")
public class MedicamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            MedicamentosService medicamentosService = new MedicamentosServiceJdbcImplement(conn);

            if (accion == null || accion.isEmpty() || "listar".equals(accion)) {
                List<Medicamentos> listaMedicamentos = medicamentosService.listarTodos();
                request.setAttribute("listaMedicamentos", listaMedicamentos);
                request.getRequestDispatcher("/listar-medicamentos.jsp").forward(request, response);

            } else if ("nuevo".equals(accion)) {
                request.getRequestDispatcher("/nuevo-medicamento.jsp").forward(request, response);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Medicamentos medicamento = medicamentosService.buscarPorId(id);
                if (medicamento != null) {
                    request.setAttribute("medicamento", medicamento);
                    request.getRequestDispatcher("/editar-medicamento.jsp").forward(request, response);
                } else {
                    response.sendRedirect("medicamentos?error=not_found");
                }

            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                medicamentosService.eliminar(id);
                response.sendRedirect("medicamentos?success=deleted");

            } else {
                response.sendRedirect("medicamentos");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "crear";

        try (Connection conn = Conexion.getConnection()) {
            MedicamentosService medicamentosService = new MedicamentosServiceJdbcImplement(conn);

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String stockStr = request.getParameter("stock");

            Medicamentos medicamento = new Medicamentos();
            medicamento.setNombre(nombre);
            medicamento.setDescripcion(descripcion);

            int stock = 0;
            if (stockStr != null && !stockStr.isEmpty()) {
                stock = Integer.parseInt(stockStr);
            }
            medicamento.setStock(stock);

            if ("actualizar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                medicamento.setIdMedicamento(id);
                boolean actualizado = medicamentosService.actualizar(medicamento);
                if (actualizado) {
                    response.sendRedirect("medicamentos?success=updated");
                } else {
                    response.sendRedirect("medicamentos?error=no_actualizado");
                }
            } else {
                boolean guardado = medicamentosService.guardar(medicamento);
                if (guardado) {
                    response.sendRedirect("medicamentos?success=created");
                } else {
                    response.sendRedirect("medicamentos?error=no_guardado");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}