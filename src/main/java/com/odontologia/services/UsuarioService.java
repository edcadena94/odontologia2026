package com.odontologia.services;

// Importamos el modelo Usuario
import com.odontologia.models.Usuario;

/**
 * INTERFACE USUARIO SERVICE
 *
 * PARA QUE SIRVE:
 * - Define los metodos que debe tener el servicio de usuarios
 * - Es una INTERFACE, solo declara metodos, no los implementa
 * - La implementacion real esta en UsuarioServiceJdbcImplement.java
 *
 * OPERACIONES QUE MANEJA:
 * - Login de usuarios (autenticacion)
 *
 * FLUJO DE LOGIN:
 * 1. Usuario ingresa username y password en el formulario
 * 2. Controller recibe los datos
 * 3. Controller llama a UsuarioService.login(username, password)
 * 4. Service busca el usuario y verifica el password
 * 5. Si es correcto, retorna el Usuario
 * 6. Si es incorrecto, retorna null
 */
public interface UsuarioService {

    /**
     * Realiza el login de un usuario
     *
     * @param username Nombre de usuario ingresado
     * @param password Contrasena ingresada (texto plano)
     * @return El usuario si las credenciales son correctas, null si son incorrectas
     *
     * EJEMPLO:
     * Usuario usuario = usuarioService.login("admin", "password123");
     *
     * if (usuario != null) {
     *     // Login exitoso
     *     System.out.println("Bienvenido " + usuario.getUsername());
     *     System.out.println("Rol: " + usuario.getRol());
     * } else {
     *     // Login fallido
     *     System.out.println("Usuario o contrasena incorrectos");
     * }
     */
    Usuario login(String username, String password);
}