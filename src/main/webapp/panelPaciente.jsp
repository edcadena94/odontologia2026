<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.odontologia.models.Usuario" %>
<%
    // Verificar que haya sesión activa
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !usuario.isPaciente()) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String cedulaPaciente = usuario.getCedulaPaciente();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Portal - Esthetyc Dental Studio</title>
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
            max-width: 1200px;
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

        .welcome-card {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            text-align: center;
        }

        .welcome-card h2 {
            color: #667eea;
            margin-bottom: 10px;
        }

        .welcome-card p {
            color: #666;
            font-size: 16px;
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
        }

        .module-icon.purple { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .module-icon.blue { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
        .module-icon.green { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }

        .module-card h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 20px;
        }

        .module-card p {
            color: #666;
            font-size: 14px;
        }

        .info-box {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 10px;
            margin-top: 15px;
            border-left: 4px solid #667eea;
        }

        .info-box strong {
            color: #667eea;
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                gap: 20px;
                text-align: center;
            }

            .modules-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <h1><i class="fas fa-user-circle"></i> Mi Portal Paciente</h1>
            <div class="user-info">
                <div class="user-avatar">
                    <%= usuario.getUsername().substring(0, 1).toUpperCase() %>
                </div>
                <div>
                    <strong><%= usuario.getUsername() %></strong>
                    <p style="color: #666; font-size: 12px;">Paciente</p>
                </div>
                <a href="login.jsp" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> Salir
                </a>
            </div>
        </div>

        <!-- Bienvenida -->
        <div class="welcome-card">
            <h2>¡Bienvenido a tu Portal!</h2>
            <p>Aquí puedes ver tus citas, facturas e historial médico</p>
            <div class="info-box">
                <strong>Tu Cédula:</strong> <%= cedulaPaciente %>
            </div>
        </div>

        <!-- Módulos -->
        <div class="modules-grid">
            <a href="cita?paciente=<%= cedulaPaciente %>" class="module-card">
                <div class="module-icon purple">
                    <i class="fas fa-calendar-check"></i>
                </div>
                <h3>Mis Citas</h3>
                <p>Ver mis citas programadas y historial</p>
            </a>

            <a href="cita?accion=nuevo&paciente=<%= cedulaPaciente %>" class="module-card">
                <div class="module-icon blue">
                    <i class="fas fa-calendar-plus"></i>
                </div>
                <h3>Agendar Cita</h3>
                <p>Solicitar una nueva cita</p>
            </a>

            <a href="factura?paciente=<%= cedulaPaciente %>" class="module-card">
                <div class="module-icon green">
                    <i class="fas fa-file-invoice-dollar"></i>
                </div>
                <h3>Mis Facturas</h3>
                <p>Ver mis facturas y pagos</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon purple">
                    <i class="fas fa-notes-medical"></i>
                </div>
                <h3>Historial Clínico</h3>
                <p>Ver mi historial médico</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon blue">
                    <i class="fas fa-user-edit"></i>
                </div>
                <h3>Mi Perfil</h3>
                <p>Actualizar mis datos personales</p>
            </a>

            <a href="#" class="module-card">
                <div class="module-icon green">
                    <i class="fas fa-phone-alt"></i>
                </div>
                <h3>Contacto</h3>
                <p>Contáctanos para consultas</p>
            </a>
        </div>
    </div>
</body>
</html>
