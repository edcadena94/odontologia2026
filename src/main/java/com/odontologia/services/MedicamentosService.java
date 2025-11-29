package com.odontologia.services;

import com.odontologia.models.Medicamentos;
import java.util.List;

/**
 * Interface que define los métodos de servicio para Medicamentos
 * Establece el contrato que debe cumplir cualquier implementación
 * de la lógica de negocio de medicamentos
 */
public interface MedicamentosService {

    /**
     * Obtiene todos los medicamentos registrados
     * @return Lista de todos los medicamentos
     */
    List<Medicamentos> listarTodos();

    /**
     * Busca un medicamento por su ID
     * @param id Identificador único del medicamento
     * @return Medicamento encontrado o null si no existe
     */
    Medicamentos buscarPorId(int id);

    /**
     * Guarda un nuevo medicamento en el sistema
     * @param medicamento Objeto con los datos del medicamento
     * @return true si se guardó correctamente, false si falló
     */
    boolean guardar(Medicamentos medicamento);

    /**
     * Actualiza los datos de un medicamento existente
     * @param medicamento Objeto con los datos actualizados
     * @return true si se actualizó correctamente, false si falló
     */
    boolean actualizar(Medicamentos medicamento);

    /**
     * Elimina un medicamento del sistema
     * @param id Identificador del medicamento a eliminar
     * @return true si se eliminó correctamente, false si falló
     */
    boolean eliminar(int id);
}