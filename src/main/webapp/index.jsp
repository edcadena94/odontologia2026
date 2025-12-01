<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 17/11/2025
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio - Esthetyc Dental Studio</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        /* ========== RESET Y VARIABLES ========== */
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

        /* ========== SIDEBAR (MENU LATERAL) ========== */
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

        /* ========== MENU DE NAVEGACION ========== */
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

        .nav-menu .logout a:hover {
            background: rgba(239, 68, 68, 0.2);
            color: #fecaca;
        }

        /* ========== CONTENIDO PRINCIPAL ========== */
        .main-content {
            margin-left: 260px;
            padding: 30px;
        }

        /* ========== HEADER ========== */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            background: var(--white);
            padding: 20px 30px;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .header h1 {
            color: var(--text);
            font-size: 24px;
            font-weight: 600;
        }

        .header .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .header .user-info .avatar {
            width: 45px;
            height: 45px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: 600;
            font-size: 18px;
        }

        .header .user-info span {
            color: var(--text);
            font-weight: 500;
        }

        .header .user-info small {
            color: var(--gray);
            font-size: 12px;
        }

        /* ========== TARJETAS DE ESTADISTICAS ========== */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 25px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: var(--white);
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            display: flex;
            align-items: center;
            gap: 20px;
            transition: all 0.3s ease;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        .stat-card .icon {
            width: 65px;
            height: 65px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
        }

        .stat-card.pacientes .icon {
            background: linear-gradient(135deg, #6B46C1 0%, #8B5CF6 100%);
            color: white;
        }

        .stat-card.doctores .icon {
            background: linear-gradient(135deg, #059669 0%, #34D399 100%);
            color: white;
        }

        .stat-card.citas .icon {
            background: linear-gradient(135deg, #2563EB 0%, #60A5FA 100%);
            color: white;
        }

        .stat-card.facturas .icon {
            background: linear-gradient(135deg, #DC2626 0%, #F87171 100%);
            color: white;
        }

        .stat-card .info h3 {
            font-size: 28px;
            color: var(--text);
            font-weight: 700;
        }

        .stat-card .info p {
            color: var(--gray);
            font-size: 14px;
        }

        /* ========== ACCIONES RAPIDAS ========== */
        .quick-actions {
            background: var(--white);
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .quick-actions h2 {
            color: var(--text);
            font-size: 20px;
            margin-bottom: 25px;
            font-weight: 600;
        }

        .actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
        }

        .action-btn {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 15px;
            padding: 30px 20px;
            background: var(--gray-light);
            border-radius: 15px;
            text-decoration: none;
            color: var(--text);
            transition: all 0.3s ease;
            border: 2px solid transparent;
        }

        .action-btn:hover {
            border-color: var(--primary);
            background: white;
            transform: translateY(-3px);
        }

        .action-btn i {
            font-size: 35px;
            color: var(--primary);
        }

        .action-btn span {
            font-weight: 500;
            font-size: 14px;
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

        @media (max-width: 576px) {
            .sidebar {
                display: none;
            }

            .main-content {
                margin-left: 0;
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
        <li><a href="index.jsp" class="active"><i class="fas fa-home"></i> <span>Inicio</span></a></li>
        <li><a href="pacientes"><i class="fas fa-users"></i> <span>Pacientes</span></a></li>
        <li><a href="doctor"><i class="fas fa-user-md"></i> <span>Doctores</span></a></li>
        <li><a href="cita"><i class="fas fa-calendar-check"></i> <span>Citas</span></a></li>
        <li><a href="factura"><i class="fas fa-file-invoice-dollar"></i> <span>Facturas</span></a></li>
        <li><a href="medicamentos"><i class="fas fa-pills"></i> <span>Medicamentos</span></a></li>

        <li class="logout">
            <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> <span>Cerrar Sesión</span></a>
        </li>
    </ul>
</nav>

<!-- ========== CONTENIDO PRINCIPAL ========== -->
<main class="main-content">
    <!-- Header -->
    <div class="header">
        <h1>Dashboard</h1>
        <div class="user-info">
            <div>
                <span><%= session.getAttribute("usuario") != null ? session.getAttribute("usuario") : "Usuario" %></span><br>
                <small><%= session.getAttribute("rol") != null ? session.getAttribute("rol") : "Rol" %></small>
            </div>
            <div class="avatar">
                <i class="fas fa-user"></i>
            </div>
        </div>
    </div>

    <!-- Tarjetas de estadísticas -->
    <div class="stats-grid">
        <div class="stat-card pacientes">
            <div class="icon"><i class="fas fa-users"></i></div>
            <div class="info">
                <h3>3</h3>
                <p>Pacientes</p>
            </div>
        </div>

        <div class="stat-card doctores">
            <div class="icon"><i class="fas fa-user-md"></i></div>
            <div class="info">
                <h3>3</h3>
                <p>Doctores</p>
            </div>
        </div>

        <div class="stat-card citas">
            <div class="icon"><i class="fas fa-calendar-check"></i></div>
            <div class="info">
                <h3>3</h3>
                <p>Citas Hoy</p>
            </div>
        </div>

        <div class="stat-card facturas">
            <div class="icon"><i class="fas fa-file-invoice-dollar"></i></div>
            <div class="info">
                <h3>$400</h3>
                <p>Ingresos</p>
            </div>
        </div>
    </div>

    <!-- Acciones rápidas -->
    <div class="quick-actions">
        <h2>Acciones Rápidas</h2>
        <div class="actions-grid">
            <a href="pacientes?accion=nuevo" class="action-btn">
                <i class="fas fa-user-plus"></i>
                <span>Nuevo Paciente</span>
            </a>
            <a href="cita" class="action-btn">
                <i class="fas fa-calendar-plus"></i>
                <span>Agendar Cita</span>
            </a>
            <a href="doctor?accion=nuevo" class="action-btn">
                <i class="fas fa-user-md"></i>
                <span>Nuevo Doctor</span>
            </a>
            <a href="factura?accion=nuevo" class="action-btn">
                <i class="fas fa-receipt"></i>
                <span>Nueva Factura</span>
            </a>
        </div>
    </div>
</main>

</body>
</html>

