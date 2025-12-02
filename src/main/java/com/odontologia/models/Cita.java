package com.odontologia.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * MODELO CITA
 * Esta clase representa una cita médica entre un paciente y un doctor
 * Cada objeto = 1 fila en la tabla "cita"
 *
 * CAMBIO IMPORTANTE: Ahora usa cedulaPaciente (String) en lugar de idPaciente (Integer)
 */
public class Cita {

    // ========== ATRIBUTOS PRINCIPALES ==========

    /**
     * ID único de la cita (clave primaria)
     * Corresponde a: id_cita en la BD
     */
    private Integer idCita;

    /**
     * Cédula del paciente (ahora es String, no Integer)
     * Se relaciona con la tabla "paciente" por cédula
     * Ejemplo: "1234567890"
     */
    private String cedulaPaciente;

    /**
     * ID del odontólogo que atenderá
     * Se relaciona con la tabla "odontologo"
     */
    private Integer idOdontologo;

    /**
     * ID del servicio solicitado
     * Se relaciona con la tabla "servicio"
     */
    private Integer idServicio;

    /**
     * Fecha de la cita
     * Ejemplo: 2025-12-01
     */
    private LocalDate fechaCita;

    /**
     * Hora de inicio
     */
    private LocalTime horaInicio;

    /**
     * Hora de fin (opcional)
     */
    private LocalTime horaFin;

    /**
     * Estado de la cita
     * Valores: "PENDIENTE", "CONFIRMADA", "EN_CURSO", "COMPLETADA", "CANCELADA", "NO_ASISTIO"
     */
    private String estado;

    /**
     * Motivo de la cita
     */
    private String motivo;

    /**
     * Observaciones adicionales
     */
    private String observaciones;


    // ========== ATRIBUTOS ADICIONALES (Para mostrar información completa) ==========

    private String nombrePaciente;
    private String apellidoPaciente;
    private String nombreDoctor;
    private String apellidoDoctor;
    private String especialidadDoctor;


    // ========== CONSTRUCTORES ==========

    /**
     * CONSTRUCTOR VACÍO
     */
    public Cita() {
        this.estado = "PENDIENTE";
    }

    /**
     * CONSTRUCTOR BÁSICO (Sin ID)
     * @param cedulaPaciente Cédula del paciente
     * @param idOdontologo ID del odontólogo
     * @param fechaCita Fecha
     * @param horaInicio Hora
     * @param motivo Motivo
     */
    public Cita(String cedulaPaciente, Integer idOdontologo, LocalDate fechaCita, 
                LocalTime horaInicio, String motivo) {
        this();
        this.cedulaPaciente = cedulaPaciente;
        this.idOdontologo = idOdontologo;
        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.motivo = motivo;
    }


    // ========== GETTERS ==========

    public Integer getIdCita() { return idCita; }
    
    /**
     * Alias para compatibilidad
     */
    public Integer getId() { return idCita; }

    public String getCedulaPaciente() { return cedulaPaciente; }
    
    /**
     * Alias para compatibilidad con código anterior
     * IMPORTANTE: Ahora retorna null porque ya no usamos ID numérico
     * El código nuevo debe usar getCedulaPaciente()
     */
    @Deprecated
    public Integer getIdPaciente() { 
        return null; // Ya no existe, usar getCedulaPaciente()
    }

    public Integer getIdOdontologo() { return idOdontologo; }
    
    /**
     * Alias para compatibilidad
     */
    public Integer getIdDoctor() { return idOdontologo; }

    public Integer getIdServicio() { return idServicio; }
    public LocalDate getFechaCita() { return fechaCita; }
    
    /**
     * Alias para compatibilidad
     */
    public LocalDate getFecha() { return fechaCita; }
    
    public LocalTime getHoraInicio() { return horaInicio; }
    
    /**
     * Alias para compatibilidad
     */
    public LocalTime getHora() { return horaInicio; }
    
