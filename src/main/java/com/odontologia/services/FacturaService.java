package com.odontologia.services;

// Importamos el modelo Factura
import com.odontologia.models.Factura;
// Importamos List para listas
import java.util.List;

/**
 * INTERFACE FACTURA SERVICE
 *
 * PARA QUE SIRVE:
 * - Define los metodos que debe tener el servicio de facturas
 * - Es una INTERFACE, solo declara metodos, no los implementa
 * - La implementacion real esta en FacturaServiceJdbcImplement.java
 *
 * OPERACIONES QUE MANEJA:
 * - Guardar nuevas facturas
 * - Actualizar facturas existentes
 * - Eliminar facturas
 * - Buscar facturas por ID
 * - Listar todas las facturas
 *
 * FLUJO DE LA APLICACION:
 * Controller -> Service -> Repository -> Base de Datos
 */
public interface FacturaService {

    // ========== METODOS CRUD ==========

    /**
     * Guarda una nueva factura en el sistema
     *
     * @param factura Objeto Factura a guardar
     * @return true si se guardo correctamente, false si hubo error
     *
     * EJEMPLO:
     * Factura nueva = new Factura("FAC-001", LocalDate.now(), 150.00, 1);
     * boolean guardada = facturaService.guardar(nueva);
     */
    boolean guardar(Factura factura);

    /**
     * Actualiza una factura existente
     *
     * @param factura Objeto Factura con los datos actualizados
     * @return true si se actualizo correctamente, false si hubo error
     *
     * EJEMPLO:
     * Factura factura = facturaService.buscarPorId(1);
     * factura.setMontoTotal(200.00);
     * boolean actualizada = facturaService.actualizar(factura);
     */
    boolean actualizar(Factura factura);

    /**
     * Elimina una factura por su ID
     *
     * @param idFactura ID de la factura a eliminar
     * @return true si se elimino correctamente, false si hubo error
     *
     * EJEMPLO:
     * boolean eliminada = facturaService.eliminar(5);
     */
    boolean eliminar(int idFactura);

    // ========== METODOS DE CONSULTA ==========

    /**
     * Busca una factura por su ID
     *
     * @param idFactura ID de la factura a buscar
     * @return La factura encontrada o null si no existe
     *
     * EJEMPLO:
     * Factura factura = facturaService.buscarPorId(1);
     * if (factura != null) {
     *     System.out.println("Monto: $" + factura.getMontoTotal());
     * }
     */
    Factura buscarPorId(int idFactura);

    /**
     * Lista todas las facturas del sistema
     *
     * @return Lista con todas las facturas
     *
     * EJEMPLO:
     * List<Factura> facturas = facturaService.listarTodas();
     * for (Factura f : facturas) {
     *     System.out.println(f.getNumeroFactura() + " - $" + f.getMontoTotal());
     * }
     */
    List<Factura> listarTodas();
}