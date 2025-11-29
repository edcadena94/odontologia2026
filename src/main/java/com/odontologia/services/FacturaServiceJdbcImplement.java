package com.odontologia.services;

// Importamos el modelo Factura
import com.odontologia.models.Factura;
// Importamos el repositorio de facturas
import com.odontologia.repositories.FacturaRepository;

// Importamos Connection para la base de datos
import java.sql.Connection;
// Importamos List para listas
import java.util.List;

/**
 * CLASE FACTURA SERVICE JDBC IMPLEMENT
 *
 * PARA QUE SIRVE:
 * - Esta clase IMPLEMENTA la interface FacturaService
 * - Contiene la LOGICA DE NEGOCIO para facturas
 * - Valida los datos antes de enviarlos al Repository
 * - Actua como intermediario entre Controller y Repository
 *
 * FLUJO:
 * Controller -> Service (valida) -> Repository -> BD
 */
public class FacturaServiceJdbcImplement implements FacturaService {

    // ========== ATRIBUTO ==========

    /**
     * Repositorio de facturas
     * Se usa para acceder a la base de datos
     */
    private final FacturaRepository facturaRepository;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     * Crea internamente el repositorio con esa conexion
     *
     * @param conn Conexion activa a MySQL
     *
     * EJEMPLO:
     * Connection conn = Conexion.getConnection();
     * FacturaService service = new FacturaServiceJdbcImplement(conn);
     */
    public FacturaServiceJdbcImplement(Connection conn) {
        this.facturaRepository = new FacturaRepository(conn);
    }

    // ========== METODO GUARDAR ==========

    /**
     * Guarda una nueva factura con validaciones
     *
     * VALIDACIONES:
     * 1. La factura no puede ser null
     * 2. El numero de factura es obligatorio
     * 3. La fecha es obligatoria
     * 4. El monto total es obligatorio
     * 5. El ID del paciente es obligatorio
     *
     * @param factura La factura a guardar
     * @return true si se guardo correctamente, false si fallo validacion
     */
    @Override
    public boolean guardar(Factura factura) {
        // Validaciones: todos los campos obligatorios
        if (factura == null ||
                factura.getNumeroFactura() == null || factura.getNumeroFactura().trim().isEmpty() ||
                factura.getFecha() == null ||
                factura.getMontoTotal() == null ||
                factura.getIdPaciente() == null) {
            return false;
        }

        // Si paso todas las validaciones, guardamos
        return facturaRepository.guardar(factura);
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza una factura existente con validaciones
     *
     * VALIDACIONES:
     * 1. La factura no puede ser null
     * 2. Debe tener un ID valido
     *
     * @param factura La factura a actualizar
     * @return true si se actualizo correctamente
     */
    @Override
    public boolean actualizar(Factura factura) {
        // Validacion: Debe tener un ID valido para actualizar
        if (factura == null || factura.getIdFactura() == null || factura.getIdFactura() <= 0) {
            return false;
        }

        // Si paso la validacion, actualizamos
        return facturaRepository.actualizar(factura);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina una factura por su ID
     *
     * VALIDACION: El ID debe ser mayor a 0
     *
     * @param idFactura ID de la factura a eliminar
     * @return true si se elimino correctamente
     */
    @Override
    public boolean eliminar(int idFactura) {
        // Validacion: ID debe ser positivo
        if (idFactura <= 0) {
            return false;
        }
        return facturaRepository.eliminar(idFactura);
    }

    // ========== METODO BUSCAR POR ID ==========

    /**
     * Busca una factura por su ID
     *
     * @param idFactura ID de la factura a buscar
     * @return La factura encontrada o null
     */
    @Override
    public Factura buscarPorId(int idFactura) {
        // Validacion: ID debe ser positivo
        if (idFactura <= 0) {
            return null;
        }
        return facturaRepository.buscarPorId(idFactura);
    }

    // ========== METODO LISTAR TODAS ==========

    /**
     * Obtiene todas las facturas
     *
     * @return Lista con todas las facturas
     */
    @Override
    public List<Factura> listarTodas() {
        return facturaRepository.obtenerTodas();
    }
}