    public LocalTime getHoraFin() { return horaFin; }
    public String getEstado() { return estado; }
    public String getMotivo() { return motivo; }
    public String getObservaciones() { return observaciones; }
    public String getNombrePaciente() { return nombrePaciente; }
    public String getApellidoPaciente() { return apellidoPaciente; }
    public String getNombreDoctor() { return nombreDoctor; }
    public String getApellidoDoctor() { return apellidoDoctor; }
    public String getEspecialidadDoctor() { return especialidadDoctor; }


    // ========== SETTERS ==========

    public void setIdCita(Integer idCita) { this.idCita = idCita; }
    public void setId(Integer id) { this.idCita = id; }
    
    public void setCedulaPaciente(String cedulaPaciente) { this.cedulaPaciente = cedulaPaciente; }
    
    /**
     * No hace nada - solo para compatibilidad
     */
    @Deprecated
    public void setIdPaciente(Integer idPaciente) { 
        // No se usa más, ignorar
    }
    
    public void setIdOdontologo(Integer idOdontologo) { this.idOdontologo = idOdontologo; }
    public void setIdDoctor(Integer idDoctor) { this.idOdontologo = idDoctor; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }
    public void setFecha(LocalDate fecha) { this.fechaCita = fecha; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public void setHora(LocalTime hora) { this.horaInicio = hora; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public void setApellidoPaciente(String apellidoPaciente) { this.apellidoPaciente = apellidoPaciente; }
    public void setNombreDoctor(String nombreDoctor) { this.nombreDoctor = nombreDoctor; }
    public void setApellidoDoctor(String apellidoDoctor) { this.apellidoDoctor = apellidoDoctor; }
    public void setEspecialidadDoctor(String especialidadDoctor) { this.especialidadDoctor = especialidadDoctor; }


    // ========== MÉTODOS DE UTILIDAD ==========

    public String getNombreCompletoPaciente() {
        if (nombrePaciente != null && apellidoPaciente != null) {
            return nombrePaciente + " " + apellidoPaciente;
        }
        return cedulaPaciente != null ? cedulaPaciente : "";
    }

    public String getNombreCompletoDoctor() {
        if (nombreDoctor != null && apellidoDoctor != null) {
            return "Dr. " + nombreDoctor + " " + apellidoDoctor;
        }
        return "";
    }

    public String getNombreDoctorConEspecialidad() {
        String nombreCompleto = getNombreCompletoDoctor();
        if (especialidadDoctor != null && !especialidadDoctor.trim().isEmpty()) {
            nombreCompleto += " - " + especialidadDoctor;
        }
        return nombreCompleto;
    }

    public String getFechaFormateada() {
        if (fechaCita != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fechaCita.format(formatter);
        }
        return "";
    }

    public String getHoraFormateada() {
        if (horaInicio != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return horaInicio.format(formatter);
        }
        return "";
    }

    public String getFechaHoraFormateada() {
        return getFechaFormateada() + " - " + getHoraFormateada();
    }

    public boolean estaProgramada() { return "PROGRAMADA".equalsIgnoreCase(estado) || "PENDIENTE".equalsIgnoreCase(estado); }
    public boolean estaConfirmada() { return "CONFIRMADA".equalsIgnoreCase(estado); }
    public boolean estaCompletada() { return "COMPLETADA".equalsIgnoreCase(estado); }
    public boolean estaCancelada() { return "CANCELADA".equalsIgnoreCase(estado); }
    public boolean sePuedeModificar() { return estaProgramada() || estaConfirmada(); }
    public boolean sePuedeCancelar() { return !estaCompletada() && !estaCancelada(); }

    public boolean esValida() {
        return cedulaPaciente != null && !cedulaPaciente.trim().isEmpty() &&
                idOdontologo != null && idOdontologo > 0 &&
                fechaCita != null &&
                horaInicio != null;
    }


    // ========== MÉTODOS OVERRIDE ==========

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + idCita +
                ", cedulaPaciente='" + cedulaPaciente + '\'' +
                ", idOdontologo=" + idOdontologo +
                ", fecha=" + fechaCita +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cita cita = (Cita) obj;
        return idCita != null && idCita.equals(cita.idCita);
    }

    @Override
    public int hashCode() {
        return idCita != null ? idCita.hashCode() : 0;
    }
}
