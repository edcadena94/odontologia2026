<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.odontologia.models.Usuario" %>
<%
    // Verificar que haya sesión activa
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !usuario.isSecretaria()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Secretaria - Esthetyc Dental Studio</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }

        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            background: white;
            padding: 20px 30px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .header h1 {
            color: #667eea;
            font-size: 28px;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-avatar {
            width: 50px;
            height: 50px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 20px;
            font-weight: bold;
        }

        .logout-btn {
            background: #dc3545;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            transition: all 0.3s;
        }

        .logout-btn:hover {
            background: #c82333;
            transform: translateY(-2px);
        }

        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-bottom: 30px;
        }

        .quick-btn {
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            text-align: center;
            text-decoration: none;
            color: #333;
            transition: all 0.3s;
            border: 2px solid transparent;
        }

        .quick-btn:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0,0,0,0.15);
            border-color: #667eea;
        }

        .quick-btn i {
            font-size: 32px;
            color: #667eea;
            margin-bottom: 10px;
        }

        .quick-btn span {
            display: block;
            font-weight: 600;
            font-size: 14px;
        }

        .modules-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
        }

        .module-card {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            text-align: center;
            transition: all 0.3s;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
            display: block;
        }

        .module-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.2);
        }

        .module-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 36px;
            color: white;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        .module-card h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 20px;
        }

        .module-card p {
            color: #666;
            font-size: 14px;
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                gap: 20px;
                text-align: center;
            }

            .quick-actions, .modules-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-user-tie"></i> Panel Secretaria</h1>
            <div class="user-info">
                <div class="user-avatar">
                    <%= usuario.getUsername().substring(0, 1).toUpperCase() %>
                </div>
                <div>
                    <strong><%= usuario.getUsername() %></strong>
                    <p style="color: #666; font-size: 12px;">Secretaria</p>
                </div>
                <a href="login.jsp" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Salir
                </a>
            </div>
        </div>

        <!-- Acciones Rápidas -->
        <div class="quick-actions">
            <a href="cita?accion=nuevo" class="quick-btn">
                <i class="fas fa-calendar-plus"></i>
                <span>Nueva Cita</span>
            </a>

            <a href="pacientes?accion=nuevo" class="quick-btn">
                <i class="fas fa-user-plus"></i>
                <span>Nuevo Paciente</span>
            </a>

            <a href="factura?accion=nuevo" class="quick-btn">
                <i class="fas fa-file-invoice"></i>
                <span>Nueva Factura</span>
            </a>

            <a href="cita" class="quick-btn">
                <i class="fas fa-clock"></i>
                <span>Citas del Día</span>
            </a>
        </div>

        <!-- Módulos Principales -->
        <div class="modules-grid">
            <a href="pacientes" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-users"></i>
                </div>
                <h3>Gestión de Pacientes</h3>
                <p>Registrar, buscar y actualizar información de pacientes</p>
            </a>

            <a href="cita" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-calendar-alt"></i>
                </div>
                <h3>Agenda de Citas</h3>
                <p>Ver, agendar y gestionar citas médicas</p>
            </a>

            <a href="factura" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-file-invoice-dollar"></i>
                </div>
                <h3>Facturación</h3>
                <p>Generar y gestionar facturas de pacientes</p>
            </a>

            <a href="doctor" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-user-md"></i>
                </div>
                <h3>Doctores</h3>
                <p>Ver información de odontólogos</p>
            </a>

            <a href="medicamentos" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-pills"></i>
                </div>
                <h3>Medicamentos</h3>
                <p>Consultar inventario de medicamentos</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-chart-line"></i>
                </div>
                <h3>Reportes</h3>
                <p>Ver estadísticas y reportes del día</p>
            </a>
        </div>
    </div>
</body>
</html>
