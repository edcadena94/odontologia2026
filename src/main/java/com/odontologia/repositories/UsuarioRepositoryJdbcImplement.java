package com.odontologia.repositories;

// Importamos el modelo Usuario
import com.odontologia.models.Usuario;
// Importamos nuestra clase de conexion
import com.odontologia.util.Conexion;

// Importamos las clases de SQL necesarias
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * CLASE USUARIO REPOSITORY JDBC IMPLEMENT
 *
 * PARA QUE SIRVE:
 * - Esta clase IMPLEMENTA la interface UsuarioRepository
 * - Contiene el codigo real que se conecta a la base de datos
 * - "Jdbc" en el nombre indica que usa JDBC (Java Database Connectivity)
 * - "Implement" indica que es una implementacion de la interface
 *
 * RELACION CON LA INTERFACE:
 * - UsuarioRepository (interface) -> solo DECLARA el metodo encontrarPorUsername()
 * - UsuarioRepositoryJdbcImplement (clase) -> IMPLEMENTA el metodo con codigo real
 *
 * POR QUE "implements UsuarioRepository":
 * - Obliga a esta clase a tener todos los metodos de la interface
 * - Permite usar polimorfismo: UsuarioRepository repo = new UsuarioRepositoryJdbcImplement();
 *
 * COMO SE USA:
 * UsuarioRepository repo = new UsuarioRepositoryJdbcImplement();
 * Usuario usuario = repo.encontrarPorUsername("admin");
 */
public class UsuarioRepositoryJdbcImplement implements UsuarioRepository {

    /**
     * Busca un usuario en la base de datos por su username
     *
     * FLUJO DEL METODO:
     * 1. Abre una conexion a la base de datos
     * 2. Ejecuta un SELECT para buscar el usuario
     * 3. Si encuentra resultado, crea un objeto Usuario con los datos
     * 4. Cierra la conexion automaticamente (try-with-resources)
     * 5. Retorna el usuario encontrado o null si no existe
     *
     * @param username El nombre de usuario a buscar
     * @return El usuario encontrado, o null si no existe
     *
     * EJEMPLO DE USO EN LOGIN:
     * UsuarioRepository repo = new UsuarioRepositoryJdbcImplement();
     * Usuario usuario = repo.encontrarPorUsername("admin");
     *
     * if (usuario != null && PasswordUtil.checkPassword("miPassword", usuario.getPasswordHash())) {
     *     System.out.println("Login exitoso! Rol: " + usuario.getRol());
     * } else {
     *     System.out.println("Usuario o password incorrectos");
     * }
     */
    @Override
    public Usuario encontrarPorUsername(String username) {
        // Inicializamos el usuario como null
        // Si no encontramos nada, retornaremos null
        Usuario usuario = null;

        // Query SQL para buscar por username
        // El ? es un parametro que se llena despues (evita SQL injection)
        String sql = "SELECT * FROM Usuarios WHERE username = ?";

        // try-with-resources: abre conexion y PreparedStatement
        // Se cierran automaticamente al terminar el bloque try
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecemos el valor del parametro ?
            // El 1 indica la posicion del primer ?
            stmt.setString(1, username);

            // Ejecutamos la consulta SELECT
            // ResultSet contiene los resultados
            ResultSet rs = stmt.executeQuery();

            // rs.next() mueve el cursor al siguiente resultado
            // Si hay un resultado, entra al if
            if (rs.next()) {
                // Creamos un nuevo objeto Usuario
                usuario = new Usuario();

                // Extraemos cada columna del resultado y la asignamos
                // rs.getInt("nombre_columna") -> obtiene valor entero
                // rs.getString("nombre_columna") -> obtiene valor texto
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPasswordHash(rs.getString("password_hash"));
                usuario.setRol(rs.getString("rol"));
            }
            // Si rs.next() es false, no hay resultados
            // usuario seguira siendo null

        } catch (Exception e) {
            // Si hay cualquier error, lo imprimimos
            // El usuario seguira siendo null
            e.printStackTrace();
        }

        // Retornamos el usuario (puede ser null si no se encontro)
        return usuario;
    }
}