package com.odontologia.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * MODELO CITA
 * Esta clase representa una cita médica entre un paciente y un doctor
 * Cada objeto = 1 fila en la tabla "citas"
 *
 * IMPORTANTE: Esta clase usa LocalDate y LocalTime (Java 8+)
 * - LocalDate: Para fechas (año-mes-día)
 * - LocalTime: Para horas (hora:minuto:segundo)
 */
public class Cita {

    // ========== ATRIBUTOS PRINCIPALES ==========

    /**
     * ID único de la cita (clave primaria)
     */
    private Integer id;

    /**
     * ID del paciente que tiene la cita
     * Se relaciona con la tabla "pacientes"
     */
    private Integer idPaciente;

    /**
     * ID del doctor que atenderá la cita
     * Se relaciona con la tabla "doctores"
     */
    private Integer idDoctor;

    /**
     * Fecha de la cita
     * Ejemplo: 2025-11-24 (año-mes-día)
     */
    private LocalDate fecha;

    /**
     * Hora de la cita
     * Ejemplo: 14:30:00 (hora:minuto:segundo)
     */
    private LocalTime hora;

    /**
     * Motivo de la cita
     * Ejemplo: "Limpieza dental", "Consulta general", "Revisión de ortodoncia"
     */
    private String motivo;

    /**
     * Estado de la cita
     * Valores posibles: "PROGRAMADA", "CONFIRMADA", "COMPLETADA", "CANCELADA"
     */
    private String estado;


    // ========== ATRIBUTOS ADICIONALES (Para mostrar información completa) ==========
    // Estos NO están en la tabla "citas" pero son útiles para mostrar datos

    /**
     * Nombre del paciente
     * Se obtiene haciendo JOIN con la tabla "pacientes"
     */
    private String nombrePaciente;

    /**
     * Apellido del paciente
     */
    private String apellidoPaciente;

    /**
     * Nombre del doctor
     * Se obtiene haciendo JOIN con la tabla "doctores"
     */
    private String nombreDoctor;

    /**
     * Apellido del doctor
     */
    private String apellidoDoctor;

    /**
     * Especialidad del doctor
     */
    private String especialidadDoctor;


    // ========== CONSTRUCTORES ==========

    /**
     * CONSTRUCTOR VACÍO
     * Se usa cuando creamos un objeto sin datos
     */
    public Cita() {
    }

    /**
     * CONSTRUCTOR BÁSICO (Sin ID)
     * Se usa cuando creamos una nueva cita
     * El estado se establece automáticamente como "PROGRAMADA"
     *
     * @param idPaciente ID del paciente
     * @param idDoctor ID del doctor
     * @param fecha Fecha de la cita
     * @param hora Hora de la cita
     * @param motivo Motivo de la consulta
     *
     * Ejemplo:
     * Cita c = new Cita(1, 2, LocalDate.of(2025, 11, 24), LocalTime.of(14, 30), "Limpieza dental");
     */
    public Cita(Integer idPaciente, Integer idDoctor, LocalDate fecha, LocalTime hora, String motivo) {
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = "PROGRAMADA"; // Estado por defecto
    }

