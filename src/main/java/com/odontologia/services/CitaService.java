package com.odontologia.services;

// Importamos el modelo Cita
import com.odontologia.models.Cita;
// Importamos List para listas
import java.util.List;

/**
 * INTERFACE CITA SERVICE
 *
 * PARA QUE SIRVE:
 * - Define los metodos que debe tener el servicio de citas
 * - Es una INTERFACE, solo declara metodos, no los implementa
 * - La implementacion real esta en CitaServiceJdbcImplement.java
 *
 * OPERACIONES QUE MANEJA:
 * - Agendar nuevas citas
 * - Listar citas por doctor o paciente
 * - Cancelar citas
 * - Actualizar estado de citas
 *
 * FLUJO DE LA APLICACION:
 * Controller -> Service -> Repository -> Base de Datos
 */
public interface CitaService {

    // ========== METODO AGENDAR CITA ==========

    /**
     * Agenda una nueva cita en el sistema
     *
     * @param cita Objeto Cita con los datos de la cita
     * @return true si se agendo correctamente, false si hubo error
     *
     * EJEMPLO:
     * Cita nuevaCita = new Cita();
     * nuevaCita.setIdPaciente(1);
     * nuevaCita.setIdDoctor(2);
     * nuevaCita.setFecha(LocalDate.now());
     * nuevaCita.setHora(LocalTime.of(10, 30));
     * boolean agendada = citaService.agendarCita(nuevaCita);
     */
    boolean agendarCita(Cita cita);

    // ========== METODOS DE CONSULTA ==========

    /**
     * Lista todas las citas de un doctor especifico
     *
     * @param idDoctor ID del doctor
     * @return Lista de citas del doctor
     *
     * EJEMPLO:
     * List<Cita> citasDoctor = citaService.listarCitasPorDoctor(1);
     */
    List<Cita> listarCitasPorDoctor(int idDoctor);

    /**
     * Lista todas las citas de un paciente especifico
     *
     * @param idPaciente ID del paciente
     * @return Lista de citas del paciente
     *
     * EJEMPLO:
     * List<Cita> citasPaciente = citaService.listarCitasPorPaciente(5);
     */
    List<Cita> listarCitasPorPaciente(int idPaciente);

    /**
     * Lista todas las citas del sistema
     *
     * @return Lista con todas las citas
     *
     * EJEMPLO:
     * List<Cita> todasLasCitas = citaService.listarTodasLasCitas();
     */
    List<Cita> listarTodasLasCitas();

    // ========== METODOS DE ACTUALIZACION ==========

    /**
     * Cancela una cita por su ID
     * Cambia el estado de la cita a "CANCELADA"
     *
     * @param idCita ID de la cita a cancelar
     * @return true si se cancelo correctamente, false si hubo error
     *
     * EJEMPLO:
     * boolean cancelada = citaService.cancelarCita(10);
     * if (cancelada) {
     *     System.out.println("Cita cancelada exitosamente");
     * }
     */
    boolean cancelarCita(int idCita);

    /**
     * Actualiza el estado de una cita
     * Estados posibles: PROGRAMADA, CONFIRMADA, COMPLETADA, CANCELADA
     *
     * @param idCita ID de la cita
     * @param nuevoEstado Nuevo estado a asignar
     * @return true si se actualizo correctamente, false si hubo error
     *
     * EJEMPLO:
     * // Confirmar una cita
     * citaService.actualizarEstadoCita(10, "CONFIRMADA");
     *
     * // Marcar como completada
     * citaService.actualizarEstadoCita(10, "COMPLETADA");
     */
    boolean actualizarEstadoCita(int idCita, String nuevoEstado);
}