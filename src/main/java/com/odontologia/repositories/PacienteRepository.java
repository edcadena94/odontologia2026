package com.odontologia.repositories;

import com.odontologia.models.Paciente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE PACIENTE REPOSITORY
 * 
 * CAMBIO IMPORTANTE: La cédula es ahora la clave primaria (no auto-increment)
 * - ANTES: id_paciente INT AUTO_INCREMENT
 * - AHORA: cedula VARCHAR(10) PRIMARY KEY
 */
public class PacienteRepository {

    private final Connection conn;

    public PacienteRepository(Connection conn) {
        this.conn = conn;
    }


    // ========== MÉTODO GUARDAR ==========

    /**
     * Guarda un paciente en la base de datos
     * 
     * CAMBIO: Ahora usa INSERT con cédula como PK
     * Ya NO se usa UPDATE porque la cédula no cambia
     */
    public boolean guardar(Paciente paciente) {
        // SQL actualizado con los nuevos campos
        String sql = "INSERT INTO paciente (cedula, nombre, apellido, fecha_nacimiento, " +
                     "genero, telefono, email, direccion, alergias, antecedentes_medicos, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getCedula());
            stmt.setString(2, paciente.getNombre());
            stmt.setString(3, paciente.getApellido());
            
            // fecha_nacimiento puede ser NULL
            if (paciente.getFechaNacimiento() != null) {
                stmt.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            
            stmt.setString(5, paciente.getGenero());
            stmt.setString(6, paciente.getTelefono());
            stmt.setString(7, paciente.getEmail());
            stmt.setString(8, paciente.getDireccion());
            stmt.setString(9, paciente.getAlergias());
            stmt.setString(10, paciente.getAntecedentesMedicos());
            stmt.setString(11, paciente.getEstado() != null ? paciente.getEstado() : "ACTIVO");

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ========== MÉTODO ACTUALIZAR ==========

    /**
     * Actualiza un paciente existente
     * La cédula NO se actualiza (es la PK)
     */
    public boolean actualizar(Paciente paciente) {
        String sql = "UPDATE paciente SET nombre = ?, apellido = ?, fecha_nacimiento = ?, " +
                     "genero = ?, telefono = ?, email = ?, direccion = ?, " +
                     "alergias = ?, antecedentes_medicos = ?, estado = ? " +
                     "WHERE cedula = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            
            if (paciente.getFechaNacimiento() != null) {
                stmt.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
            } else {
                stmt.setNull(3, Types.DATE);
            }
            
            stmt.setString(4, paciente.getGenero());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getEmail());
            stmt.setString(7, paciente.getDireccion());
            stmt.setString(8, paciente.getAlergias());
            stmt.setString(9, paciente.getAntecedentesMedicos());
            stmt.setString(10, paciente.getEstado() != null ? paciente.getEstado() : "ACTIVO");
            stmt.setString(11, paciente.getCedula()); // WHERE cedula = ?

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ========== MÉTODO ELIMINAR ==========

    /**
     * Elimina un paciente por su cédula
     * CAMBIO: Antes era por ID (int), ahora por cédula (String)
     */
    public boolean eliminar(String cedula) {
        String sql = "DELETE FROM paciente WHERE cedula = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // ========== MÉTODO BUSCAR POR CÉDULA ==========

    /**
     * Busca un paciente por su cédula (PK)
     * CAMBIO: Antes era buscarPorId(int), ahora es buscarPorCedula(String)
     */
    public Paciente buscarPorCedula(String cedula) {
        String sql = "SELECT * FROM paciente WHERE cedula = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearPaciente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ALIAS para compatibilidad con código anterior
     * Antes: buscarPorId(int)
     * Ahora: buscarPorId(String) llama a buscarPorCedula
     */
    @Deprecated
    public Paciente buscarPorId(int id) {
        // Ya no se usa, pero mantenemos para compatibilidad
        return null;
    }

    public Paciente buscarPorId(String cedula) {
        return buscarPorCedula(cedula);
    }


    // ========== MÉTODO OBTENER TODOS ==========

    /**
     * Obtiene todos los pacientes
     */
    public List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE estado = 'ACTIVO' ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }


    // ========== MÉTODO BUSCAR POR NOMBRE ==========

    /**
     * Busca pacientes por nombre o apellido
     */
    public List<Paciente> buscarPorNombre(String nombre) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE (nombre LIKE ? OR apellido LIKE ?) " +
                     "AND estado = 'ACTIVO' ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String busqueda = "%" + nombre + "%";
            stmt.setString(1, busqueda);
            stmt.setString(2, busqueda);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }


    // ========== MÉTODO VERIFICAR SI EXISTE CÉDULA ==========

    /**
     * Verifica si una cédula ya existe en la base de datos
     * Útil para evitar duplicados al registrar
     */
    public boolean existeCedula(String cedula) {
        String sql = "SELECT COUNT(*) FROM paciente WHERE cedula = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // ========== MÉTODO VERIFICAR EMAIL ==========

    /**
     * Verifica si un email ya existe
     */
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM paciente WHERE email = ?";

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


    // ========== MÉTODO CONTAR ==========

    /**
     * Cuenta el total de pacientes activos
     */
    public int contar() {
        String sql = "SELECT COUNT(*) FROM paciente WHERE estado = 'ACTIVO'";

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


    // ========== MÉTODO PRIVADO: MAPEAR ==========

    /**
     * Convierte ResultSet a objeto Paciente
     * CAMBIO: Ahora mapea los nuevos campos
     */
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setCedula(rs.getString("cedula"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));
        
        // Convertir java.sql.Date a LocalDate
        Date fechaNac = rs.getDate("fecha_nacimiento");
        if (fechaNac != null) {
            paciente.setFechaNacimiento(fechaNac.toLocalDate());
        }
        
        paciente.setGenero(rs.getString("genero"));
        paciente.setTelefono(rs.getString("telefono"));
        paciente.setEmail(rs.getString("email"));
        paciente.setDireccion(rs.getString("direccion"));
        paciente.setAlergias(rs.getString("alergias"));
        paciente.setAntecedentesMedicos(rs.getString("antecedentes_medicos"));
        paciente.setEstado(rs.getString("estado"));
        
        // fecha_registro
        Timestamp fechaReg = rs.getTimestamp("fecha_registro");
        if (fechaReg != null) {
            paciente.setFechaRegistro(fechaReg.toLocalDateTime());
        }

        return paciente;
    }
}
