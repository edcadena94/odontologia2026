package com.odontologia.services;

// Importamos el modelo Doctor
import com.odontologia.models.Doctor;
// Importamos el repositorio de doctores
import com.odontologia.repositories.DoctorRepository;
// Importamos Connection para la base de datos
import java.sql.Connection;
// Importamos List para listas
import java.util.List;

/**
 * CLASE DOCTOR SERVICE JDBC IMPLEMENT
 *
 * PARA QUE SIRVE:
 * - Esta clase IMPLEMENTA la interface DoctorService
 * - Contiene la LOGICA DE NEGOCIO para doctores
 * - Valida los datos antes de enviarlos al Repository
 * - Actua como intermediario entre Controller y Repository
 *
 * FLUJO:
 * Controller -> Service (valida) -> Repository -> BD
 */
public class DoctorServiceJdbcImplement implements DoctorService {

    // ========== ATRIBUTO ==========

    /**
     * Repositorio de doctores
     * Se usa para acceder a la base de datos
     */
    private final DoctorRepository doctorRepository;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     * Crea internamente el repositorio con esa conexion
     *
     * @param conn Conexion activa a MySQL
     *
     * EJEMPLO:
     * Connection conn = Conexion.getConnection();
     * DoctorService service = new DoctorServiceJdbcImplement(conn);
     */
    public DoctorServiceJdbcImplement(Connection conn) {
        this.doctorRepository = new DoctorRepository(conn);
    }

    // ========== METODO GUARDAR ==========

    /**
     * Guarda un nuevo doctor con validaciones
     *
     * VALIDACIONES:
     * 1. El doctor no puede ser null
     * 2. El nombre es obligatorio
     * 3. El apellido es obligatorio
     * 4. La especialidad es obligatoria
     * 5. Si tiene email, verifica que no exista ya
     *
     * @param doctor El doctor a guardar
     * @return true si se guardo correctamente, false si fallo validacion
     */
    @Override
    public boolean guardar(Doctor doctor) {
        // Validacion 1: El doctor no puede ser null y debe tener nombre
        if (doctor == null || doctor.getNombre() == null || doctor.getNombre().trim().isEmpty()) {
            return false;
        }

        // Validacion 2: El apellido es obligatorio
        if (doctor.getApellido() == null || doctor.getApellido().trim().isEmpty()) {
            return false;
        }

        // Validacion 3: La especialidad es obligatoria
        if (doctor.getEspecialidad() == null || doctor.getEspecialidad().trim().isEmpty()) {
            return false;
        }

        // Validacion 4: Si tiene email, verificar que no exista
        if (doctor.getEmail() != null && !doctor.getEmail().trim().isEmpty()) {
            if (existeEmail(doctor.getEmail())) {
                return false; // Email ya registrado
            }
        }

        // Si paso todas las validaciones, guardamos
        return doctorRepository.guardar(doctor);
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza un doctor existente con validaciones
     *
     * VALIDACIONES:
     * 1. El doctor debe tener un ID valido
     * 2. El nombre es obligatorio
     * 3. El apellido es obligatorio
     * 4. La especialidad es obligatoria
     *
     * @param doctor El doctor a actualizar
     * @return true si se actualizo correctamente
     */
    @Override
    public boolean actualizar(Doctor doctor) {
        // Validacion: Debe tener un ID valido para actualizar
        if (doctor == null || doctor.getIdDoctor() == null || doctor.getIdDoctor() <= 0) {
            return false;
        }

        // Validacion: Nombre obligatorio
        if (doctor.getNombre() == null || doctor.getNombre().trim().isEmpty()) {
            return false;
        }

        // Validacion: Apellido obligatorio
        if (doctor.getApellido() == null || doctor.getApellido().trim().isEmpty()) {
            return false;
        }

        // Validacion: Especialidad obligatoria
        if (doctor.getEspecialidad() == null || doctor.getEspecialidad().trim().isEmpty()) {
            return false;
        }

        // Si paso todas las validaciones, actualizamos
        return doctorRepository.actualizar(doctor);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina un doctor por su ID
     *
     * VALIDACION: El ID debe ser mayor a 0
     *
     * @param idDoctor ID del doctor a eliminar
     * @return true si se elimino correctamente
     */
    @Override
    public boolean eliminar(int idDoctor) {
        // Validacion: ID debe ser positivo
        if (idDoctor <= 0) {
            return false;
        }
        return doctorRepository.eliminar(idDoctor);
    }

    // ========== METODO BUSCAR POR ID ==========

    /**
     * Busca un doctor por su ID
     *
     * @param idDoctor ID del doctor a buscar
     * @return El doctor encontrado o null
     */
    @Override
    public Doctor buscarPorId(int idDoctor) {
        // Validacion: ID debe ser positivo
        if (idDoctor <= 0) {
            return null;
        }
        return doctorRepository.buscarPorId(idDoctor);
    }

    // ========== METODO LISTAR TODOS ==========

    /**
     * Obtiene todos los doctores
     *
     * @return Lista con todos los doctores
     */
    @Override
    public List<Doctor> listarTodos() {
        return doctorRepository.obtenerTodos();
    }

    // ========== METODO BUSCAR POR NOMBRE ==========

    /**
     * Busca doctores por nombre o apellido
     *
     * @param nombre Texto a buscar
     * @return Lista de doctores que coinciden
     */
    @Override
    public List<Doctor> buscarPorNombre(String nombre) {
        // Validacion: El nombre no puede estar vacio
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of(); // Retorna lista vacia
        }
        return doctorRepository.buscarPorNombre(nombre.trim());
    }

    // ========== METODO BUSCAR POR ESPECIALIDAD ==========

    /**
     * Busca doctores por especialidad
     *
     * @param especialidad Especialidad a buscar
     * @return Lista de doctores con esa especialidad
     */
    @Override
    public List<Doctor> buscarPorEspecialidad(String especialidad) {
        // Validacion: La especialidad no puede estar vacia
        if (especialidad == null || especialidad.trim().isEmpty()) {
            return List.of(); // Retorna lista vacia
        }
        return doctorRepository.buscarPorEspecialidad(especialidad.trim());
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
        return doctorRepository.existeEmail(email.trim());
    }

    // ========== METODO LISTAR ESPECIALIDADES ==========

    /**
     * Obtiene todas las especialidades unicas
     * Util para llenar dropdowns
     *
     * @return Lista de especialidades sin repetir
     */
    @Override
    public List<String> listarEspecialidades() {
        return doctorRepository.listarEspecialidades();
    }

    // ========== METODO CONTAR DOCTORES ==========

    /**
     * Cuenta el total de doctores
     *
     * @return Numero total de doctores
     */
    @Override
    public int contarDoctores() {
        return doctorRepository.contar();
    }

    // ========== METODO OBTENER DOCTORES ACTIVOS ==========

    /**
     * Obtiene los doctores activos
     * Por ahora retorna todos, pero se puede personalizar
     * para filtrar solo los que esten habilitados
     *
     * @return Lista de doctores activos
     */
    @Override
    public List<Doctor> obtenerDoctoresActivos() {
        // Por ahora devuelve todos
        // Se puede agregar un campo "activo" en la BD para filtrar
        return doctorRepository.obtenerTodos();
    }
}