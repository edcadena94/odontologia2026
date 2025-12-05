<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.odontologia.models.Usuario" %>
<%
    // Verificar que haya sesión activa
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !usuario.isAdmin()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrador - Esthetyc Dental Studio</title>
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

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            display: flex;
            align-items: center;
            gap: 20px;
            transition: all 0.3s;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 24px rgba(0,0,0,0.15);
        }

        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            color: white;
        }

        .stat-icon.purple { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .stat-icon.blue { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
        .stat-icon.green { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }
        .stat-icon.orange { background: linear-gradient(135deg, #fa709a 0%, #fee140 100%); }

        .stat-info h3 {
            font-size: 32px;
            color: #333;
            margin-bottom: 5px;
        }

        .stat-info p {
            color: #666;
            font-size: 14px;
        }

        .modules-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
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

            .stats-grid, .modules-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-crown"></i> Panel Administrador</h1>
            <div class="user-info">
                <div class="user-avatar">
                    <%= usuario.getUsername().substring(0, 1).toUpperCase() %>
                </div>
                <div>
                    <strong><%= usuario.getUsername() %></strong>
                    <p style="color: #666; font-size: 12px;">Administrador</p>
                </div>
                <a href="login.jsp" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Salir
                </a>
            </div>
        </div>

        <!-- Estadísticas -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon purple">
                    <i class="fas fa-users"></i>
                </div>
                <div class="stat-info">
                    <h3 id="totalPacientes">-</h3>
                    <p>Total Pacientes</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon blue">
                    <i class="fas fa-user-md"></i>
                </div>
                <div class="stat-info">
                    <h3 id="totalDoctores">-</h3>
                    <p>Doctores Activos</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon green">
                    <i class="fas fa-calendar-check"></i>
                </div>
                <div class="stat-info">
                    <h3 id="citasHoy">-</h3>
                    <p>Citas Hoy</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon orange">
                    <i class="fas fa-dollar-sign"></i>
                </div>
                <div class="stat-info">
                    <h3 id="facturasHoy">-</h3>
                    <p>Facturas Hoy</p>
                </div>
            </div>
        </div>

        <!-- Módulos -->
        <div class="modules-grid">
            <a href="pacientes" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-users"></i>
                </div>
                <h3>Pacientes</h3>
                <p>Gestionar pacientes registrados</p>
            </a>

            <a href="doctor" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-user-md"></i>
                </div>
                <h3>Doctores</h3>
                <p>Gestionar odontólogos</p>
            </a>

            <a href="cita" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-calendar-alt"></i>
                </div>
                <h3>Citas</h3>
                <p>Gestionar agenda de citas</p>
            </a>

            <a href="factura" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-file-invoice-dollar"></i>
                </div>
                <h3>Facturas</h3>
                <p>Gestionar facturación</p>
            </a>

            <a href="medicamentos" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-pills"></i>
                </div>
                <h3>Medicamentos</h3>
                <p>Inventario de medicamentos</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-users-cog"></i>
                </div>
                <h3>Usuarios</h3>
                <p>Gestionar usuarios del sistema</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-chart-bar"></i>
                </div>
                <h3>Reportes</h3>
                <p>Ver estadísticas y reportes</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon">
                    <i class="fas fa-cog"></i>
                </div>
                <h3>Configuración</h3>
                <p>Ajustes del sistema</p>
            </a>
        </div>
    </div>
</body>
</html>
