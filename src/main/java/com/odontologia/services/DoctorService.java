package com.odontologia.services;

// Importamos el modelo Doctor
import com.odontologia.models.Doctor;
// Importamos List para listas
import java.util.List;

/**
 * INTERFACE DOCTOR SERVICE
 *
 * PARA QUE SIRVE:
 * - Define los metodos que debe tener el servicio de doctores
 * - Es una INTERFACE, solo declara metodos, no los implementa
 * - La implementacion real esta en DoctorServiceJdbcImplement.java
 *
 * FLUJO DE LA APLICACION:
 * Controller -> Service -> Repository -> Base de Datos
 */
public interface DoctorService {

    // ========== METODOS CRUD ==========

    /**
     * Guarda un nuevo doctor en el sistema
     *
     * @param doctor Objeto Doctor a guardar
     * @return true si se guardo correctamente, false si hubo error
     */
    boolean guardar(Doctor doctor);

    /**
     * Actualiza un doctor existente
     *
     * @param doctor Objeto Doctor con los datos actualizados
     * @return true si se actualizo correctamente, false si hubo error
     */
    boolean actualizar(Doctor doctor);

    /**
     * Elimina un doctor por su ID
     *
     * @param idDoctor ID del doctor a eliminar
     * @return true si se elimino correctamente, false si hubo error
     */
    boolean eliminar(int idDoctor);

    // ========== METODOS DE CONSULTA ==========

    /**
     * Busca un doctor por su ID
     *
     * @param idDoctor ID del doctor a buscar
     * @return El doctor encontrado o null si no existe
     */
    Doctor buscarPorId(int idDoctor);

    /**
     * Obtiene todos los doctores del sistema
     *
     * @return Lista con todos los doctores
     */
    List<Doctor> listarTodos();

    /**
     * Busca doctores por nombre o apellido
     * Busqueda parcial (LIKE %nombre%)
     *
     * @param nombre Texto a buscar en nombre o apellido
     * @return Lista de doctores que coinciden
     */
    List<Doctor> buscarPorNombre(String nombre);

    /**
     * Busca doctores por especialidad
     *
     * @param especialidad Especialidad a buscar (ej: "Ortodoncia")
     * @return Lista de doctores con esa especialidad
     */
    List<Doctor> buscarPorEspecialidad(String especialidad);

    // ========== METODOS ADICIONALES ==========

    /**
     * Verifica si un email ya esta registrado
     * Util para evitar duplicados
     *
     * @param email Email a verificar
     * @return true si ya existe, false si no
     */
    boolean existeEmail(String email);

    /**
     * Obtiene todas las especialidades unicas
     * Util para llenar un dropdown/select
     *
     * @return Lista de especialidades sin repetir
     */
    List<String> listarEspecialidades();

    /**
     * Cuenta el total de doctores registrados
     *
     * @return Numero total de doctores
     */
    int contarDoctores();

    /**
     * Obtiene los doctores activos
     * Un doctor activo es aquel que puede atender citas
     *
     * @return Lista de doctores activos
     */
    List<Doctor> obtenerDoctoresActivos();
}