package com.odontologia.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * MODELO RECEPCIONISTA
 * Esta clase representa un recepcionista del consultorio odontológico
 * Cada objeto = 1 fila en la tabla "recepcionistas"
 *
 * FUNCIONES: Puede agendar, modificar, cancelar y consultar citas
 */
public class Recepcionista {

    // ========== ATRIBUTOS ==========

    /**
     * ID único del recepcionista (clave primaria)
     */
    private int id;

    /**
     * Nombre del recepcionista
     * Ejemplo: "María González"
     */
    private String nombre;

    /**
     * Correo electrónico
     * Ejemplo: "maria@clinica.com"
     */
    private String correo;

    /**
     * Teléfono de contacto
     * Ejemplo: "0991234567"
     */
    private String telefono;


    // ========== CONSTRUCTORES ==========

    /**
     * CONSTRUCTOR VACÍO
     */
    public Recepcionista() {
    }

    /**
     * CONSTRUCTOR SIN ID
     * Se usa cuando registramos un nuevo recepcionista
     */
    public Recepcionista(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    /**
     * CONSTRUCTOR COMPLETO CON ID
     * Se usa cuando recuperamos de la base de datos
     */
    public Recepcionista(int id, String nombre, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }


    // ========== MÉTODOS DE GESTIÓN DE CITAS ==========

    /**
     * Agenda una nueva cita
     * @param cita La cita a agendar
     * @return true si se agendó correctamente
     */
    public boolean agendarCita(Cita cita) {
        // Lógica para agendar una cita
        return true;
    }

    /**
     * Modifica una cita existente
     * @param idCita ID de la cita a modificar
     * @param nuevaCita Nueva información de la cita
     * @return true si se modificó correctamente
     */
    public boolean modificarCita(int idCita, Cita nuevaCita) {
        // Lógica para modificar una cita existente
        return true;
    }

    /**
     * Cancela una cita existente
     * @param idCita ID de la cita a cancelar
     * @return true si se canceló correctamente
     */
    public boolean cancelarCita(int idCita) {
        // Lógica para cancelar una cita
        return true;
    }

    /**
     * Consulta las citas de una fecha específica
     * @param fecha Fecha a consultar
     * @return Lista de citas para esa fecha
     */
    public List<Cita> consultarCitas(LocalDate fecha) {
        // Lógica para consultar citas por fecha
        return new ArrayList<>();
    }


    // ========== GETTERS ==========

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }


    // ========== SETTERS ==========

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    // ========== MÉTODOS OVERRIDE ==========

    @Override
    public String toString() {
        return "Recepcionista{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}