package com.odontologia.repositories;

import com.odontologia.models.Medicamentos;
import java.util.List;

/**
 * Interface que define las operaciones de base de datos para Medicamentos
 * Sigue el patrón Repository para separar la lógica de acceso a datos
 * de la lógica de negocio
 */
public interface MedicamentosRepository {

    /**
     * Obtiene todos los medicamentos de la base de datos
     * @return Lista con todos los medicamentos
     */
    List<Medicamentos> listarTodos();

    /**
     * Busca un medicamento específico por su ID
     * @param id Identificador único del medicamento
     * @return Medicamento encontrado o null si no existe
     */
    Medicamentos buscarPorId(int id);

    /**
     * Inserta un nuevo medicamento en la base de datos
     * @param medicamento Objeto con los datos a insertar
     * @return true si la inserción fue exitosa
     */
    boolean guardar(Medicamentos medicamento);

    /**
     * Actualiza un medicamento existente en la base de datos
     * @param medicamento Objeto con los datos actualizados
     * @return true si la actualización fue exitosa
     */
    boolean actualizar(Medicamentos medicamento);

    /**
     * Elimina un medicamento de la base de datos
     * @param id ID del medicamento a eliminar
     * @return true si la eliminación fue exitosa
     */
    boolean eliminar(int id);
}