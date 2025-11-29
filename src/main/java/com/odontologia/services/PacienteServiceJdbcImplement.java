package com.odontologia.services;

// Importamos el modelo Paciente
import com.odontologia.models.Paciente;
// Importamos el repositorio de pacientes
import com.odontologia.repositories.PacienteRepository;

// Importamos Connection para la base de datos
import java.sql.Connection;
// Importamos Date para fechas
import java.util.Date;
// Importamos List para listas
import java.util.List;

/**
 * CLASE PACIENTE SERVICE JDBC IMPLEMENT
 *
 * PARA QUE SIRVE:
 * - Esta clase IMPLEMENTA la interface PacienteService
 * - Contiene la LOGICA DE NEGOCIO para pacientes
 * - Valida los datos antes de enviarlos al Repository
 * - Actua como intermediario entre Controller y Repository
 *
 * POR QUE VALIDAR AQUI:
 * - El Controller solo recibe datos del usuario
 * - El Repository solo ejecuta SQL
 * - El Service valida que los datos sean correctos
 *
 * FLUJO:
 * Controller -> Service (valida) -> Repository -> BD
 */
public class PacienteServiceJdbcImplement implements PacienteService {

    // ========== ATRIBUTO ==========

    /**
     * Repositorio de pacientes
     * Se usa para acceder a la base de datos
     */
    private final PacienteRepository pacienteRepository;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     * Crea internamente el repositorio con esa conexion
     *
     * @param conn Conexion activa a MySQL
     *
     * EJEMPLO:
     * Connection conn = Conexion.getConnection();
     * PacienteService service = new PacienteServiceJdbcImplement(conn);
     */
    public PacienteServiceJdbcImplement(Connection conn) {
        this.pacienteRepository = new PacienteRepository(conn);
    }

    // ========== METODO GUARDAR ==========

    /**
     * Guarda un nuevo paciente con validaciones
     *
     * VALIDACIONES:
     * 1. El paciente no puede ser null
     * 2. El nombre es obligatorio
     * 3. El apellido es obligatorio
     * 4. La fecha de nacimiento es obligatoria
     * 5. Si tiene email, verifica que no exista ya
     *
     * @param paciente El paciente a guardar
     * @return true si se guardo correctamente, false si fallo validacion
     */
    @Override
    public boolean guardar(Paciente paciente) {
        // Validacion 1: El paciente no puede ser null
        if (paciente == null || paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            return false;
        }

        // Validacion 2: El apellido es obligatorio
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
            return false;
        }

        // Validacion 3: La fecha de nacimiento es obligatoria
        if (paciente.getFechaNacimiento() == null) {
            return false;
        }

        // Validacion 4: Si tiene email, verificar que no exista
        if (paciente.getEmail() != null && !paciente.getEmail().trim().isEmpty()) {
            if (existeEmail(paciente.getEmail())) {
                return false; // Email ya registrado
            }
        }

        // Si paso todas las validaciones, guardamos
        return pacienteRepository.guardar(paciente);
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza un paciente existente con validaciones
     *
     * VALIDACIONES:
     * 1. El paciente debe tener un ID valido
     * 2. El nombre es obligatorio
     * 3. El apellido es obligatorio
     * 4. La fecha de nacimiento es obligatoria
     *
     * @param paciente El paciente a actualizar
     * @return true si se actualizo correctamente
     */
    @Override
    public boolean actualizar(Paciente paciente) {
        // Validacion: Debe tener un ID valido para actualizar
        if (paciente == null || paciente.getIdPaciente() == null || paciente.getIdPaciente() <= 0) {
            return false;
        }

        // Validacion: Nombre obligatorio
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            return false;
        }

        // Validacion: Apellido obligatorio
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
            return false;
        }

        // Validacion: Fecha de nacimiento obligatoria
        if (paciente.getFechaNacimiento() == null) {
            return false;
        }

        // Si paso todas las validaciones, actualizamos
        return pacienteRepository.actualizar(paciente);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina un paciente por su ID
     *
     * VALIDACION: El ID debe ser mayor a 0
     *
     * @param idPaciente ID del paciente a eliminar
     * @return true si se elimino correctamente
     */
    @Override
    public boolean eliminar(int idPaciente) {
        // Validacion: ID debe ser positivo
        if (idPaciente <= 0) {
            return false;
        }
        return pacienteRepository.eliminar(idPaciente);
    }

    // ========== METODO BUSCAR POR ID ==========

    /**
     * Busca un paciente por su ID
     *
     * @param idPaciente ID del paciente a buscar
     * @return El paciente encontrado o null
     */
    @Override
    public Paciente buscarPorId(int idPaciente) {
        // Validacion: ID debe ser positivo
        if (idPaciente <= 0) {
            return null;
        }
        return pacienteRepository.buscarPorId(idPaciente);
    }

    // ========== METODO LISTAR TODOS ==========

    /**
     * Obtiene todos los pacientes
     *
     * @return Lista con todos los pacientes
     */
    @Override
    public List<Paciente> listarTodos() {
        return pacienteRepository.obtenerTodos();
    }

    // ========== METODO BUSCAR POR NOMBRE ==========

    /**
     * Busca pacientes por nombre o apellido
     *
     * @param nombre Texto a buscar
     * @return Lista de pacientes que coinciden
     */
    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        // Validacion: El nombre no puede estar vacio
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of(); // Retorna lista vacia
        }
        return pacienteRepository.buscarPorNombre(nombre.trim());
    }

    // ========== METODO BUSCAR POR FECHA NACIMIENTO ==========

    /**
     * Busca pacientes por fecha de nacimiento
     *
     * @param fechaNacimiento Fecha a buscar
     * @return Lista de pacientes nacidos en esa fecha
     */
    @Override
    public List<Paciente> buscarPorFechaNacimiento(Date fechaNacimiento) {
        // Validacion: La fecha no puede ser null
        if (fechaNacimiento == null) {
            return List.of(); // Retorna lista vacia
        }
        return pacienteRepository.buscarPorFechaNacimiento(fechaNacimiento);
    }

    // ========== METODO BUSCAR POR SEXO ==========

    /**
     * Busca pacientes por sexo
     *
     * @param sexo 'M' o 'F'
     * @return Lista de pacientes con ese sexo
     */
    @Override
    public List<Paciente> buscarPorSexo(char sexo) {
        // Validacion: Solo acepta 'M' o 'F'
        if (sexo != 'M' && sexo != 'F') {
            return List.of(); // Retorna lista vacia
        }
        return pacienteRepository.buscarPorSexo(sexo);
    }

    // ========== METODO EXISTE EMAIL ==========

    /**
     * Verifica si un email ya existe
     *
     * @param email Email a verificar
     * @return true si ya existe
     */
    @Override
    public boolean existeEmail(String email) {
        // Validacion: El email no puede estar vacio
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return pacienteRepository.existeEmail(email.trim());
    }

    // ========== METODO CONTAR PACIENTES ==========

    /**
     * Cuenta el total de pacientes
     *
     * @return Numero total de pacientes
     */
    @Override
    public int contarPacientes() {
        return pacienteRepository.contar();
    }
}