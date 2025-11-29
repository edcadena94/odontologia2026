package com.odontologia.services;

import com.odontologia.models.Medicamentos;
import com.odontologia.repositories.MedicamentosRepository;
import com.odontologia.repositories.MedicamentosRepositoryJdbcImplement;

import java.sql.Connection;
import java.util.List;

/**
 * Implementación de MedicamentosService usando JDBC
 * Esta clase actúa como intermediario entre el controlador y el repositorio
 * Aquí se puede agregar lógica de negocio adicional como validaciones
 */
public class MedicamentosServiceJdbcImplement implements MedicamentosService {

    // Repositorio que maneja las operaciones de base de datos
    private MedicamentosRepository medicamentosRepository;

    /**
     * Constructor que recibe la conexión a la base de datos
     * @param conn Conexión activa a MySQL
     */
    public MedicamentosServiceJdbcImplement(Connection conn) {
        // Inicializamos el repositorio con la conexión
        this.medicamentosRepository = new MedicamentosRepositoryJdbcImplement(conn);
    }

    /**
     * Obtiene todos los medicamentos delegando al repositorio
     * @return Lista de medicamentos
     */
    @Override
    public List<Medicamentos> listarTodos() {
        return medicamentosRepository.listarTodos();
    }

    /**
     * Busca un medicamento por ID delegando al repositorio
     * @param id ID del medicamento
     * @return Medicamento encontrado o null
     */
    @Override
    public Medicamentos buscarPorId(int id) {
        return medicamentosRepository.buscarPorId(id);
    }

    /**
     * Guarda un nuevo medicamento delegando al repositorio
     * @param medicamento Datos del medicamento a guardar
     * @return true si se guardó exitosamente
     */
    @Override
    public boolean guardar(Medicamentos medicamento) {
        return medicamentosRepository.guardar(medicamento);
    }

    /**
     * Actualiza un medicamento existente delegando al repositorio
     * @param medicamento Datos actualizados del medicamento
     * @return true si se actualizó exitosamente
     */
    @Override
    public boolean actualizar(Medicamentos medicamento) {
        return medicamentosRepository.actualizar(medicamento);
    }

    /**
     * Elimina un medicamento delegando al repositorio
     * @param id ID del medicamento a eliminar
     * @return true si se eliminó exitosamente
     */
    @Override
    public boolean eliminar(int id) {
        return medicamentosRepository.eliminar(id);
    }
}