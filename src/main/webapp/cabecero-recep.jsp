<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Recepcionista - Esthetyc Dental Studio</title>

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

        .header h1 i {
            color: var(--primary);
            margin-right: 10px;
        }

        .header .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .header .user-info .avatar {
            width: 45px;
            height: 45px;
            background: linear-gradient(135deg, #10b981 0%, #34d399 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
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

        .role-badge {
            background: linear-gradient(135deg, #10b981 0%, #34d399 100%);
            color: white;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
        }

        /* ========== BIENVENIDA ========== */
        .welcome-card {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            border-radius: 20px;
            padding: 40px;
            color: white;
            margin-bottom: 30px;
            position: relative;
            overflow: hidden;
        }

        .welcome-card::before {
            content: '';
            position: absolute;
            top: -50px;
            right: -50px;
            width: 200px;
            height: 200px;
            background: rgba(255,255,255,0.1);
            border-radius: 50%;
        }

        .welcome-card h2 {
            font-size: 28px;
            margin-bottom: 10px;
        }

        .welcome-card p {
            opacity: 0.9;
            font-size: 16px;
        }

        /* ========== TARJETAS DE ACCIONES ========== */
        .actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 30px;
        }

        .action-card {
            background: var(--white);
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            transition: all 0.3s ease;
            text-decoration: none;
            color: var(--text);
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        }

        .action-card .icon {
            width: 70px;
            height: 70px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
        }

        .action-card.pacientes .icon {
            background: linear-gradient(135deg, #6B46C1 0%, #8B5CF6 100%);
            color: white;
        }

        .action-card.citas .icon {
            background: linear-gradient(135deg, #2563EB 0%, #60A5FA 100%);
            color: white;
        }

        .action-card.doctores .icon {
            background: linear-gradient(135deg, #059669 0%, #34D399 100%);
            color: white;
        }

        .action-card.facturas .icon {
            background: linear-gradient(135deg, #DC2626 0%, #F87171 100%);
            color: white;
        }

        .action-card .info h3 {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 5px;
        }

        .action-card .info p {
            color: var(--gray);
            font-size: 13px;
        }

        /* ========== TAREAS RAPIDAS ========== */
        .quick-tasks {
            background: var(--white);
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .quick-tasks h3 {
            color: var(--text);
            font-size: 18px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .quick-tasks h3 i {
            color: var(--primary);
        }

        .task-list {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }

        .task-btn {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 15px 20px;
            background: var(--gray-light);
            border-radius: 10px;
            text-decoration: none;
            color: var(--text);
            font-size: 14px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .task-btn:hover {
            background: var(--primary);
            color: white;
        }

        .task-btn i {
            font-size: 18px;
            color: var(--primary);
        }

        .task-btn:hover i {
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

        @media (max-width: 576px) {
            .sidebar {
                display: none;
            }

            .main-content {
                margin-left: 0;
            }

            .header {
                flex-direction: column;
                gap: 15px;
                text-align: center;
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
        <li><a href="cabecero-recep.jsp" class="active"><i class="fas fa-home"></i> <span>Inicio</span></a></li>
        <li><a href="pacientes"><i class="fas fa-users"></i> <span>Pacientes</span></a></li>
        <li><a href="cita"><i class="fas fa-calendar-check"></i> <span>Citas</span></a></li>
        <li><a href="doctor"><i class="fas fa-user-md"></i> <span>Doctores</span></a></li>
        <li><a href="factura"><i class="fas fa-file-invoice-dollar"></i> <span>Facturas</span></a></li>

        <li class="logout">
            <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> <span>Cerrar Sesión</span></a>
        </li>
    </ul>
</nav>

<!-- ========== CONTENIDO PRINCIPAL ========== -->
<main class="main-content">
    <!-- Header -->
    <div class="header">
        <h1><i class="fas fa-desktop"></i> Panel de Recepción</h1>
        <div class="user-info">
            <div>
                <span><%= session.getAttribute("usuario") != null ? session.getAttribute("usuario") : "Recepcionista" %></span><br>
                <small><span class="role-badge">Recepcionista</span></small>
            </div>
            <div class="avatar">
                <i class="fas fa-user"></i>
            </div>
        </div>
    </div>

    <!-- Bienvenida -->
    <div class="welcome-card">
        <h2>¡Bienvenido/a al Panel de Recepción!</h2>
        <p>Gestiona pacientes, agenda citas y administra la clínica desde aquí.</p>
    </div>

    <!-- Acciones principales -->
    <div class="actions-grid">
        <a href="pacientes" class="action-card pacientes">
            <div class="icon"><i class="fas fa-users"></i></div>
            <div class="info">
                <h3>Pacientes</h3>
                <p>Ver y registrar pacientes</p>
            </div>
        </a>

        <a href="cita" class="action-card citas">
            <div class="icon"><i class="fas fa-calendar-check"></i></div>
            <div class="info">
                <h3>Agendar Cita</h3>
                <p>Programar nuevas citas</p>
            </div>
        </a>

        <a href="doctor" class="action-card doctores">
            <div class="icon"><i class="fas fa-user-md"></i></div>
            <div class="info">
                <h3>Doctores</h3>
                <p>Ver doctores disponibles</p>
            </div>
        </a>

        <a href="factura" class="action-card facturas">
            <div class="icon"><i class="fas fa-file-invoice-dollar"></i></div>
            <div class="info">
                <h3>Facturas</h3>
                <p>Gestionar facturación</p>
            </div>
        </a>
    </div>

    <!-- Tareas rápidas -->
    <div class="quick-tasks">
        <h3><i class="fas fa-bolt"></i> Acciones Rápidas</h3>
        <div class="task-list">
            <a href="registrarPaciente.jsp" class="task-btn">
                <i class="fas fa-user-plus"></i> Nuevo Paciente
            </a>
            <a href="cita" class="task-btn">
                <i class="fas fa-calendar-plus"></i> Nueva Cita
            </a>
            <a href="factura?accion=nuevo" class="task-btn">
                <i class="fas fa-receipt"></i> Nueva Factura
            </a>
            <a href="pacientes" class="task-btn">
                <i class="fas fa-search"></i> Buscar Paciente
            </a>
        </div>
    </div>
</main>

</body>
</html>