<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
v<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Factura - Esthetyc Dental Studio</title>

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

        /* ========== FORMULARIO ========== */
        .form-container {
            background: var(--white);
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            padding: 40px;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 25px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full-width {
            grid-column: span 2;
        }

        .form-group label {
            color: var(--text);
            font-weight: 500;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .form-group label i {
            color: var(--primary);
            margin-right: 8px;
        }

        .form-group input,
        .form-group select {
            padding: 14px 18px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 15px;
            font-family: 'Poppins', sans-serif;
            transition: all 0.3s ease;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: var(--primary-light);
            box-shadow: 0 0 0 4px rgba(139, 92, 246, 0.1);
        }

        /* ========== BOTONES ========== */
        .form-buttons {
            display: flex;
            gap: 15px;
            justify-content: flex-end;
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
            font-family: 'Poppins', sans-serif;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(107, 70, 193, 0.4);
        }

        .btn-secondary {
            background: var(--gray-light);
            color: var(--text);
        }

        .btn-secondary:hover {
            background: #e0e0e0;
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
            .form-grid {
                grid-template-columns: 1fr;
            }

            .form-group.full-width {
                grid-column: span 1;
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
        <li><a href="doctor"><i class="fas fa-user-md"></i> <span>Doctores</span></a></li>
        <li><a href="cita"><i class="fas fa-calendar-check"></i> <span>Citas</span></a></li>
        <li><a href="factura" class="active"><i class="fas fa-file-invoice-dollar"></i> <span>Facturas</span></a></li>
        <a href="medicamentos"><i class="fas fa-pills"></i> <span>Medicamentos</span></a></li>

        <li class="logout">
            <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> <span>Cerrar Sesión</span></a>
        </li>
    </ul>
</nav>

<!-- ========== CONTENIDO PRINCIPAL ========== -->
<main class="main-content">
    <!-- Header de página -->
    <div class="page-header">
        <h1><i class="fas fa-file-invoice-dollar"></i> Nueva Factura</h1>
        <a href="factura" class="btn-volver">
            <i class="fas fa-arrow-left"></i> Volver
        </a>
    </div>

    <!-- Formulario -->
    <div class="form-container">
        <form action="factura" method="POST">
            <input type="hidden" name="accion" value="crear">

            <div class="form-grid">
                <!-- Número de Factura -->
                <div class="form-group">
                    <label><i class="fas fa-hashtag"></i> Número de Factura</label>
                    <input type="text" name="numeroFactura" placeholder="Ej: FAC-001" required>
                </div>

                <!-- Fecha -->
                <div class="form-group">
                    <label><i class="fas fa-calendar"></i> Fecha</label>
                    <input type="date" name="fecha" required>
                </div>

                <!-- ID Paciente -->
                <div class="form-group">
                    <label><i class="fas fa-user"></i> ID del Paciente</label>
                    <input type="number" name="idPaciente" placeholder="Ingrese el ID del paciente" required min="1">
                </div>

                <!-- Monto Total -->
                <div class="form-group">
                    <label><i class="fas fa-dollar-sign"></i> Monto Total</label>
                    <input type="number" name="montoTotal" placeholder="0.00" step="0.01" required min="0">
                </div>
            </div>

            <!-- Botones -->
            <div class="form-buttons">
                <a href="factura" class="btn btn-secondary">
                    <i class="fas fa-times"></i> Cancelar
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Guardar Factura
                </button>
            </div>
        </form>
    </div>
</main>

</body>
</html>