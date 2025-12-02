package com.odontologia.services;

import com.odontologia.models.Paciente;
import com.odontologia.repositories.PacienteRepository;
import java.sql.Connection;
import java.util.List;

/**
 * IMPLEMENTACIÓN DEL SERVICIO DE PACIENTE
 * 
 * CAMBIOS: Adaptado para usar cédula como PK en lugar de ID
 */
public class PacienteServiceJdbcImplement implements PacienteService {

    private final PacienteRepository pacienteRepository;

    /**
     * Constructor que recibe la conexión
     */
    public PacienteServiceJdbcImplement(Connection conn) {
        this.pacienteRepository = new PacienteRepository(conn);
    }

    /**
     * Lista todos los pacientes activos
     */
    @Override
    public List<Paciente> listarTodos() {
        return pacienteRepository.obtenerTodos();
    }

    /**
     * Busca un paciente por su cédula (PK)
     * CAMBIO: Antes era buscarPorId(int), ahora buscarPorId(String)
     */
    @Override
    public Paciente buscarPorId(int idPaciente) {
        // Método obsoleto - mantener para compatibilidad pero retorna null
        return null;
    }

    /**
     * NUEVO: Busca por cédula (String)
     */
    public Paciente buscarPorCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return null;
        }
        return pacienteRepository.buscarPorCedula(cedula);
    }

    /**
     * Busca pacientes por nombre
     */
    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return listarTodos();
        }
        return pacienteRepository.buscarPorNombre(nombre);
    }

    /**
     * Guarda un nuevo paciente
     * VALIDACIÓN: Verifica que la cédula no exista
     */
    @Override
    public boolean guardar(Paciente paciente) {
        // Validar datos básicos
        if (!paciente.esValido()) {
            return false;
        }

        // Validar formato de cédula
        if (!paciente.cedulaValida()) {
            return false;
        }

        // Verificar que la cédula no exista
        if (pacienteRepository.existeCedula(paciente.getCedula())) {
            return false; // Cédula duplicada
        }

        // Guardar
        return pacienteRepository.guardar(paciente);
    }

    /**
     * Actualiza un paciente existente
     */
    @Override
    public boolean actualizar(Paciente paciente) {
        // Validar datos básicos
        if (!paciente.esValido()) {
            return false;
        }

        // Verificar que el paciente exista
        Paciente pacienteExistente = pacienteRepository.buscarPorCedula(paciente.getCedula());
        if (pacienteExistente == null) {
            return false; // No existe
        }

        // Actualizar
        return pacienteRepository.actualizar(paciente);
    }

    /**
     * Elimina un paciente por su cédula
     * CAMBIO: Antes era eliminar(int), ahora eliminar(String)
     */
    @Override
    public boolean eliminar(int idPaciente) {
        // Método obsoleto - mantener para compatibilidad
        return false;
    }

    /**
     * NUEVO: Elimina por cédula
     */
    public boolean eliminarPorCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }

        // Verificar que exista antes de eliminar
        Paciente paciente = pacienteRepository.buscarPorCedula(cedula);
        if (paciente == null) {
            return false;
        }

        return pacienteRepository.eliminar(cedula);
    }

    /**
     * Verifica si una cédula ya está registrada
     */
    public boolean existeCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        return pacienteRepository.existeCedula(cedula);
    }

    /**
     * Verifica si un email ya está registrado
     */
    public boolean existeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return pacienteRepository.existeEmail(email);
    }

    /**
     * Cuenta el total de pacientes activos
     */
    public int contarPacientes() {
        return pacienteRepository.contar();
    }
}
