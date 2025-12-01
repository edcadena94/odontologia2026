package com.odontologia.repositories;

import com.odontologia.models.Medicamentos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE MEDICAMENTOS REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Esta clase se encarga de comunicarse con la BASE DE DATOS
 * - Realiza todas las operaciones CRUD para la tabla "medicamentos"
 * - CRUD = Create (crear), Read (leer), Update (actualizar), Delete (eliminar)
 *
 * COMO SE USA:
 * 1. Se crea una instancia pasando la conexion a la base de datos
 * 2. Se llaman los metodos para guardar, buscar, eliminar medicamentos
 */
public class MedicamentosRepository {

    // ========== ATRIBUTO ==========

    /**
     * Conexion a la base de datos MySQL
     */
    private final Connection conn;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     * @param conn Conexion activa a MySQL
     */
    public MedicamentosRepository(Connection conn) {
        this.conn = conn;
    }

    // ========== METODO GUARDAR ==========

    /**
     * Guarda un medicamento en la base de datos
     * Si el ID es 0 -> INSERT (nuevo)
     * Si el ID existe -> UPDATE (actualizar)
     *
     * @param medicamento El medicamento a guardar
     * @return true si se guardo correctamente
     */
    public boolean guardar(Medicamentos medicamento) {
        String sql;

        // Verificamos si es nuevo o existente
        boolean esNuevo = medicamento.getIdMedicamento() == 0;

        if (esNuevo) {
            sql = "INSERT INTO medicamentos (nombre, descripcion, stock) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE medicamentos SET nombre = ?, descripcion = ?, stock = ? WHERE id_medicamento = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Llenamos los parametros
            stmt.setString(1, medicamento.getNombre());
            stmt.setString(2, medicamento.getDescripcion());
            stmt.setLong(3, medicamento.getStock()); // CAMBIADO: setLong en vez de setInt

            // Si es UPDATE, agregamos el ID
            if (!esNuevo) {
                stmt.setLong(4, medicamento.getIdMedicamento()); // CAMBIADO: setLong
            }

            // Ejecutamos
            int affectedRows = stmt.executeUpdate();

            // Si es INSERT, obtenemos el ID generado
            if (esNuevo && affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    medicamento.setIdMedicamento(rs.getLong(1)); // CAMBIADO: getLong
                }
            }

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza un medicamento existente
     * @param medicamento El medicamento a actualizar
     * @return true si se actualizo correctamente
     */
    public boolean actualizar(Medicamentos medicamento) {
        return guardar(medicamento);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina un medicamento por su ID
     * @param id El ID del medicamento a eliminar
     * @return true si se elimino correctamente
     */
    public boolean eliminar(long id) { // CAMBIADO: long en vez de int
        String sql = "DELETE FROM medicamentos WHERE id_medicamento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id); // CAMBIADO: setLong
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO BUSCAR POR ID ==========

    /**
     * Busca un medicamento por su ID
     * @param id El ID del medicamento
     * @return El medicamento encontrado o null
     */
    public Medicamentos buscarPorId(long id) { // CAMBIADO: long en vez de int
        String sql = "SELECT * FROM medicamentos WHERE id_medicamento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id); // CAMBIADO: setLong
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearMedicamento(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ========== METODO OBTENER TODOS ==========

    /**
     * Obtiene todos los medicamentos ordenados por nombre
     * @return Lista de medicamentos
     */
    public List<Medicamentos> obtenerTodos() {
        List<Medicamentos> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos ORDER BY nombre ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                medicamentos.add(mapearMedicamento(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicamentos;
    }

    // ========== METODO PRIVADO: MAPEAR ==========

    /**
     * Convierte una fila del ResultSet a un objeto Medicamentos
     * @param rs ResultSet posicionado en una fila
     * @return Objeto Medicamentos con los datos
     */
    private Medicamentos mapearMedicamento(ResultSet rs) throws SQLException {
        Medicamentos medicamento = new Medicamentos();

        medicamento.setIdMedicamento(rs.getLong("id_medicamento")); // CAMBIADO: getLong
        medicamento.setNombre(rs.getString("nombre"));
        medicamento.setDescripcion(rs.getString("descripcion"));
        medicamento.setStock(rs.getLong("stock")); // CAMBIADO: getLong

        return medicamento;
    }
}