    /**
     * CONSTRUCTOR COMPLETO (Con ID y Estado)
     * Se usa cuando recuperamos una cita de la base de datos
     *
     * @param id ID de la cita
     * @param idPaciente ID del paciente
     * @param idDoctor ID del doctor
     * @param fecha Fecha de la cita
     * @param hora Hora de la cita
     * @param motivo Motivo de la consulta
     * @param estado Estado de la cita
     */
    public Cita(Integer id, Integer idPaciente, Integer idDoctor, LocalDate fecha,
                LocalTime hora, String motivo, String estado) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
    }


    // ========== GETTERS ==========

    public Integer getId() {
        return id;
    }

    /**
     * Alias para getId() - para compatibilidad
     */
    public Integer getIdCita() {
        return id;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getEstado() {
        return estado;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public String getApellidoDoctor() {
        return apellidoDoctor;
    }

    public String getEspecialidadDoctor() {
        return especialidadDoctor;
    }


    // ========== SETTERS ==========

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public void setApellidoDoctor(String apellidoDoctor) {
        this.apellidoDoctor = apellidoDoctor;
    }

    public void setEspecialidadDoctor(String especialidadDoctor) {
        this.especialidadDoctor = especialidadDoctor;
    }


    // ========== MÉTODOS DE UTILIDAD ==========

    /**
     * Obtiene el nombre completo del paciente
     * @return "Juan Pérez"
     */
    public String getNombreCompletoPaciente() {
        if (nombrePaciente != null && apellidoPaciente != null) {
            return nombrePaciente + " " + apellidoPaciente;
        }
        return "";
    }

    /**
     * Obtiene el nombre completo del doctor con título
     * @return "Dr. Carlos Martínez"
     */
    public String getNombreCompletoDoctor() {
        if (nombreDoctor != null && apellidoDoctor != null) {
            return "Dr. " + nombreDoctor + " " + apellidoDoctor;
        }
        return "";
    }

    /**
     * Obtiene el nombre del doctor con especialidad
     * @return "Dr. Carlos Martínez - Ortodoncia"
     */
    public String getNombreDoctorConEspecialidad() {
        String nombreCompleto = getNombreCompletoDoctor();
        if (especialidadDoctor != null && !especialidadDoctor.trim().isEmpty()) {
            nombreCompleto += " - " + especialidadDoctor;
        }
        return nombreCompleto;
    }

    /**
     * Obtiene la fecha en formato legible
     * @return "24/11/2025"
     */
    public String getFechaFormateada() {
        if (fecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fecha.format(formatter);
        }
        return "";
    }

    /**
     * Obtiene la hora en formato legible
     * @return "14:30"
     */
    public String getHoraFormateada() {
        if (hora != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return hora.format(formatter);
        }
        return "";
    }

    /**
     * Obtiene fecha y hora juntas en formato legible
     * @return "24/11/2025 - 14:30"
     */
    public String getFechaHoraFormateada() {
        return getFechaFormateada() + " - " + getHoraFormateada();
    }


    // ========== MÉTODOS DE VALIDACIÓN DE ESTADO ==========

    /**
     * Verifica si la cita está programada
     * @return true si el estado es "PROGRAMADA"
     */
    public boolean estaProgramada() {
        return "PROGRAMADA".equalsIgnoreCase(estado);
    }

    /**
     * Verifica si la cita está confirmada
     * @return true si el estado es "CONFIRMADA"
     */
    public boolean estaConfirmada() {
        return "CONFIRMADA".equalsIgnoreCase(estado);
    }

    /**
     * Verifica si la cita está completada
     * @return true si el estado es "COMPLETADA"
     */
    public boolean estaCompletada() {
        return "COMPLETADA".equalsIgnoreCase(estado);
    }

    /**
     * Verifica si la cita está cancelada
     * @return true si el estado es "CANCELADA"
     */
    public boolean estaCancelada() {
        return "CANCELADA".equalsIgnoreCase(estado);
    }

    /**
     * Verifica si la cita se puede modificar
     * Solo se puede modificar si está PROGRAMADA o CONFIRMADA
     * @return true si se puede modificar
     */
    public boolean sePuedeModificar() {
        return estaProgramada() || estaConfirmada();
    }

    /**
     * Verifica si la cita se puede cancelar
     * Solo se puede cancelar si NO está completada ni ya cancelada
     * @return true si se puede cancelar
     */
    public boolean sePuedeCancelar() {
        return !estaCompletada() && !estaCancelada();
    }

    /**
     * Valida que la cita tenga los datos mínimos requeridos
     * @return true si tiene paciente, doctor, fecha y hora
     */
    public boolean esValida() {
        return idPaciente != null && idPaciente > 0 &&
                idDoctor != null && idDoctor > 0 &&
                fecha != null &&
                hora != null;
    }


    // ========== MÉTODOS OVERRIDE ==========

    /**
     * Representación en texto del objeto
     * Útil para debugging
     */
    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", idPaciente=" + idPaciente +
                ", idDoctor=" + idDoctor +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", motivo='" + motivo + '\'' +
                ", estado='" + estado + '\'' +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", apellidoPaciente='" + apellidoPaciente + '\'' +
                ", nombreDoctor='" + nombreDoctor + '\'' +
                ", apellidoDoctor='" + apellidoDoctor + '\'' +
                ", especialidadDoctor='" + especialidadDoctor + '\'' +
                '}';
    }

    /**
     * Compara si dos citas son iguales
     * Dos citas son iguales si tienen el mismo ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cita cita = (Cita) obj;
        return id != null && id.equals(cita.id);
    }

    /**
     * Genera un código hash único para el objeto
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}