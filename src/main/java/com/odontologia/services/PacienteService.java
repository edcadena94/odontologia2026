package com.odontologia.services;

// Importamos el modelo Paciente
import com.odontologia.models.Paciente;
// Importamos Date para fechas
import java.util.Date;
// Importamos List para listas
import java.util.List;

/**
 * INTERFACE PACIENTE SERVICE
 *
 * PARA QUE SIRVE:
 * - Define los metodos que debe tener el servicio de pacientes
 * - Es una INTERFACE, solo declara metodos, no los implementa
 * - La implementacion real esta en PacienteServiceJdbcImplement.java
 *
 * QUE ES UN SERVICE:
 * - Es la capa de LOGICA DE NEGOCIO
 * - Actua como intermediario entre Controller y Repository
 * - Aqui se validan datos, se aplican reglas de negocio, etc.
 *
 * FLUJO DE LA APLICACION:
 * Controller -> Service -> Repository -> Base de Datos
 *
 * EJEMPLO:
 * El usuario hace clic en "Guardar Paciente"
 * 1. Controller recibe la peticion
 * 2. Controller llama a Service.guardar(paciente)
 * 3. Service valida los datos
 * 4. Service llama a Repository.guardar(paciente)
 * 5. Repository ejecuta el INSERT en la BD
 */
public interface PacienteService {

    // ========== METODOS CRUD ==========

    /**
     * Guarda un nuevo paciente en el sistema
     *
     * @param paciente Objeto Paciente a guardar
     * @return true si se guardo correctamente, false si hubo error
     *
     * EJEMPLO:
     * Paciente nuevo = new Paciente("Juan", "Perez", ...);
     * boolean guardado = pacienteService.guardar(nuevo);
     */
    boolean guardar(Paciente paciente);

    /**
     * Actualiza un paciente existente
     *
     * @param paciente Objeto Paciente con los datos actualizados
     * @return true si se actualizo correctamente, false si hubo error
     *
     * EJEMPLO:
     * Paciente paciente = pacienteService.buscarPorId(1);
     * paciente.setTelefono("0999999999");
     * boolean actualizado = pacienteService.actualizar(paciente);
     */
    boolean actualizar(Paciente paciente);

    /**
     * Elimina un paciente por su ID
     *
     * @param idPaciente ID del paciente a eliminar
     * @return true si se elimino correctamente, false si hubo error
     *
     * EJEMPLO:
     * boolean eliminado = pacienteService.eliminar(5);
     */
    boolean eliminar(int idPaciente);

    // ========== METODOS DE CONSULTA ==========

    /**
     * Busca un paciente por su ID
     *
     * @param idPaciente ID del paciente a buscar
     * @return El paciente encontrado o null si no existe
     *
     * EJEMPLO:
     * Paciente paciente = pacienteService.buscarPorId(1);
     * if (paciente != null) {
     *     System.out.println("Encontrado: " + paciente.getNombreCompleto());
     * }
     */
    Paciente buscarPorId(int idPaciente);

    /**
     * Obtiene todos los pacientes del sistema
     *
     * @return Lista con todos los pacientes
     *
     * EJEMPLO:
     * List<Paciente> pacientes = pacienteService.listarTodos();
     * for (Paciente p : pacientes) {
     *     System.out.println(p.getNombreCompleto());
     * }
     */
    List<Paciente> listarTodos();

    /**
     * Busca pacientes por nombre o apellido
     * Busqueda parcial (LIKE %nombre%)
     *
     * @param nombre Texto a buscar en nombre o apellido
     * @return Lista de pacientes que coinciden
     *
     * EJEMPLO:
     * List<Paciente> resultados = pacienteService.buscarPorNombre("Juan");
     */
    List<Paciente> buscarPorNombre(String nombre);

    /**
     * Busca pacientes por fecha de nacimiento exacta
     *
     * @param fechaNacimiento Fecha de nacimiento a buscar
     * @return Lista de pacientes nacidos en esa fecha
     */
    List<Paciente> buscarPorFechaNacimiento(Date fechaNacimiento);

    /**
     * Busca pacientes por sexo
     *
     * @param sexo 'M' para masculino, 'F' para femenino
     * @return Lista de pacientes con ese sexo
     */
    List<Paciente> buscarPorSexo(char sexo);

    // ========== METODOS ADICIONALES ==========

    /**
     * Verifica si un email ya esta registrado
     * Util para evitar duplicados al registrar
     *
     * @param email Email a verificar
     * @return true si ya existe, false si no
     *
     * EJEMPLO:
     * if (pacienteService.existeEmail("juan@mail.com")) {
     *     System.out.println("Este email ya esta registrado");
     * }
     */
    boolean existeEmail(String email);

    /**
     * Cuenta el total de pacientes registrados
     *
     * @return Numero total de pacientes
     *
     * EJEMPLO:
     * int total = pacienteService.contarPacientes();
     * System.out.println("Total de pacientes: " + total);
     */
    int contarPacientes();
}