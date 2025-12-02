package com.odontologia.repositories;

// Importamos el modelo Usuario
import com.odontologia.models.Usuario;

/**
 * INTERFACE USUARIO REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Es una INTERFACE que define los metodos para acceder a usuarios en la BD
 * - Las interfaces solo DECLARAN metodos, no los implementan
 * - La implementacion real esta en UsuarioRepositoryJdbcImplement.java
 *
 * POR QUE USAR INTERFACE:
 * - Permite cambiar la implementacion sin modificar el resto del codigo
 * - Por ejemplo, podriamos tener una implementacion para MySQL y otra para MongoDB
 * - Facilita las pruebas unitarias (se puede crear una implementacion "falsa" para tests)
 *
 * PATRON DE DISENO:
 * - Esto sigue el principio de "Programar hacia interfaces, no implementaciones"
 * - Es parte del patron Repository
 *
 * COMO SE USA:
 * - No se instancia directamente (es interface)
 * - Se usa la implementacion: UsuarioRepository repo = new UsuarioRepositoryJdbcImplement(conn);
 */
public interface UsuarioRepository {

    /**
     * Busca un usuario por su nombre de usuario (username)
     *
     * Este metodo se usa principalmente para el LOGIN:
     * 1. El usuario ingresa su username y password
     * 2. Buscamos el usuario por username
     * 3. Comparamos el password ingresado con el hash guardado
     *
     * @param username El nombre de usuario a buscar (ej: "admin", "dr.perez")
     * @return El usuario encontrado, o null si no existe
     *
     * EJEMPLO DE USO:
     * Usuario usuario = usuarioRepository.encontrarPorUsername("admin");
     * if (usuario != null) {
     *     // Usuario encontrado, verificar password

     *     if (PasswordUtil.checkPassword(passwordIngresado, usuario.getPasswordHash())) {
     *         // Login exitoso
     *     }
     * } else {
     *     // Usuario no existe
     * }
     */
    Usuario encontrarPorUsername(String username);
}