<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.odontologia.models.Factura" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Facturas - Esthetyc Dental Studio</title>

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    :root {
      --primary: #6B46C1;
      --primary-light: #8B5CF6;
      --primary-dark: #553C9A;
      --white: #ffffff;
      --gray-light: #f7f7f7;
      --gray: #888888;
      --text: #333333;
    }

    body {
      font-family: 'Poppins', sans-serif;
      background: var(--gray-light);
      min-height: 100vh;
    }

    /* ========== SIDEBAR ========== */
    .sidebar {
      position: fixed;
      left: 0;
      top: 0;
      width: 260px;
      height: 100vh;
      background: linear-gradient(180deg, var(--primary) 0%, var(--primary-dark) 100%);
      padding: 20px 0;
      z-index: 100;
    }

    .sidebar-header {
      text-align: center;
      padding: 20px;
      border-bottom: 1px solid rgba(255,255,255,0.1);
      margin-bottom: 20px;
    }

    .sidebar-header i.fa-tooth {
      font-size: 50px;
      color: var(--white);
      margin-bottom: 10px;
    }

    .sidebar-header h2 {
      color: var(--white);
      font-size: 18px;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 1px;
    }

    .sidebar-header span {
      color: rgba(255,255,255,0.7);
      font-size: 12px;
    }

    .nav-menu {
      list-style: none;
      padding: 0 15px;
    }

    .nav-menu li {
      margin-bottom: 5px;
    }

    .nav-menu a {
      display: flex;
      align-items: center;
      gap: 15px;
      padding: 14px 20px;
      color: rgba(255,255,255,0.8);
      text-decoration: none;
      border-radius: 10px;
      transition: all 0.3s ease;
      font-size: 14px;
    }

    .nav-menu a:hover, .nav-menu a.active {
      background: rgba(255,255,255,0.15);
      color: var(--white);
      transform: translateX(5px);
    }

    .nav-menu a i {
      width: 20px;
      text-align: center;
      font-size: 18px;
    }

    .nav-menu .logout {
      margin-top: 30px;
      border-top: 1px solid rgba(255,255,255,0.1);
      padding-top: 20px;
    }

    .nav-menu .logout a {
      color: #fca5a5;
    }

    /* ========== CONTENIDO PRINCIPAL ========== */
    .main-content {
      margin-left: 260px;
      padding: 30px;
    }

    .page-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
      background: var(--white);
      padding: 25px 30px;
      border-radius: 15px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    }

    .page-header h1 {
      color: var(--text);
      font-size: 24px;
      font-weight: 600;
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .page-header h1 i {
      color: var(--primary);
    }

    .btn-nuevo {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 12px 25px;
      background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
      color: white;
      text-decoration: none;
      border-radius: 10px;
      font-weight: 500;
      transition: all 0.3s ease;
    }

    .btn-nuevo:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 25px rgba(107, 70, 193, 0.4);
    }

    /* ========== TABLA ========== */
    .table-container {
      background: var(--white);
      border-radius: 15px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.05);
      overflow: hidden;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    thead {
      background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
    }

    thead th {
      padding: 18px 20px;
      text-align: left;
      color: white;
      font-weight: 500;
      font-size: 14px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    tbody tr {
      border-bottom: 1px solid #f0f0f0;
      transition: all 0.3s ease;
    }

    tbody tr:hover {
      background: #faf5ff;
    }

    tbody td {
      padding: 18px 20px;
      color: var(--text);
      font-size: 14px;
    }

    /* ========== BADGE MONTO ========== */
    .monto {
      font-weight: 600;
      color: var(--primary);
      font-size: 16px;
    }

    .numero-factura {
      background: #f3e8ff;
      color: var(--primary);
      padding: 5px 12px;
      border-radius: 20px;
      font-size: 13px;
      font-weight: 500;
    }

    /* ========== ACCIONES ========== */
    .actions {
      display: flex;
      gap: 10px;
    }

    .btn-action {
      width: 38px;
      height: 38px;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.3s ease;
      text-decoration: none;
    }

    .btn-ver {
      background: #d1fae5;
      color: #059669;
    }

    .btn-ver:hover {
      background: #059669;
      color: white;
    }

    .btn-edit {
      background: #dbeafe;
      color: #2563eb;
    }

    .btn-edit:hover {
      background: #2563eb;
      color: white;
    }

    .btn-delete {
      background: #fee2e2;
      color: #dc2626;
    }

    .btn-delete:hover {
      background: #dc2626;
      color: white;
    }

    /* ========== ESTADO VACIO ========== */
    .empty-state {
      text-align: center;
      padding: 60px 20px;
      color: var(--gray);
    }

    .empty-state i {
      font-size: 60px;
      margin-bottom: 20px;
      color: #ddd;
    }

    .empty-state h3 {
      font-size: 18px;
      margin-bottom: 10px;
      color: var(--text);
    }

    /* ========== RESPONSIVE ========== */
    @media (max-width: 992px) {
      .sidebar {
        width: 80px;
      }

      .sidebar-header h2, .sidebar-header span, .nav-menu a span {
        display: none;
      }

      .nav-menu a {
        justify-content: center;
        padding: 14px;
      }

      .main-content {
        margin-left: 80px;
      }
    }

    @media (max-width: 768px) {
      .page-header {
        flex-direction: column;
        gap: 15px;
      }

      .table-container {
        overflow-x: auto;
      }
    }
  </style>
</head>
<body>

<!-- ========== SIDEBAR ========== -->
<nav class="sidebar">
  <div class="sidebar-header">
    <i class="fas fa-tooth"></i>
    <h2>Esthetyc</h2>
    <span>Dental Studio</span>
  </div>

  <ul class="nav-menu">
    <li><a href="index.jsp"><i class="fas fa-home"></i> <span>Inicio</span></a></li>
    <li><a href="pacientes"><i class="fas fa-users"></i> <span>Pacientes</span></a></li>
    <li><a href="doctor"><i class="fas fa-user-md"></i> <span>Doctores</span></a></li>
    <li><a href="cita"><i class="fas fa-calendar-check"></i> <span>Citas</span></a></li>
    <li><a href="factura" class="active"><i class="fas fa-file-invoice-dollar"></i> <span>Facturas</span></a></li>
    <li><a href="#"><i class="fas fa-pills"></i> <span>Medicamentos</span></a></li>

    <li class="logout">
      <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> <span>Cerrar Sesión</span></a>
    </li>
  </ul>
</nav>

<!-- ========== CONTENIDO PRINCIPAL ========== -->
<main class="main-content">
  <!-- Header de página -->
  <div class="page-header">
    <h1><i class="fas fa-file-invoice-dollar"></i> Lista de Facturas</h1>
    <a href="factura?accion=nuevo" class="btn-nuevo">
      <i class="fas fa-plus"></i> Nueva Factura
    </a>
  </div>

  <!-- Tabla de facturas -->
  <div class="table-container">
    <%
      List<Factura> listaFacturas = (List<Factura>) request.getAttribute("listaFacturas");
      if (listaFacturas != null && !listaFacturas.isEmpty()) {
    %>
    <table>
      <thead>
      <tr>
        <th>N° Factura</th>
        <th>Fecha</th>
        <th>ID Paciente</th>
        <th>Monto Total</th>
        <th>Acciones</th>
      </tr>
      </thead>
      <tbody>
      <% for (Factura f : listaFacturas) { %>
      <tr>
        <td><span class="numero-factura"><%= f.getNumeroFactura() %></span></td>
        <td><%= f.getFecha() %></td>
        <td><%= f.getIdPaciente() %></td>
        <td><span class="monto">$<%= String.format("%.2f", f.getMontoTotal()) %></span></td>
        <td>
          <div class="actions">
            <a href="factura?accion=ver&id=<%= f.getIdFactura() %>" class="btn-action btn-ver" title="Ver">
              <i class="fas fa-eye"></i>
            </a>
            <a href="factura?accion=editar&id=<%= f.getIdFactura() %>" class="btn-action btn-edit" title="Editar">
              <i class="fas fa-edit"></i>
            </a>
            <a href="factura?accion=eliminar&id=<%= f.getIdFactura() %>" class="btn-action btn-delete" title="Eliminar" onclick="return confirm('¿Está seguro de eliminar esta factura?')">
              <i class="fas fa-trash"></i>
            </a>
          </div>
        </td>
      </tr>
      <% } %>
      </tbody>
    </table>
    <% } else { %>
    <div class="empty-state">
      <i class="fas fa-file-invoice-dollar"></i>
      <h3>No hay facturas registradas</h3>
      <p>Comienza creando una nueva factura</p>
    </div>
    <% } %>
  </div>
</main>

</body>
</html>
