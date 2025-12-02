package com.odontologia.services;

import com.odontologia.models.Paciente;
import java.util.List;

/**
 * INTERFACE PACIENTE SERVICE
 * 
 * Define los métodos para gestionar pacientes
 * ACTUALIZADA: Simplificada, sin métodos innecesarios
 */
public interface PacienteService {

    /**
     * Lista todos los pacientes activos
     */
    List<Paciente> listarTodos();

    /**
     * Busca pacientes por nombre o apellido
     */
    List<Paciente> buscarPorNombre(String nombre);

    /**
     * Busca un paciente por ID (obsoleto - mantener por compatibilidad)
     */
    @Deprecated
    Paciente buscarPorId(int idPaciente);

    /**
     * Guarda un nuevo paciente
     */
    boolean guardar(Paciente paciente);

    /**
     * Actualiza un paciente existente
     */
    boolean actualizar(Paciente paciente);

    /**
     * Elimina un paciente (obsoleto - mantener por compatibilidad)
     */
    @Deprecated
    boolean eliminar(int idPaciente);
}
