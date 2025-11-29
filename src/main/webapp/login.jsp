<%--
  Created by IntelliJ IDEA.
  User: erick
  Date: 28/11/2025
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Esthetyc Dental Studio</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome (iconos) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        /* ========== RESET Y ESTILOS BASE ========== */
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

        /* ========== CONTENEDOR PRINCIPAL ========== */
        .login-container {
            display: flex;
            background: #ffffff;
            border-radius: 20px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
            overflow: hidden;
            max-width: 900px;
            width: 100%;
        }

        /* ========== LADO IZQUIERDO (BANNER) ========== */
        .login-banner {
            background: linear-gradient(135deg, #6B46C1 0%, #8B5CF6 100%);
            padding: 60px 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            width: 45%;
            color: white;
        }

        .login-banner i.fa-tooth {
            font-size: 80px;
            margin-bottom: 20px;
            text-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        .login-banner h1 {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 10px;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        .login-banner p {
            font-size: 14px;
            opacity: 0.9;
            line-height: 1.6;
        }

        /* ========== LADO DERECHO (FORMULARIO) ========== */
        .login-form-container {
            padding: 60px 50px;
            width: 55%;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-form-container h2 {
            color: #6B46C1;
            font-size: 28px;
            margin-bottom: 10px;
            font-weight: 600;
        }

        .login-form-container p.subtitle {
            color: #888;
            margin-bottom: 30px;
            font-size: 14px;
        }

        /* ========== CAMPOS DEL FORMULARIO ========== */
        .form-group {
            margin-bottom: 25px;
            position: relative;
        }

        .form-group label {
            display: block;
            color: #555;
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
        }

        .form-group .input-wrapper {
            position: relative;
        }

        .form-group .input-wrapper i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #8B5CF6;
            font-size: 18px;
        }

        .form-group input {
            width: 100%;
            padding: 15px 15px 15px 50px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 15px;
            font-family: 'Poppins', sans-serif;
            transition: all 0.3s ease;
        }

        .form-group input:focus {
            outline: none;
            border-color: #8B5CF6;
            box-shadow: 0 0 0 4px rgba(139, 92, 246, 0.1);
        }

        /* ========== BOTON LOGIN ========== */
        .btn-login {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #6B46C1 0%, #8B5CF6 100%);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            font-family: 'Poppins', sans-serif;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(107, 70, 193, 0.4);
        }

        /* ========== MENSAJE DE ERROR ========== */
        .error-message {
            background: #fee2e2;
            color: #dc2626;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .error-message i {
            font-size: 18px;
        }

        /* ========== RESPONSIVE ========== */
        @media (max-width: 768px) {
            .login-container {
                flex-direction: column;
            }

            .login-banner {
                width: 100%;
                padding: 40px 30px;
            }

            .login-form-container {
                width: 100%;
                padding: 40px 30px;
            }
        }
    </style>
</head>
<body>

<div class="login-container">
    <!-- ========== BANNER IZQUIERDO ========== -->
    <div class="login-banner">
        <i class="fas fa-tooth"></i>
        <h1>Esthetyc Dental Studio</h1>
        <p>Tu sonrisa es nuestra prioridad.<br>Sistema de gestión odontológica.</p>
    </div>

    <!-- ========== FORMULARIO DERECHO ========== -->
    <div class="login-form-container">
        <h2>Bienvenido</h2>
        <p class="subtitle">Ingresa tus credenciales para continuar</p>

        <!-- Mensaje de error (si existe) -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message">
            <i class="fas fa-exclamation-circle"></i>
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <!-- Formulario de login -->
        <form action="login" method="POST">
            <div class="form-group">
                <label for="username">Usuario</label>
                <div class="input-wrapper">
                    <i class="fas fa-user"></i>
                    <input type="text" id="username" name="username" placeholder="Ingresa tu usuario" required>
                </div>
            </div>

            <div class="form-group">
                <label for="password">Contraseña</label>
                <div class="input-wrapper">
                    <i class="fas fa-lock"></i>
                    <input type="password" id="password" name="password" placeholder="Ingresa tu contraseña" required>
                </div>
            </div>

            <button type="submit" class="btn-login">
                <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
            </button>
        </form>
    </div>
</div>

</body>
</html>