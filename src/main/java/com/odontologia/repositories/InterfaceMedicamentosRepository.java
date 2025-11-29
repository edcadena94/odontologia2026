package com.odontologia.repositories;

// Importamos el modelo Medicamentos
import com.odontologia.models.Medicamentos;

// Importamos List para retornar listas
import java.util.List;

/**
 * INTERFACE MEDICAMENTOS REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Es una INTERFACE que define los metodos para acceder a medicamentos en la BD
 * - Las interfaces solo DECLARAN metodos, no los implementan
 * - La implementacion real esta en MedicamentosRepository.java
 *
 * METODOS QUE DEFINE:
 * - listar(): Obtiene todos los medicamentos
 * - porId(): Busca un medicamento por su ID
 * - guardar(): Guarda o actualiza un medicamento
 * - eliminar(): Elimina un medicamento por su ID
 *
 * POR QUE USAR INTERFACE:
 * - Permite cambiar la implementacion sin modificar el resto del codigo
 * - Facilita las pruebas unitarias
 * - Sigue el principio de "Programar hacia interfaces"
 */
public interface InterfaceMedicamentosRepository {

    /**
     * Obtiene todos los medicamentos de la base de datos
     *
     * @return Lista con todos los medicamentos
     *
     * EJEMPLO:
     * List<Medicamentos> lista = repo.listar();
     * for (Medicamentos m : lista) {
     *     System.out.println(m.getNombre());
     * }
     */
    List<Medicamentos> listar();

    /**
     * Busca un medicamento por su ID
     *
     * @param id El ID del medicamento a buscar
     * @return El medicamento encontrado, o null si no existe
     *
     * EJEMPLO:
     * Medicamentos med = repo.porId(1L);
     * if (med != null) {
     *     System.out.println("Encontrado: " + med.getNombre());
     * }
     */
    Medicamentos porId(Long id);

    /**
     * Guarda un medicamento en la base de datos
     * Si no tiene ID, lo inserta como nuevo
     * Si tiene ID, actualiza el existente
     *
     * @param articulo El medicamento a guardar
     *
     * EJEMPLO:
     * Medicamentos nuevo = new Medicamentos("Ibuprofeno", "Antiinflamatorio", 100);
     * repo.guardar(nuevo);
     */
    void guardar(Medicamentos articulo);

    /**
     * Elimina un medicamento de la base de datos
     *
     * @param id El ID del medicamento a eliminar
     *
     * EJEMPLO:
     * repo.eliminar(5L);
     */
    void eliminar(Long id);
}