package com.odontologia.models;

/**
 * MODELO DOCTOR
 * Esta clase representa UN doctor del consultorio odontológico
 * Cada objeto = 1 fila en la tabla "doctores"
 */
public class Doctor {

    // ========== ATRIBUTOS ==========

    /**
     * ID único del doctor (clave primaria)
     */
    private Integer idDoctor;

    /**
     * Nombre del doctor
     * Ejemplo: "Carlos"
     */
    private String nombre;

    /**
     * Apellido del doctor
     * Ejemplo: "Martínez"
     */
    private String apellido;

    /**
     * Especialidad médica
     * Ejemplo: "Ortodoncia", "Endodoncia", "Cirugía Maxilofacial"
     */
    private String especialidad;

    /**
     * Teléfono de contacto
     * Ejemplo: "0998765432"
     */
    private String telefono;

    /**
     * Correo electrónico
     * Ejemplo: "dr.martinez@clinica.com"
     */
    private String email;


    // ========== CONSTRUCTORES ==========

    /**
     * CONSTRUCTOR VACÍO
     * Se usa cuando creamos un objeto sin datos
     * Ejemplo: Doctor d = new Doctor();
     */
    public Doctor() {
    }

    /**
     * CONSTRUCTOR SIN ID
     * Se usa cuando creamos un nuevo doctor (antes de guardarlo en BD)
     * El ID se genera automáticamente en la base de datos
     *
     * @param nombre Nombre del doctor
     * @param apellido Apellido del doctor
     * @param especialidad Especialidad médica
     * @param telefono Teléfono de contacto
     * @param email Correo electrónico
     *
     * Ejemplo:
     * Doctor d = new Doctor("Carlos", "Martínez", "Ortodoncia", "0998765432", "dr.martinez@mail.com");
     */
    public Doctor(String nombre, String apellido, String especialidad, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
    }

    /**
     * CONSTRUCTOR COMPLETO CON ID
     * Se usa cuando recuperamos un doctor existente de la base de datos
     * O cuando vamos a actualizar sus datos
     *
     * @param idDoctor ID del doctor
     * @param nombre Nombre del doctor
     * @param apellido Apellido del doctor
     * @param especialidad Especialidad médica
     * @param telefono Teléfono de contacto
     * @param email Correo electrónico
     *
     * Ejemplo:
     * Doctor d = new Doctor(1, "Carlos", "Martínez", "Ortodoncia", "0998765432", "dr.martinez@mail.com");
     */
    public Doctor(Integer idDoctor, String nombre, String apellido, String especialidad, String telefono, String email) {
        this.idDoctor = idDoctor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
    }


    // ========== GETTERS ==========

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }


    // ========== SETTERS ==========

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // ========== MÉTODOS DE UTILIDAD ==========

    /**
     * Obtiene el nombre completo del doctor
     * @return "Carlos Martínez"
     */
    public String getNombreCompleto() {
        // Manejo de valores null para evitar errores
        String nombreStr = (nombre != null ? nombre : "");
        String apellidoStr = (apellido != null ? apellido : "");
        return nombreStr + " " + apellidoStr;
    }

    /**
     * Obtiene el nombre con título profesional
     * @return "Dr. Carlos Martínez"
     *
     * USO: En lugar de escribir "Dr. " + doctor.getNombre()
     *      Simplemente: doctor.getNombreConTitulo()
     */
    public String getNombreConTitulo() {
        return "Dr. " + getNombreCompleto();
    }

    /**
     * Obtiene el nombre completo con título y especialidad
     * @return "Dr. Carlos Martínez - Ortodoncia"
     *
     * USO: Útil para mostrar en listas o selects
     */
    public String getNombreConEspecialidad() {
        String nombreCompleto = getNombreConTitulo();

        // Solo agregar especialidad si existe
        if (especialidad != null && !especialidad.trim().isEmpty()) {
            nombreCompleto += " - " + especialidad;
        }

        return nombreCompleto;
    }

    /**
     * Obtiene las iniciales del doctor
     * @return "CM" (Carlos Martínez)
     *
     * USO: Útil para avatares o identificadores cortos
     */
    public String getIniciales() {
        String inicialNombre = "";
        String inicialApellido = "";

        // Obtener primera letra del nombre (si existe)
        if (nombre != null && !nombre.isEmpty()) {
            inicialNombre = nombre.substring(0, 1).toUpperCase();
        }

        // Obtener primera letra del apellido (si existe)
        if (apellido != null && !apellido.isEmpty()) {
            inicialApellido = apellido.substring(0, 1).toUpperCase();
        }

        return inicialNombre + inicialApellido;
    }


    // ========== MÉTODOS DE VALIDACIÓN ==========

    /**
     * Verifica si el doctor tiene una especialidad asignada
     * @return true si tiene especialidad
     *
     * USO: if (doctor.tieneEspecialidad()) { ... }
     */
    public boolean tieneEspecialidad() {
        return especialidad != null && !especialidad.trim().isEmpty();
    }

    /**
     * Verifica si el doctor tiene teléfono
     * @return true si tiene teléfono
     */
    public boolean tieneTelefono() {
        return telefono != null && !telefono.trim().isEmpty();
    }

    /**
     * Verifica si el doctor tiene email
     * @return true si tiene email
     */
    public boolean tieneEmail() {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Valida que el doctor tenga los datos mínimos requeridos
     * @return true si tiene nombre, apellido y especialidad
     *
     * USO: Antes de guardar en BD
     * if (!doctor.esValido()) {
     *     System.out.println("Faltan datos obligatorios");
     * }
     */
    public boolean esValido() {
        return nombre != null && !nombre.trim().isEmpty() &&
                apellido != null && !apellido.trim().isEmpty() &&
                especialidad != null && !especialidad.trim().isEmpty();
    }


    // ========== MÉTODOS OVERRIDE ==========

    /**
     * Representación en texto del objeto
     * Útil para debugging (imprimir el objeto completo)
     */
    @Override
    public String toString() {
        return "Doctor{" +
                "idDoctor=" + idDoctor +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * Compara si dos doctores son iguales
     * Dos doctores son iguales si tienen el mismo ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Doctor doctor = (Doctor) obj;
        return idDoctor != null && idDoctor.equals(doctor.idDoctor);
    }

    /**
     * Genera un código hash único para el objeto
     * Se basa en el ID del doctor
     */
    @Override
    public int hashCode() {
        return idDoctor != null ? idDoctor.hashCode() : 0;
    }
}