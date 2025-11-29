package com.odontologia.services;

// Importamos el modelo Cita
import com.odontologia.models.Cita;
// Importamos el repositorio de citas
import com.odontologia.repositories.CitaRepository;
// Importamos Connection para la base de datos
import java.sql.Connection;
// Importamos List para listas
import java.util.List;

/**
 * CLASE CITA SERVICE JDBC IMPLEMENT
 *
 * PARA QUE SIRVE:
 * - Esta clase IMPLEMENTA la interface CitaService
 * - Contiene la LOGICA DE NEGOCIO para citas
 * - Valida los datos antes de enviarlos al Repository
 * - Actua como intermediario entre Controller y Repository
 *
 * ESTADOS DE UNA CITA:
 * - PROGRAMADA: Cita recien creada
 * - CONFIRMADA: El paciente confirmo asistencia
 * - COMPLETADA: La cita ya se realizo
 * - CANCELADA: La cita fue cancelada
 *
 * FLUJO:
 * Controller -> Service (valida) -> Repository -> BD
 */
public class CitaServiceJdbcImplement implements CitaService {

    // ========== ATRIBUTO ==========

    /**
     * Repositorio de citas
     * Se usa para acceder a la base de datos
     */
    private final CitaRepository citaRepository;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     * Crea internamente el repositorio con esa conexion
     *
     * @param conn Conexion activa a MySQL
     *
     * EJEMPLO:
     * Connection conn = Conexion.getConnection();
     * CitaService service = new CitaServiceJdbcImplement(conn);
     */
    public CitaServiceJdbcImplement(Connection conn) {
        this.citaRepository = new CitaRepository(conn);
    }

    // ========== METODO AGENDAR CITA ==========

    /**
     * Agenda una nueva cita con validaciones
     *
     * VALIDACIONES:
     * 1. La cita no puede ser null
     * 2. Debe tener un ID de paciente valido
     * 3. Debe tener un ID de doctor valido
     * 4. Debe tener una fecha
     *
     * @param cita La cita a agendar
     * @return true si se agendo correctamente, false si fallo validacion
     */
    @Override
    public boolean agendarCita(Cita cita) {
        // Validacion 1: La cita no puede ser null
        // Validacion 2 y 3: Debe tener paciente y doctor validos
        if (cita == null || cita.getIdPaciente() <= 0 || cita.getIdDoctor() <= 0) {
            return false;
        }

        // Validacion 4: Debe tener fecha
        if (cita.getFecha() == null) {
            return false;
        }

        // Si paso todas las validaciones, agendamos
        return citaRepository.agendarCita(cita);
    }

    // ========== METODO LISTAR CITAS POR DOCTOR ==========

    /**
     * Lista todas las citas de un doctor
     *
     * @param idDoctor ID del doctor
     * @return Lista de citas del doctor
     */
    @Override
    public List<Cita> listarCitasPorDoctor(int idDoctor) {
        return citaRepository.buscarPorDoctor(idDoctor);
    }

    // ========== METODO LISTAR CITAS POR PACIENTE ==========

    /**
     * Lista todas las citas de un paciente
     *
     * @param idPaciente ID del paciente
     * @return Lista de citas del paciente
     */
    @Override
    public List<Cita> listarCitasPorPaciente(int idPaciente) {
        return citaRepository.buscarPorPaciente(idPaciente);
    }

    // ========== METODO LISTAR TODAS LAS CITAS ==========

    /**
     * Lista todas las citas del sistema
     *
     * @return Lista con todas las citas
     */
    @Override
    public List<Cita> listarTodasLasCitas() {
        return citaRepository.obtenerTodasLasCitas();
    }

    // ========== METODO CANCELAR CITA ==========

    /**
     * Cancela una cita
     * Internamente cambia el estado a "CANCELADA"
     *
     * @param idCita ID de la cita a cancelar
     * @return true si se cancelo correctamente
     */
    @Override
    public boolean cancelarCita(int idCita) {
        // Llamamos a actualizarEstado con el estado CANCELADA
        return citaRepository.actualizarEstado(idCita, "CANCELADA");
    }

    // ========== METODO ACTUALIZAR ESTADO CITA ==========

    /**
     * Actualiza el estado de una cita
     *
     * @param idCita ID de la cita
     * @param nuevoEstado Nuevo estado (PROGRAMADA, CONFIRMADA, COMPLETADA, CANCELADA)
     * @return true si se actualizo correctamente
     */
    @Override
    public boolean actualizarEstadoCita(int idCita, String nuevoEstado) {
        return citaRepository.actualizarEstado(idCita, nuevoEstado);
    }
}