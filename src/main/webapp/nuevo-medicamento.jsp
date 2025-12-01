<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 30/11/2025
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Medicamento - Esthetyc Dental Studio</title>

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
            --danger: #dc2626;
            --success: #059669;
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
            border: 1px solid #ddd;
        }

        .btn-volver:hover {
            background: #e5e5e5;
        }

        /* ========== FORMULARIO ========== */
        .form-container {
            background: var(--white);
            border-radius: 15px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            padding: 40px;
            max-width: 600px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text);
            font-size: 14px;
        }

        .form-group label .required {
            color: var(--danger);
        }

        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 14px 18px;
            border: 2px solid #e5e5e5;
            border-radius: 10px;
            font-size: 14px;
            font-family: 'Poppins', sans-serif;
            transition: all 0.3s ease;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(107, 70, 193, 0.1);
        }

        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }

        .form-group .input-icon {
            position: relative;
        }

        .form-group .input-icon i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--gray);
        }

        .form-group .input-icon input {
            padding-left: 45px;
        }

        .form-group small {
            display: block;
            margin-top: 5px;
            color: var(--gray);
            font-size: 12px;
        }

        /* ========== BOTONES ========== */
        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }

        .btn-guardar {
            flex: 1;
            padding: 15px 30px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-guardar:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(107, 70, 193, 0.4);
        }

        .btn-cancelar {
            padding: 15px 30px;
            background: var(--gray-light);
            color: var(--text);
            border: 1px solid #ddd;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-cancelar:hover {
            background: #e5e5e5;
        }

        /* ========== ALERTAS ========== */
        .alert {
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .alert-error {
            background: #fee2e2;
            color: var(--danger);
            border: 1px solid #fecaca;
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

            .form-container {
                padding: 25px;
            }

            .form-actions {
                flex-direction: column;
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
        <li><a href="factura"><i class="fas fa-file-invoice-dollar"></i> <span>Facturas</span></a></li>
        <li><a href="medicamentos" class="active"><i class="fas fa-pills"></i> <span>Medicamentos</span></a></li>

        <li class="logout">
            <a href="login.jsp"><i class="fas fa-sign-out-alt"></i> <span>Cerrar Sesión</span></a>
        </li>
    </ul>
</nav>

<!-- ========== CONTENIDO PRINCIPAL ========== -->
<main class="main-content">
    <!-- Header de página -->
    <div class="page-header">
        <h1><i class="fas fa-plus-circle"></i> Nuevo Medicamento</h1>
        <a href="medicamentos" class="btn-volver">
            <i class="fas fa-arrow-left"></i> Volver a la lista
        </a>
    </div>

    <!-- Formulario -->
    <div class="form-container">

        <!-- Mensaje de error -->
        <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-error">
            <i class="fas fa-exclamation-circle"></i>
            <% if ("campos_vacios".equals(request.getParameter("error"))) { %>
            Por favor complete todos los campos obligatorios.
            <% } else { %>
            Ocurrió un error al guardar el medicamento.
            <% } %>
        </div>
        <% } %>

        <form action="medicamentos" method="POST">
            <input type="hidden" name="accion" value="crear">

            <!-- Nombre -->
            <div class="form-group">
                <label for="nombre">Nombre del Medicamento <span class="required">*</span></label>
                <div class="input-icon">
                    <i class="fas fa-pills"></i>
                    <input type="text" id="nombre" name="nombre" required
                           placeholder="Ej: Ibuprofeno 400mg">
                </div>
            </div>

            <!-- Descripción -->
            <div class="form-group">
                <label for="descripcion">Descripción</label>
                <textarea id="descripcion" name="descripcion"
                          placeholder="Descripción del medicamento, indicaciones, etc."></textarea>
                <small>Opcional: Agregue detalles sobre el uso o composición del medicamento.</small>
            </div>

            <!-- Stock -->
            <div class="form-group">
                <label for="stock">Stock Inicial <span class="required">*</span></label>
                <div class="input-icon">
                    <i class="fas fa-boxes"></i>
                    <input type="number" id="stock" name="stock" required
                           min="0" value="0" placeholder="Cantidad en inventario">
                </div>
                <small>Cantidad de unidades disponibles en el inventario.</small>
            </div>

            <!-- Botones -->
            <div class="form-actions">
                <a href="medicamentos" class="btn-cancelar">
                    <i class="fas fa-times"></i> Cancelar
                </a>
                <button type="submit" class="btn-guardar">
                    <i class="fas fa-save"></i> Guardar Medicamento
                </button>
            </div>
        </form>
    </div>
</main>

</body>
</html>
