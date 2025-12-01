<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.odontologia.models.Doctor" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ver Doctor - Esthetyc Dental Studio</title>

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

        .btn-volver {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 12px 25px;
            background: var(--gray-light);
            color: var(--text);
            text-decoration: none;
            border-radius: 10px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-volver:hover {
            background: #e0e0e0;
        }

        /* ========== TARJETA DEL DOCTOR ========== */
        .doctor-profile {
            background: var(--white);
            border-radius: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            overflow: hidden;
        }

        .profile-header {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            padding: 50px 40px;
            text-align: center;
            color: white;
        }

        .profile-avatar {
            width: 120px;
            height: 120px;
            background: rgba(255,255,255,0.2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            border: 4px solid rgba(255,255,255,0.3);
        }

        .profile-avatar i {
            font-size: 50px;
        }

        .profile-header h2 {
            font-size: 28px;
            margin-bottom: 10px;
        }

        .profile-header .especialidad {
            background: rgba(255,255,255,0.2);
            padding: 8px 25px;
            border-radius: 25px;
            display: inline-block;
            font-size: 14px;
        }

        .profile-body {
            padding: 40px;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 30px;
        }

        .info-item {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 20px;
            background: var(--gray-light);
            border-radius: 15px;
        }

        .info-item .icon {
            width: 55px;
            height: 55px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 22px;
        }

        .info-item .details label {
            display: block;
            color: var(--gray);
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 5px;
        }

        .info-item .details span {
            color: var(--text);
            font-size: 16px;
            font-weight: 500;
        }

        /* ========== BOTONES ========== */
        .profile-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            margin-top: 30px;
            padding-top: 30px;
            border-top: 1px solid #f0f0f0;
        }

        .btn {
            padding: 14px 30px;
            border: none;
            border-radius: 10px;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
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
            .info-grid {
                grid-template-columns: 1fr;
            }

            .page-header {
                flex-direction: column;
                gap: 15px;
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
        <a href="medicamentos"><i class="fas fa-pills"></i> <span>Medicamentos</span></a></li>

        <li class="logout">
            <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> <span>Cerrar Sesión</span></a>
        </li>
    </ul>
</nav>

<!-- ========== CONTENIDO PRINCIPAL ========== -->
<main class="main-content">
    <%
        Doctor doctor = (Doctor) request.getAttribute("doctor");
        if (doctor == null) {
            response.sendRedirect("doctor?error=not_found");
            return;
        }
    %>

    <!-- Header de página -->
    <div class="page-header">
        <h1><i class="fas fa-user-md"></i> Perfil del Doctor</h1>
        <a href="doctor" class="btn-volver">
            <i class="fas fa-arrow-left"></i> Volver
        </a>
    </div>

    <!-- Perfil del doctor -->
    <div class="doctor-profile">
        <div class="profile-header">
            <div class="profile-avatar">
                <i class="fas fa-user-md"></i>
            </div>
            <h2>Dr. <%= doctor.getNombre() %> <%= doctor.getApellido() %></h2>
            <span class="especialidad"><%= doctor.getEspecialidad() %></span>
        </div>

        <div class="profile-body">
            <div class="info-grid">
                <div class="info-item">
                    <div class="icon"><i class="fas fa-id-card"></i></div>
                    <div class="details">
                        <label>ID Doctor</label>
                        <span><%= doctor.getIdDoctor() %></span>
                    </div>
                </div>

                <div class="info-item">
                    <div class="icon"><i class="fas fa-stethoscope"></i></div>
                    <div class="details">
                        <label>Especialidad</label>
                        <span><%= doctor.getEspecialidad() %></span>
                    </div>
                </div>

                <div class="info-item">
                    <div class="icon"><i class="fas fa-phone"></i></div>
                    <div class="details">
                        <label>Teléfono</label>
                        <span><%= doctor.getTelefono() != null && !doctor.getTelefono().isEmpty() ? doctor.getTelefono() : "No registrado" %></span>
                    </div>
                </div>

                <div class="info-item">
                    <div class="icon"><i class="fas fa-envelope"></i></div>
                    <div class="details">
                        <label>Email</label>
                        <span><%= doctor.getEmail() != null && !doctor.getEmail().isEmpty() ? doctor.getEmail() : "No registrado" %></span>
                    </div>
                </div>
            </div>

            <div class="profile-actions">
                <a href="doctor?accion=editar&id=<%= doctor.getIdDoctor() %>" class="btn btn-edit">
                    <i class="fas fa-edit"></i> Editar
                </a>
                <a href="doctor?accion=eliminar&id=<%= doctor.getIdDoctor() %>" class="btn btn-delete" onclick="return confirm('¿Está seguro de eliminar este doctor?')">
                    <i class="fas fa-trash"></i> Eliminar
                </a>
            </div>
        </div>
    </div>
</main>

</body>
</html>
