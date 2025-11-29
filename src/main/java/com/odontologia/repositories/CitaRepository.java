package com.odontologia.repositories;

// Importamos el modelo Cita
import com.odontologia.models.Cita;
// Importamos las clases de SQL
import java.sql.*;
// Importamos LocalDate y LocalTime para fechas y horas
import java.time.LocalDate;
import java.time.LocalTime;
// Importamos ArrayList y List
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE CITA REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Se encarga de comunicarse con la BASE DE DATOS
 * - Realiza todas las operaciones CRUD para la tabla "citas"
 * - Maneja fechas y horas de las citas
 */
public class CitaRepository {

    // ========== ATRIBUTO ==========

    /**
     * Conexion a la base de datos
     */
    private final Connection conn;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion
     *
     * @param conn Conexion activa a MySQL
     */
    public CitaRepository(Connection conn) {
        this.conn = conn;
    }

    // ========== METODO AGENDAR CITA (INSERT o UPDATE) ==========

    /**
     * Agenda una nueva cita o actualiza una existente
     * Si NO tiene ID -> INSERT (nueva cita)
     * Si SI tiene ID -> UPDATE (actualizar cita)
     *
     * @param cita La cita a guardar
     * @return true si se guardo correctamente
     */
    public boolean agendarCita(Cita cita) {
        String sql;

        if (cita.getId() == null || cita.getId() == 0) {
            // Insertar nueva cita
            sql = "INSERT INTO citas (id_paciente, id_doctor, fecha, hora, estado, motivo) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            // Actualizar cita existente
            sql = "UPDATE citas SET id_paciente = ?, id_doctor = ?, fecha = ?, hora = ?, estado = ?, motivo = ? WHERE id_cita = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getIdPaciente());
            stmt.setInt(2, cita.getIdDoctor());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));

            // Manejar hora (puede ser null)
            if (cita.getHora() != null) {
                stmt.setTime(4, Time.valueOf(cita.getHora()));
            } else {
                stmt.setTime(4, null);
            }

            // Si no tiene estado, ponemos PROGRAMADA por defecto
            stmt.setString(5, cita.getEstado() != null ? cita.getEstado() : "PROGRAMADA");
            stmt.setString(6, cita.getMotivo());

