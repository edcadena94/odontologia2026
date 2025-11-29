package com.odontologia.repositories;

// Importamos el modelo Paciente
import com.odontologia.models.Paciente;

// Importamos las clases de SQL necesarias
import java.sql.*;
// Importamos ArrayList para crear listas
import java.util.ArrayList;
// Importamos Date para manejar fechas
import java.util.Date;
// Importamos List como tipo de retorno
import java.util.List;

/**
 * CLASE PACIENTE REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Esta clase se encarga de comunicarse con la BASE DE DATOS
 * - Realiza todas las operaciones CRUD para la tabla "pacientes"
 * - CRUD = Create (crear), Read (leer), Update (actualizar), Delete (eliminar)
 *
 * PATRON USADO: Repository Pattern
 * - Separa la logica de acceso a datos de la logica de negocio
 */
public class PacienteRepository {

    // ========== ATRIBUTO ==========

    /**
     * Conexion a la base de datos
     * Se recibe en el constructor y se usa en todos los metodos
     */
    private final Connection conn;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     *
     * @param conn Conexion activa a MySQL
     *
     * Ejemplo de uso:
     * Connection conn = Conexion.getConnection();
     * PacienteRepository repo = new PacienteRepository(conn);
     */
    public PacienteRepository(Connection conn) {
        this.conn = conn;
    }

    // ========== METODO GUARDAR (INSERT o UPDATE) ==========

    /**
     * Guarda un paciente en la base de datos
     * Si el paciente NO tiene ID -> hace INSERT (nuevo paciente)
     * Si el paciente SI tiene ID -> hace UPDATE (actualizar paciente)
     *
     * @param paciente El paciente a guardar
     * @return true si se guardo correctamente, false si hubo error
     */
    public boolean guardar(Paciente paciente) {
        String sql;

        // Verificamos si es un INSERT o un UPDATE
        if ((paciente.getIdPaciente() == null) || (paciente.getIdPaciente() == 0)) {
            // Es un NUEVO paciente -> INSERT
            sql = "INSERT INTO pacientes (nombre, apellido, fecha_nacimiento, sexo, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        } else {
            // Es un paciente EXISTENTE -> UPDATE
            sql = "UPDATE pacientes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, sexo = ?, direccion = ?, telefono = ?, email = ? WHERE id_paciente = ?";
        }

        // Ejecutamos la consulta SQL
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos los valores de los parametros (los ?)
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            stmt.setString(4, String.valueOf(paciente.getSexo()));
            stmt.setString(5, paciente.getDireccion());
            stmt.setString(6, paciente.getTelefono());
            stmt.setString(7, paciente.getEmail());

            // Si es UPDATE, agregamos el ID como parametro 8
            if (paciente.getIdPaciente() != null && paciente.getIdPaciente() > 0) {
                stmt.setInt(8, paciente.getIdPaciente());
            }

            // Ejecutamos y verificamos si afecto alguna fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // Si hay error, lo imprimimos y retornamos false
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza un paciente existente
     * Es un alias para guardar() cuando el paciente ya tiene ID
     *
     * @param paciente El paciente a actualizar
     * @return true si se actualizo correctamente
     */
    public boolean actualizar(Paciente paciente) {
        return guardar(paciente);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina un paciente de la base de datos por su ID
     *
     * @param id El ID del paciente a eliminar
     * @return true si se elimino correctamente, false si hubo error
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pacientes WHERE id_paciente = ?";

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
     * Busca un paciente por su ID
     *
     * @param id El ID del paciente a buscar
     * @return El paciente encontrado o null si no existe
     */
    public Paciente buscarPorId(int id) {
        String sql = "SELECT * FROM pacientes WHERE id_paciente = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // Si encontramos un resultado, lo mapeamos a un objeto Paciente
            if (rs.next()) {
                return mapearPaciente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========== METODO OBTENER TODOS ==========

    /**
     * Obtiene todos los pacientes de la base de datos
     * Los ordena por nombre y apellido
     *
     * @return Lista con todos los pacientes
     */
    public List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            // Recorremos todos los resultados y los agregamos a la lista
            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    // ========== METODO BUSCAR POR NOMBRE ==========

    /**
     * Busca pacientes cuyo nombre o apellido contenga el texto buscado
     * Usa LIKE para busqueda parcial
     *
     * @param nombre Texto a buscar en nombre o apellido
     * @return Lista de pacientes que coinciden
     */
    public List<Paciente> buscarPorNombre(String nombre) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE nombre LIKE ? OR apellido LIKE ? ORDER BY nombre, apellido";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Agregamos % para busqueda parcial (contiene)
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

    // ========== METODO BUSCAR POR FECHA NACIMIENTO ==========

    /**
     * Busca pacientes por su fecha de nacimiento exacta
     *
     * @param fecha La fecha de nacimiento a buscar
     * @return Lista de pacientes con esa fecha
     */
    public List<Paciente> buscarPorFechaNacimiento(Date fecha) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE fecha_nacimiento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    // ========== METODO BUSCAR POR SEXO ==========

    /**
     * Busca pacientes por sexo
     *
     * @param sexo 'M' para masculino, 'F' para femenino
     * @return Lista de pacientes con ese sexo
     */
    public List<Paciente> buscarPorSexo(char sexo) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE sexo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(sexo));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pacientes.add(mapearPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    // ========== METODO VERIFICAR EMAIL ==========

    /**
     * Verifica si un email ya existe en la base de datos
     * Util para evitar emails duplicados al registrar
     *
     * @param email El email a verificar
     * @return true si el email ya existe, false si no
     */
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM pacientes WHERE email = ?";

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

    // ========== METODO CONTAR ==========

    /**
     * Cuenta el total de pacientes en la base de datos
     *
     * @return Numero total de pacientes
     */
    public int contar() {
        String sql = "SELECT COUNT(*) FROM pacientes";

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

    // ========== METODO PRIVADO: MAPEAR RESULTSET A PACIENTE ==========

    /**
     * METODO AUXILIAR PRIVADO
     * Convierte una fila del ResultSet a un objeto Paciente
     *
     * @param rs ResultSet posicionado en una fila
     * @return Objeto Paciente con los datos de esa fila
     * @throws SQLException Si hay error al leer los datos
     */
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();

        // Extraemos cada columna y la asignamos al objeto
        paciente.setIdPaciente(rs.getInt("id_paciente"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));
        paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

        // El sexo es un char, lo extraemos del String
        String sexoStr = rs.getString("sexo");
        paciente.setSexo(sexoStr != null ? sexoStr.charAt(0) : ' ');

        paciente.setDireccion(rs.getString("direccion"));
        paciente.setTelefono(rs.getString("telefono"));
        paciente.setEmail(rs.getString("email"));

        return paciente;
    }
}