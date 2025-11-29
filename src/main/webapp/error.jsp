<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Esthetyc Dental Studio</title>

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

        body {
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
        }

        .error-container {
            background: white;
            border-radius: 20px;
            padding: 60px 50px;
            text-align: center;
            max-width: 500px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
        }

        .error-icon {
            width: 120px;
            height: 120px;
            background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 30px;
        }

        .error-icon i {
            font-size: 50px;
            color: #dc2626;
        }

        h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 15px;
        }

        p {
            color: #888;
            font-size: 16px;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .error-message {
            background: #fef2f2;
            color: #dc2626;
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            font-size: 14px;
        }

        .btn-home {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            padding: 15px 35px;
            background: linear-gradient(135deg, #6B46C1 0%, #8B5CF6 100%);
            color: white;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-home:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(107, 70, 193, 0.4);
        }

        .logo {
            margin-top: 40px;
            padding-top: 30px;
            border-top: 1px solid #f0f0f0;
        }

        .logo i {
            font-size: 30px;
            color: #6B46C1;
            margin-bottom: 10px;
        }

        .logo span {
            display: block;
            color: #888;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="error-container">
    <div class="error-icon">
        <i class="fas fa-exclamation-triangle"></i>
    </div>

    <h1>¡Oops! Algo salió mal</h1>
    <p>Ha ocurrido un error inesperado. Por favor, intenta nuevamente o regresa al inicio.</p>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error-message">
        <i class="fas fa-info-circle"></i>
        <%= request.getAttribute("errorMessage") %>
    </div>
    <% } %>

    <a href="index.jsp" class="btn-home">
        <i class="fas fa-home"></i> Volver al Inicio
    </a>

    <div class="logo">
        <i class="fas fa-tooth"></i>
        <span>Esthetyc Dental Studio</span>
    </div>
</div>

</body>
</html>