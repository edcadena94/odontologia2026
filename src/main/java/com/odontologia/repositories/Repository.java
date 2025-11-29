package com.odontologia.repositories;

// Importamos SQLException para manejar errores de base de datos
import java.sql.SQLException;
// Importamos List para devolver listas de objetos
import java.util.List;

/**
 * INTERFACE REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Es una INTERFACE generica para operaciones CRUD
 * - CRUD = Create (crear), Read (leer), Update (actualizar), Delete (eliminar)
 * - Todas las demas clases Repository implementan esta interface
 * - El <T> significa que funciona con CUALQUIER tipo de objeto (Paciente, Doctor, etc.)
 *
 * EJEMPLO DE USO:
 * - PacienteRepository implements Repository<Paciente>
 * - DoctorRepository implements Repository<Doctor>
 *
 * @param <T> Tipo de entidad (Paciente, Doctor, Cita, etc.)
 */
public interface Repository<T> {

    /**
     * LISTAR TODOS
     * Obtiene todos los registros de la tabla
     *
     * @return Lista con todos los objetos
     * @throws SQLException Si hay error en la consulta SQL
     *
     * Ejemplo: List<Paciente> pacientes = pacienteRepository.listar();
     */
    List<T> listar() throws SQLException;

    /**
     * BUSCAR POR ID
     * Obtiene un registro especifico por su ID
     *
     * @param id El ID del registro a buscar
     * @return El objeto encontrado o null si no existe
     * @throws SQLException Si hay error en la consulta SQL
     *
     * Ejemplo: Paciente p = pacienteRepository.porId(1);
     */
    T porId(Integer id) throws SQLException;

    /**
     * GUARDAR
     * Inserta un nuevo registro o actualiza uno existente
     *
     * @param entity El objeto a guardar
     * @throws SQLException Si hay error en la consulta SQL
     *
     * Ejemplo: pacienteRepository.guardar(nuevoPaciente);
     */
    void guardar(T entity) throws SQLException;

    /**
     * ELIMINAR
     * Elimina un registro por su ID
     *
     * @param id El ID del registro a eliminar
     * @throws SQLException Si hay error en la consulta SQL
     *
     * Ejemplo: pacienteRepository.eliminar(5);
     */
    void eliminar(Integer id) throws SQLException;

    /**
     * CONTAR
     * Cuenta cuantos registros hay en la tabla
     *
     * @return Numero total de registros
     * @throws SQLException Si hay error en la consulta SQL
     *
     * Ejemplo: int total = pacienteRepository.contar();
     */
    int contar() throws SQLException;

    /**
     * EXISTE
     * Verifica si un registro existe por su ID
     *
     * @param id El ID a verificar
     * @return true si existe, false si no
     * @throws SQLException Si hay error en la consulta SQL
     *
     * Ejemplo: if (pacienteRepository.existe(10)) { ... }
     */
    boolean existe(Integer id) throws SQLException;
}