            // Si es UPDATE, agregamos el ID
            if (cita.getId() != null && cita.getId() > 0) {
                stmt.setInt(7, cita.getId());
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza una cita existente
     *
     * @param cita La cita a actualizar
     * @return true si se actualizo correctamente
     */
    public boolean actualizar(Cita cita) {
        return agendarCita(cita);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina una cita por su ID
     *
     * @param id El ID de la cita a eliminar
     * @return true si se elimino correctamente
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id_cita = ?";

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
     * Busca una cita por su ID
     *
     * @param id El ID de la cita
     * @return La cita encontrada o null
     */
    public Cita buscarPorId(int id) {
        String sql = "SELECT * FROM citas WHERE id_cita = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearCita(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========== METODO BUSCAR POR DOCTOR ==========

    /**
     * Busca todas las citas de un doctor
     *
     * @param idDoctor El ID del doctor
     * @return Lista de citas del doctor
     */
    public List<Cita> buscarPorDoctor(int idDoctor) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE id_doctor = ? ORDER BY fecha, hora";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDoctor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // ========== METODO BUSCAR POR PACIENTE ==========

    /**
     * Busca todas las citas de un paciente
     *
     * @param idPaciente El ID del paciente
     * @return Lista de citas del paciente
     */
    public List<Cita> buscarPorPaciente(int idPaciente) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE id_paciente = ? ORDER BY fecha, hora";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // ========== METODO OBTENER TODAS LAS CITAS ==========

    /**
     * Obtiene todas las citas de la base de datos
     *
     * @return Lista con todas las citas
     */
    public List<Cita> obtenerTodasLasCitas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas ORDER BY fecha, hora";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // ========== METODO ACTUALIZAR ESTADO ==========

    /**
     * Actualiza solo el estado de una cita
     * Estados: PROGRAMADA, CONFIRMADA, COMPLETADA, CANCELADA
     *
     * @param idCita El ID de la cita
     * @param nuevoEstado El nuevo estado
     * @return true si se actualizo correctamente
     */
    public boolean actualizarEstado(int idCita, String nuevoEstado) {
        String sql = "UPDATE citas SET estado = ? WHERE id_cita = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idCita);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO BUSCAR POR FECHA ==========

    /**
     * Busca citas por fecha
     *
     * @param fecha La fecha a buscar
     * @return Lista de citas en esa fecha
     */
    public List<Cita> buscarPorFecha(LocalDate fecha) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE DATE(fecha) = ? ORDER BY hora";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // ========== METODO BUSCAR POR ESTADO ==========

    /**
     * Busca citas por estado
     *
     * @param estado El estado a buscar
     * @return Lista de citas con ese estado
     */
    public List<Cita> buscarPorEstado(String estado) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE estado = ? ORDER BY fecha, hora";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                citas.add(mapearCita(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // ========== METODO VERIFICAR DISPONIBILIDAD ==========

    /**
     * Verifica si un doctor esta disponible en una fecha y hora
     *
     * @param idDoctor El ID del doctor
     * @param fecha La fecha a verificar
     * @param hora La hora a verificar
     * @return true si esta disponible, false si ya tiene cita
     */
    public boolean verificarDisponibilidad(int idDoctor, LocalDate fecha, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM citas WHERE id_doctor = ? AND fecha = ? AND hora = ? AND estado != 'CANCELADA'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDoctor);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setTime(3, Time.valueOf(hora));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Si COUNT es 0, esta disponible
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ========== METODO CONTAR ==========

    /**
     * Cuenta el total de citas
     *
     * @return Numero total de citas
     */
    public int contar() {
        String sql = "SELECT COUNT(*) FROM citas";

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

    // ========== METODO LISTAR CON NOMBRES (JOIN) ==========

    /**
     * Obtiene todas las citas CON los nombres de paciente y doctor
     * Usa JOIN para traer datos de las 3 tablas
     *
     * @return Lista de citas con nombres incluidos
     */
    public List<Cita> listarConNombres() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, " +
                "p.nombre as nombre_paciente, p.apellido as apellido_paciente, " +
                "d.nombre as nombre_doctor, d.apellido as apellido_doctor, d.especialidad " +
                "FROM citas c " +
                "LEFT JOIN pacientes p ON c.id_paciente = p.id_paciente " +
                "LEFT JOIN doctores d ON c.id_doctor = d.id_doctor " +
                "ORDER BY c.fecha, c.hora";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cita cita = mapearCita(rs);

                // Agregar nombres del JOIN
                cita.setNombrePaciente(rs.getString("nombre_paciente"));
                cita.setApellidoPaciente(rs.getString("apellido_paciente"));
                cita.setNombreDoctor(rs.getString("nombre_doctor"));
                cita.setApellidoDoctor(rs.getString("apellido_doctor"));
                cita.setEspecialidadDoctor(rs.getString("especialidad"));

                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    // ========== METODO PRIVADO: MAPEAR RESULTSET A CITA ==========

    /**
     * Convierte una fila del ResultSet a un objeto Cita
     *
     * @param rs ResultSet posicionado en una fila
     * @return Objeto Cita con los datos
     */
    private Cita mapearCita(ResultSet rs) throws SQLException {
        Cita cita = new Cita();

        cita.setId(rs.getInt("id_cita"));
        cita.setIdPaciente(rs.getInt("id_paciente"));
        cita.setIdDoctor(rs.getInt("id_doctor"));
        cita.setFecha(rs.getDate("fecha").toLocalDate());
        cita.setEstado(rs.getString("estado"));
        cita.setMotivo(rs.getString("motivo"));

        // Manejar hora (puede ser null)
        Time hora = rs.getTime("hora");
        if (hora != null) {
            cita.setHora(hora.toLocalTime());
        }

        return cita;
    }
}