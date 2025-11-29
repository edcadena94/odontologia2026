package com.odontologia.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * CLASE TEST CONNECTION
 * Clase de utilidad para probar la conexion a la base de datos
 *
 * USO: Ejecutar este main para verificar que la configuracion de JDBC es correcta
 */
public class TestConnection {

    /**
     * Metodo principal para probar la conexion
     */
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   PROBANDO CONEXION A BASE DE DATOS");
        System.out.println("===========================================");

        try (Connection connection = Conexion.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexion exitosa!");
                System.out.println("Base de datos conectada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar:");
            System.out.println(e.getMessage());
            System.out.println("POSIBLES SOLUCIONES:");
            System.out.println("1. Verifica que MySQL este corriendo");
            System.out.println("2. Verifica el nombre de la base de datos en Conexion.java");
            System.out.println("3. Verifica usuario y contrasena en Conexion.java");
        }

        System.out.println("===========================================");
    }
}