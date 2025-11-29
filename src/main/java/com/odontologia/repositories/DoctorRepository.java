package com.odontologia.repositories;

// Importamos el modelo Doctor
import com.odontologia.models.Doctor;
// Importamos las clases de SQL
import java.sql.*;
// Importamos ArrayList y List para manejar listas
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE DOCTOR REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Se encarga de comunicarse con la BASE DE DATOS
 * - Realiza todas las operaciones CRUD para la tabla "doctores"
 * - CRUD = Create, Read, Update, Delete
 */
public class DoctorRepository {

    // ========== ATRIBUTO ==========

    /**
     * Conexion a la base de datos
     */
    private final Connection conn;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     *
     * @param conn Conexion activa a MySQL
     */
    public DoctorRepository(Connection conn) {
        this.conn = conn;
    }

    // ========== METODO GUARDAR (INSERT o UPDATE) ==========

    /**
     * Guarda un doctor en la base de datos
     * Si NO tiene ID -> INSERT (nuevo doctor)
     * Si SI tiene ID -> UPDATE (actualizar doctor)
     *
     * @param doctor El doctor a guardar
     * @return true si se guardo correctamente
     */
    public boolean guardar(Doctor doctor) {
        String sql;

        // Verificamos si es INSERT o UPDATE
        if (doctor.getIdDoctor() == null || doctor.getIdDoctor() == 0) {
            // Insertar nuevo doctor
            sql = "INSERT INTO doctores (nombre, apellido, especialidad, telefono, email) VALUES (?, ?, ?, ?, ?)";
        } else {
            // Actualizar doctor existente
            sql = "UPDATE doctores SET nombre = ?, apellido = ?, especialidad = ?, telefono = ?, email = ? WHERE id_doctor = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos los valores de los parametros
            stmt.setString(1, doctor.getNombre());
            stmt.setString(2, doctor.getApellido());
            stmt.setString(3, doctor.getEspecialidad());
            stmt.setString(4, doctor.getTelefono());
            stmt.setString(5, doctor.getEmail());

            // Si es UPDATE, agregamos el ID
            if (doctor.getIdDoctor() != null && doctor.getIdDoctor() > 0) {
                stmt.setInt(6, doctor.getIdDoctor());
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza un doctor existente
     * Es un alias para guardar()
     *
     * @param doctor El doctor a actualizar
     * @return true si se actualizo correctamente
     */
    public boolean actualizar(Doctor doctor) {
        return guardar(doctor);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina un doctor por su ID
     *
     * @param id El ID del doctor a eliminar
     * @return true si se elimino correctamente
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM doctores WHERE id_doctor = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO BUSCAR POR ID ==========

    /**
     * Busca un doctor por su ID
     *
     * @param id El ID del doctor
     * @return El doctor encontrado o null si no existe
     */
    public Doctor buscarPorId(int id) {
        String sql = "SELECT * FROM doctores WHERE id_doctor = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDoctor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========== METODO OBTENER TODOS ==========

    /**
     * Obtiene todos los doctores de la base de datos
     * Ordenados por nombre y apellido
     *
     * @return Lista con todos los doctores
     */
    public List<Doctor> obtenerTodos() {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT * FROM doctores ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                doctores.add(mapearDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctores;
    }

    // ========== METODO BUSCAR POR NOMBRE ==========

    /**
     * Busca doctores por nombre o apellido
     * Usa LIKE para busqueda parcial
     *
     * @param nombre Texto a buscar
     * @return Lista de doctores que coinciden
     */
    public List<Doctor> buscarPorNombre(String nombre) {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT * FROM doctores WHERE nombre LIKE ? OR apellido LIKE ? ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String busqueda = "%" + nombre + "%";
            stmt.setString(1, busqueda);
            stmt.setString(2, busqueda);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                doctores.add(mapearDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctores;
    }

    // ========== METODO BUSCAR POR ESPECIALIDAD ==========

    /**
     * Busca doctores por especialidad exacta
     *
     * @param especialidad La especialidad a buscar
     * @return Lista de doctores con esa especialidad
     */
    public List<Doctor> buscarPorEspecialidad(String especialidad) {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT * FROM doctores WHERE especialidad = ? ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                doctores.add(mapearDoctor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctores;
    }

    // ========== METODO VERIFICAR EMAIL ==========

    /**
     * Verifica si un email ya existe
     *
     * @param email El email a verificar
     * @return true si ya existe
     */
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM doctores WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ========== METODO LISTAR ESPECIALIDADES ==========

    /**
     * Obtiene todas las especialidades unicas de los doctores
     * Util para llenar un dropdown/select
     *
     * @return Lista de especialidades sin repetir
     */
    public List<String> listarEspecialidades() {
        List<String> especialidades = new ArrayList<>();
        String sql = "SELECT DISTINCT especialidad FROM doctores WHERE especialidad IS NOT NULL ORDER BY especialidad";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                especialidades.add(rs.getString("especialidad"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return especialidades;
    }

    // ========== METODO CONTAR ==========

    /**
     * Cuenta el total de doctores
     *
     * @return Numero total de doctores
     */
    public int contar() {
        String sql = "SELECT COUNT(*) FROM doctores";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ========== METODO PRIVADO: MAPEAR RESULTSET A DOCTOR ==========

    /**
     * Convierte una fila del ResultSet a un objeto Doctor
     *
     * @param rs ResultSet posicionado en una fila
     * @return Objeto Doctor con los datos
     */
    private Doctor mapearDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setIdDoctor(rs.getInt("id_doctor"));
        doctor.setNombre(rs.getString("nombre"));
        doctor.setApellido(rs.getString("apellido"));
        doctor.setEspecialidad(rs.getString("especialidad"));
        doctor.setTelefono(rs.getString("telefono"));
        doctor.setEmail(rs.getString("email"));
        return doctor;
    }
}