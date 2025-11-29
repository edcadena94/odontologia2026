package com.odontologia.repositories;

import com.odontologia.models.Medicamentos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del repositorio de Medicamentos usando JDBC
 * Esta clase contiene todas las consultas SQL para la tabla medicamentos
 * Maneja las operaciones CRUD (Create, Read, Update, Delete)
 */
public class MedicamentosRepositoryJdbcImplement implements MedicamentosRepository {

    // Conexión a la base de datos MySQL
    private Connection conn;

    /**
     * Constructor que recibe la conexión a la base de datos
     * @param conn Conexión activa a MySQL
     */
    public MedicamentosRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene todos los medicamentos de la tabla medicamentos
     * Ejecuta: SELECT * FROM medicamentos
     * @return Lista con todos los medicamentos encontrados
     */
    @Override
    public List<Medicamentos> listarTodos() {
        // Lista donde guardaremos los resultados
        List<Medicamentos> lista = new ArrayList<>();

        // Consulta SQL para obtener todos los registros
        String sql = "SELECT * FROM medicamentos";

        // try-with-resources: cierra automáticamente Statement y ResultSet
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Recorremos cada fila del resultado
            while (rs.next()) {
                // Creamos un objeto Medicamentos por cada fila
                Medicamentos m = new Medicamentos();

                // Mapeamos las columnas de la BD a los atributos del objeto
                m.setIdMedicamento(rs.getInt("id_medicamento"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setStock(rs.getInt("stock"));

                // Agregamos el medicamento a la lista
                lista.add(m);
            }

        } catch (SQLException e) {
            // Si hay error SQL, lo imprimimos para debugging
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Busca un medicamento específico por su ID
     * Ejecuta: SELECT * FROM medicamentos WHERE id_medicamento = ?
     * @param id ID del medicamento a buscar
     * @return Medicamento encontrado o null si no existe
     */
    @Override
    public Medicamentos buscarPorId(int id) {
        // Consulta SQL con parámetro para evitar SQL Injection
        String sql = "SELECT * FROM medicamentos WHERE id_medicamento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos el valor del parámetro (el ID)
            stmt.setInt(1, id);

            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            // Si encontramos un resultado
            if (rs.next()) {
                // Creamos y poblamos el objeto Medicamentos
                Medicamentos m = new Medicamentos();
                m.setIdMedicamento(rs.getInt("id_medicamento"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setStock(rs.getInt("stock"));
                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si no encontramos nada, retornamos null
        return null;
    }

    /**
     * Inserta un nuevo medicamento en la base de datos
     * Ejecuta: INSERT INTO medicamentos (nombre, descripcion, stock) VALUES (?, ?, ?)
     * @param medicamento Objeto con los datos a insertar
     * @return true si se insertó al menos una fila
     */
    @Override
    public boolean guardar(Medicamentos medicamento) {
        // Consulta SQL de inserción con parámetros
        String sql = "INSERT INTO medicamentos (nombre, descripcion, stock) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos los valores de los parámetros
            stmt.setString(1, medicamento.getNombre());
            stmt.setString(2, medicamento.getDescripcion());
            stmt.setInt(3, medicamento.getStock());

            // executeUpdate() retorna el número de filas afectadas
            // Si es mayor a 0, la inserción fue exitosa
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Actualiza un medicamento existente en la base de datos
     * Ejecuta: UPDATE medicamentos SET nombre=?, descripcion=?, stock=? WHERE id_medicamento=?
     * @param medicamento Objeto con los datos actualizados
     * @return true si se actualizó al menos una fila
     */
    @Override
    public boolean actualizar(Medicamentos medicamento) {
        // Consulta SQL de actualización
        String sql = "UPDATE medicamentos SET nombre = ?, descripcion = ?, stock = ? WHERE id_medicamento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos los valores en el orden de los ?
            stmt.setString(1, medicamento.getNombre());
            stmt.setString(2, medicamento.getDescripcion());
            stmt.setInt(3, medicamento.getStock());
            stmt.setInt(4, medicamento.getIdMedicamento()); // WHERE

            // Retornamos true si   se actualizó al menos una fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Elimina un medicamento de la base de datos
     * Ejecuta: DELETE FROM medicamentos WHERE id_medicamento = ?
     * @param id ID del medicamento a eliminar
     * @return true si se eliminó al menos una fila
     */
    @Override
    public boolean eliminar(int id) {
        // Consulta SQL de eliminación
        String sql = "DELETE FROM medicamentos WHERE id_medicamento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos el ID del medicamento a eliminar
            stmt.setInt(1, id);

            // Retornamos true si se eliminó al menos una fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}