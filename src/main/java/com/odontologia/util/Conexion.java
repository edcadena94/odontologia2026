package com.odontologia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * CLASE CONEXION
 * Gestiona la conexión a la base de datos MySQL
 *
 * PATRÓN: Singleton (una sola conexión compartida)
 *
 * USO:
 * Connection conn = Conexion.getConnection();
 */
public class Conexion {

    // ========== CONFIGURACIÓN DE LA BASE DE DATOS ==========

    /**
     * URL de conexión a MySQL
     * Formato: jdbc:mysql://[host]:[puerto]/[nombre_base_datos]
     *
     * IMPORTANTE: Cambia "odontologia" por el nombre de TU base de datos
     */
    private static final String URL = "jdbc:mysql://localhost:3306/odontologia";

    /**
     * Usuario de MySQL
     * Por defecto en XAMPP/WAMP es "root"
     */
    private static final String USER = "root";

    /**
     * Contraseña de MySQL
     * Por defecto en XAMPP/WAMP está vacía ""
     * En algunos casos puede ser "root" o la que hayas configurado
     */
    private static final String PASSWORD = "";

    /**
     * Instancia única de la conexión (Patrón Singleton)
     */
    private static Connection connection;


    // ========== CONSTRUCTOR PRIVADO ==========

    /**
     * Constructor privado para evitar instanciación externa
     * Solo se puede obtener conexión mediante getConnection()
     */
    private Conexion() {
    }


    // ========== MÉTODO PRINCIPAL ==========

    /**
     * Obtiene la conexión a la base de datos
     * Si no existe, la crea. Si ya existe, la reutiliza.
     *
     * @return Objeto Connection para ejecutar queries
     * @throws SQLException Si hay error de conexión
     *
     * EJEMPLO DE USO:
     * try {
     *     Connection conn = Conexion.getConnection();
     *     // Usar la conexión...
     * } catch (SQLException e) {
     *     System.out.println("Error: " + e.getMessage());
     * }
     */
    public static Connection getConnection() throws SQLException {

        // Si la conexión no existe o está cerrada, crear una nueva
        if (connection == null || connection.isClosed()) {
            try {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Crear la conexión
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("✅ Conexión exitosa a la base de datos");

            } catch (ClassNotFoundException e) {
                // Error: No se encontró el driver de MySQL
                System.out.println("❌ Error: Driver de MySQL no encontrado");
                System.out.println("   Asegúrate de tener mysql-connector en el pom.xml");
                throw new SQLException("Driver no encontrado: " + e.getMessage());

            } catch (SQLException e) {
                // Error: No se pudo conectar a MySQL
                System.out.println("❌ Error al conectar a MySQL:");
                System.out.println("   URL: " + URL);
                System.out.println("   Usuario: " + USER);
                System.out.println("   Mensaje: " + e.getMessage());
                throw e;
            }
        }

        return connection;
    }


    // ========== MÉTODO PARA CERRAR CONEXIÓN ==========

    /**
     * Cierra la conexión a la base de datos
     * Llamar cuando la aplicación termine
     *
     * EJEMPLO DE USO:
     * Conexion.closeConnection();
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.out.println("❌ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }


    // ========== MÉTODO PARA PROBAR CONEXIÓN ==========

    /**
     * Prueba si la conexión funciona
     * @return true si la conexión es exitosa
     *
     * EJEMPLO DE USO:
     * if (Conexion.testConnection()) {
     *     System.out.println("Todo bien!");
     * }
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
