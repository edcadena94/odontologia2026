package com.odontologia.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * MODELO PACIENTE
 * La CÉDULA es la clave primaria (no auto-increment)
 */
public class Paciente {

    // ========== ATRIBUTOS ==========
    
    private String cedula;  // PK
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String genero;
    private String telefono;
    private String email;
    private String direccion;
    private String alergias;
    private String antecedentesMedicos;
    private String estado;
    private LocalDateTime fechaRegistro;


    // ========== CONSTRUCTORES ==========

    public Paciente() {
        this.estado = "ACTIVO";
    }

    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        this();
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }


    // ========== GETTERS ==========

    public String getCedula() { return cedula; }
    public String getIdPaciente() { return cedula; } // Alias compatibilidad
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero() { return genero; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }
    public String getAlergias() { return alergias; }
    public String getAntecedentesMedicos() { return antecedentesMedicos; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }


    // ========== SETTERS ==========

    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setIdPaciente(String cedula) { this.cedula = cedula; } // Alias
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public void setAntecedentesMedicos(String antecedentesMedicos) { this.antecedentesMedicos = antecedentesMedicos; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }


    // ========== MÉTODOS DE UTILIDAD ==========

    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }

    public int getEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean isActivo() { return "ACTIVO".equals(estado); }
    public boolean tieneAlergias() { return alergias != null && !alergias.trim().isEmpty(); }
    public boolean tieneAntecedentes() { return antecedentesMedicos != null && !antecedentesMedicos.trim().isEmpty(); }

    public boolean esValido() {
        return cedula != null && !cedula.trim().isEmpty() &&
                nombre != null && !nombre.trim().isEmpty() &&
                apellido != null && !apellido.trim().isEmpty() &&
                telefono != null && !telefono.trim().isEmpty();
    }

    public boolean cedulaValida() {
        if (cedula == null || cedula.length() != 10) return false;
        return cedula.matches("\\d{10}");
    }


    // ========== MÉTODOS OVERRIDE ==========

    @Override
    public String toString() {
        return "Paciente{cedula='" + cedula + "', nombre='" + getNombreCompleto() + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return cedula != null && cedula.equals(paciente.cedula);
    }

    @Override
    public int hashCode() {
        return cedula != null ? cedula.hashCode() : 0;
    }
}
