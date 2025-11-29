<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.odontologia.models.Doctor" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Doctores - Esthetyc Dental Studio</title>

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

    /* ========== TARJETAS DE DOCTORES ========== */
    .doctors-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
      gap: 25px;
    }

    .doctor-card {
      background: var(--white);
      border-radius: 15px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.05);
      overflow: hidden;
      transition: all 0.3s ease;
    }

    .doctor-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 35px rgba(0,0,0,0.1);
    }

    .doctor-card-header {
      background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
      padding: 25px;
      text-align: center;
    }

    .doctor-avatar {
      width: 80px;
      height: 80px;
      background: rgba(255,255,255,0.2);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 0 auto 15px;
      border: 3px solid rgba(255,255,255,0.3);
    }

    .doctor-avatar i {
      font-size: 35px;
      color: white;
    }

    .doctor-card-header h3 {
      color: white;
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 5px;
    }

    .doctor-card-header span {
      color: rgba(255,255,255,0.8);
      font-size: 13px;
      background: rgba(255,255,255,0.2);
      padding: 5px 15px;
      border-radius: 20px;
      display: inline-block;
    }

    .doctor-card-body {
      padding: 25px;
    }

    .doctor-info {
      margin-bottom: 20px;
    }

    .doctor-info p {
      display: flex;
      align-items: center;
      gap: 12px;
      color: var(--text);
      font-size: 14px;
      margin-bottom: 12px;
    }

    .doctor-info p i {
      color: var(--primary);
      width: 20px;
      text-align: center;
    }

    .doctor-actions {
      display: flex;
      gap: 10px;
    }

    .doctor-actions a {
      flex: 1;
      padding: 12px;
      border-radius: 8px;
      text-decoration: none;
      text-align: center;
      font-size: 13px;
      font-weight: 500;
      transition: all 0.3s ease;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
    }

    .btn-editar {
      background: #dbeafe;
      color: #2563eb;
    }

    .btn-editar:hover {
      background: #2563eb;
      color: white;
    }

    .btn-eliminar {
      background: #fee2e2;
      color: #dc2626;
    }

    .btn-eliminar:hover {
      background: #dc2626;
      color: white;
    }

    /* ========== ESTADO VACIO ========== */
    .empty-state {
      text-align: center;
      padding: 60px 20px;
      background: var(--white);
      border-radius: 15px;
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

      .doctors-grid {
        grid-template-columns: 1fr;
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
    <li><a href="doctor" class="active"><i class="fas fa-user-md"></i> <span>Doctores</span></a></li>
    <li><a href="cita"><i class="fas fa-calendar-check"></i> <span>Citas</span></a></li>
    <li><a href="factura"><i class="fas fa-file-invoice-dollar"></i> <span>Facturas</span></a></li>
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
    <h1><i class="fas fa-user-md"></i> Nuestros Doctores</h1>
    <a href="doctor?accion=nuevo" class="btn-nuevo">
      <i class="fas fa-plus"></i> Nuevo Doctor
    </a>
  </div>

  <!-- Grid de Doctores -->
  <%
    List<Doctor> listaDoctores = (List<Doctor>) request.getAttribute("listaDoctores");
    if (listaDoctores != null && !listaDoctores.isEmpty()) {
  %>
  <div class="doctors-grid">
    <% for (Doctor d : listaDoctores) { %>
    <div class="doctor-card">
      <div class="doctor-card-header">
        <div class="doctor-avatar">
          <i class="fas fa-user-md"></i>
        </div>
        <h3>Dr. <%= d.getNombre() %> <%= d.getApellido() %></h3>
        <span><%= d.getEspecialidad() %></span>
      </div>
      <div class="doctor-card-body">
        <div class="doctor-info">
          <p><i class="fas fa-phone"></i> <%= d.getTelefono() != null ? d.getTelefono() : "No registrado" %></p>
          <p><i class="fas fa-envelope"></i> <%= d.getEmail() != null ? d.getEmail() : "No registrado" %></p>
        </div>
        <div class="doctor-actions">
          <a href="doctor?accion=editar&id=<%= d.getIdDoctor() %>" class="btn-editar">
            <i class="fas fa-edit"></i> Editar
          </a>
          <a href="doctor?accion=eliminar&id=<%= d.getIdDoctor() %>" class="btn-eliminar" onclick="return confirm('¿Está seguro de eliminar este doctor?')">
            <i class="fas fa-trash"></i> Eliminar
          </a>
        </div>
      </div>
    </div>
    <% } %>
  </div>
  <% } else { %>
  <div class="empty-state">
    <i class="fas fa-user-md"></i>
    <h3>No hay doctores registrados</h3>
    <p>Comienza agregando un nuevo doctor</p>
  </div>
  <% } %>
</main>

</body>
</html>
