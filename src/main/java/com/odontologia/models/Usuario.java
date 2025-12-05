package com.odontologia.models;

import java.time.LocalDateTime;

/**
 * MODELO USUARIO
 * Esta clase representa un usuario que puede iniciar sesión en el sistema
 * Cada objeto = 1 fila en la tabla "usuario"
 * 
 * ROLES DISPONIBLES:
 * - ADMIN: Acceso total al sistema
 * - DOCTOR: Ve sus citas y pacientes asignados
 * - SECRETARIA: Gestiona citas, pacientes y facturas
 * - PACIENTE: Ve sus propias citas y facturas
 */
public class Usuario {

    // ========== CONSTANTES DE ROLES ==========
    
    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_DOCTOR = "DOCTOR";
    public static final String ROL_SECRETARIA = "SECRETARIA";
    public static final String ROL_PACIENTE = "PACIENTE";

    // ========== ATRIBUTOS ==========

    private Integer idUsuario;
    private String username;
    private String passwordHash;
    private String rol;
    private String cedulaPaciente;  // Solo si rol = PACIENTE
    private Integer idOdontologo;   // Solo si rol = DOCTOR
    private String estado;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime fechaCreacion;


    // ========== CONSTRUCTORES ==========

    public Usuario() {
        this.estado = "ACTIVO";
    }

    public Usuario(String username, String passwordHash, String rol) {
        this();
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public Usuario(Integer idUsuario, String username, String passwordHash, String rol) {
        this(username, passwordHash, rol);
        this.idUsuario = idUsuario;
    }


    // ========== GETTERS ==========

    public Integer getIdUsuario() { return idUsuario; }
    public Integer getId() { return idUsuario; } // Alias compatibilidad
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRol() { return rol; }
    public String getCedulaPaciente() { return cedulaPaciente; }
    public Integer getIdOdontologo() { return idOdontologo; }
    public String getEstado() { return estado; }
    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }


    // ========== SETTERS ==========

    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public void setId(Integer id) { this.idUsuario = id; } // Alias
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRol(String rol) { this.rol = rol; }
    public void setCedulaPaciente(String cedulaPaciente) { this.cedulaPaciente = cedulaPaciente; }
    public void setIdOdontologo(Integer idOdontologo) { this.idOdontologo = idOdontologo; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }


    // ========== MÉTODOS DE VERIFICACIÓN DE ROL ==========

    public boolean tieneRol(String rolBuscado) {
        return this.rol != null && this.rol.equalsIgnoreCase(rolBuscado);
    }

    public boolean isAdmin() { return tieneRol(ROL_ADMIN); }
    public boolean esAdmin() { return isAdmin(); }
    
    public boolean isDoctor() { return tieneRol(ROL_DOCTOR); }
    public boolean esDoctor() { return isDoctor(); }
    
    public boolean isSecretaria() { return tieneRol(ROL_SECRETARIA); }
    public boolean esRecepcionista() { return isSecretaria(); }
    
    public boolean isPaciente() { return tieneRol(ROL_PACIENTE); }


    // ========== MÉTODOS DE PERMISOS ==========

    public boolean puedeGestionarUsuarios() { return isAdmin(); }
    public boolean puedeGestionarDoctores() { return isAdmin(); }
    public boolean puedeGestionarPacientes() { return isAdmin() || isSecretaria(); }
    public boolean puedeGestionarCitas() { return isAdmin() || isSecretaria() || isDoctor(); }
    public boolean puedeVerCitas() { return true; }
    public boolean puedeCrearFacturas() { return isAdmin() || isSecretaria(); }
    public boolean puedeVerFacturas() { return isAdmin() || isSecretaria() || isPaciente(); }
    public boolean puedeVerHistorial() { return isAdmin() || isDoctor() || isPaciente(); }
    public boolean puedeEditarHistorial() { return isAdmin() || isDoctor(); }
    public boolean puedeVerReportes() { return isAdmin() || isSecretaria(); }


    // ========== MÉTODOS DE UTILIDAD ==========

    /**
     * Obtiene la página de inicio según el rol
     * USO: response.sendRedirect(usuario.getPaginaInicio());
     */
    public String getPaginaInicio() {
        switch (rol) {
            case ROL_ADMIN: return "panelAdmin.jsp";
            case ROL_DOCTOR: return "panelDoctor.jsp";
            case ROL_SECRETARIA: return "panelSecretaria.jsp";
            case ROL_PACIENTE: return "panelPaciente.jsp";
            default: return "login.jsp";
        }
    }

    public String getRolDescripcion() {
        switch (rol) {
            case ROL_ADMIN: return "Administrador";
            case ROL_DOCTOR: return "Doctor";
            case ROL_SECRETARIA: return "Secretaria";
            case ROL_PACIENTE: return "Paciente";
            default: return rol;
        }
    }

    public boolean isActivo() { return "ACTIVO".equals(estado); }

    public boolean esValido() {
        return username != null && !username.trim().isEmpty() &&
                passwordHash != null && !passwordHash.trim().isEmpty() &&
                rol != null && !rol.trim().isEmpty();
    }


    // ========== MÉTODOS OVERRIDE ==========

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return idUsuario != null && idUsuario.equals(usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return idUsuario != null ? idUsuario.hashCode() : 0;
    }
}
