package com.odontologia.controllers;

import com.odontologia.models.Factura;
import com.odontologia.services.FacturaService;
import com.odontologia.services.FacturaServiceJdbcImplement;
import com.odontologia.util.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/factura")
public class FacturaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try (Connection conn = Conexion.getConnection()) {
            FacturaService facturaService = new FacturaServiceJdbcImplement(conn);

            if ("listar".equals(accion) || accion == null) {
                List<Factura> listaFacturas = facturaService.listarTodas();
                request.setAttribute("listaFacturas", listaFacturas);
                request.getRequestDispatcher("/listar-facturas.jsp").forward(request, response);

            } else if ("nuevo".equals(accion)) {
                request.getRequestDispatcher("/nueva-factura.jsp").forward(request, response);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Factura factura = facturaService.buscarPorId(id);
                if (factura != null) {
                    request.setAttribute("factura", factura);
                    request.getRequestDispatcher("/editar-factura.jsp").forward(request, response);
                } else {
                    response.sendRedirect("factura?error=not_found");
                }

            } else if ("ver".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Factura factura = facturaService.buscarPorId(id);
                if (factura != null) {
                    request.setAttribute("factura", factura);
                    request.getRequestDispatcher("/ver-factura.jsp").forward(request, response);
                } else {
                    response.sendRedirect("factura?error=not_found");
                }
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
            FacturaService facturaService = new FacturaServiceJdbcImplement(conn);

            if ("crear".equals(accion)) {
                String numeroFactura = request.getParameter("numeroFactura");
                LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
                Double montoTotal = Double.parseDouble(request.getParameter("montoTotal"));
                Integer idPaciente = Integer.parseInt(request.getParameter("idPaciente"));

                if (numeroFactura == null || numeroFactura.trim().isEmpty() ||
                        fecha == null || montoTotal == null || idPaciente == null) {
                    response.sendRedirect("factura?accion=nuevo&error=campos_vacios");
                    return;
                }

                Factura factura = new Factura();
                factura.setNumeroFactura(numeroFactura.trim());
                factura.setFecha(fecha);
                factura.setMontoTotal(montoTotal);
                factura.setIdPaciente(idPaciente);

                boolean guardado = facturaService.guardar(factura);

                if (guardado) {
                    response.sendRedirect("factura?success=created");
                } else {
                    response.sendRedirect("factura?accion=nuevo&error=no_guardado");
                }

            } else if ("actualizar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String numeroFactura = request.getParameter("numeroFactura");
                LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
                Double montoTotal = Double.parseDouble(request.getParameter("montoTotal"));
                Integer idPaciente = Integer.parseInt(request.getParameter("idPaciente"));

                if (numeroFactura == null || numeroFactura.trim().isEmpty() ||
                        fecha == null || montoTotal == null || idPaciente == null) {
                    response.sendRedirect("factura?accion=editar&id=" + id + "&error=campos_vacios");
                    return;
                }

                Factura factura = new Factura();
                factura.setIdFactura(id);
                factura.setNumeroFactura(numeroFactura.trim());
                factura.setFecha(fecha);
                factura.setMontoTotal(montoTotal);
                factura.setIdPaciente(idPaciente);

                boolean actualizado = facturaService.actualizar(factura);

                if (actualizado) {
                    response.sendRedirect("factura?success=updated");
                } else {
                    response.sendRedirect("factura?accion=editar&id=" + id + "&error=no_actualizado");
                }

            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean eliminado = facturaService.eliminar(id);

                if (eliminado) {
                    response.sendRedirect("factura?success=deleted");
                } else {
                    response.sendRedirect("factura?error=no_eliminado");
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("factura?error=datos_invalidos");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("factura?error=error_interno");
        }
    }
}