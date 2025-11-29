package com.odontologia.repositories;

// Importamos el modelo Factura
import com.odontologia.models.Factura;

// Importamos las clases de SQL necesarias
import java.sql.*;
// Importamos LocalDate para manejar fechas
import java.time.LocalDate;
// Importamos ArrayList para crear listas
import java.util.ArrayList;
// Importamos List como tipo de retorno
import java.util.List;

/**
 * CLASE FACTURA REPOSITORY
 *
 * PARA QUE SIRVE:
 * - Esta clase se encarga de comunicarse con la BASE DE DATOS
 * - Realiza todas las operaciones CRUD para la tabla "facturas"
 * - CRUD = Create (crear), Read (leer), Update (actualizar), Delete (eliminar)
 *
 * COMO SE USA:
 * 1. Se crea una instancia pasando la conexion a la base de datos
 * 2. Se llaman los metodos para guardar, buscar, eliminar facturas
 *
 * EJEMPLO:
 * Connection conn = Conexion.getConnection();
 * FacturaRepository repo = new FacturaRepository(conn);
 * List<Factura> facturas = repo.obtenerTodas();
 */
public class FacturaRepository {

    // ========== ATRIBUTO ==========

    /**
     * Conexion a la base de datos MySQL
     * Se recibe en el constructor y se usa en todos los metodos
     * Es "final" porque no debe cambiar despues de crearse
     */
    private final Connection conn;

    // ========== CONSTRUCTOR ==========

    /**
     * Constructor que recibe la conexion a la base de datos
     *
     * @param conn Conexion activa a MySQL (obtenida de Conexion.getConnection())
     *
     * EJEMPLO DE USO:
     * Connection conn = Conexion.getConnection();
     * FacturaRepository repo = new FacturaRepository(conn);
     */
    public FacturaRepository(Connection conn) {
        this.conn = conn;
    }

    // ========== METODO GUARDAR (INSERT o UPDATE) ==========

    /**
     * Guarda una factura en la base de datos
     *
     * LOGICA:
     * - Si la factura NO tiene ID (es null o 0) -> hace INSERT (nueva factura)
     * - Si la factura SI tiene ID -> hace UPDATE (actualiza factura existente)
     *
     * @param factura La factura a guardar
     * @return true si se guardo correctamente, false si hubo error
     *
     * EJEMPLO PARA INSERTAR:
     * Factura nuevaFactura = new Factura("FAC-001", LocalDate.now(), 150.00, 1);
     * boolean guardado = repo.guardar(nuevaFactura);
     *
     * EJEMPLO PARA ACTUALIZAR:
     * Factura factura = repo.buscarPorId(1);
     * factura.setMontoTotal(200.00);
     * boolean actualizado = repo.guardar(factura);
     */
    public boolean guardar(Factura factura) {
        String sql;

        // Verificamos si es una factura nueva o existente
        boolean esNuevo = factura.getIdFactura() == null || factura.getIdFactura() == 0;

        if (esNuevo) {
            // Es una NUEVA factura -> usamos INSERT
            // Los ? son parametros que se llenan despues con setString, setInt, etc.
            sql = "INSERT INTO facturas (numero_factura, fecha, monto_total, id_paciente) VALUES (?, ?, ?, ?)";
        } else {
            // Es una factura EXISTENTE -> usamos UPDATE
            sql = "UPDATE facturas SET numero_factura = ?, fecha = ?, monto_total = ?, id_paciente = ? WHERE id_factura = ?";
        }

        // Ejecutamos la consulta SQL
        // try-with-resources: cierra automaticamente el PreparedStatement al terminar
        // Statement.RETURN_GENERATED_KEYS: nos permite obtener el ID generado automaticamente
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Llenamos los parametros (los ?) con los valores de la factura
            // El numero indica la posicion del ? (empezando en 1)
            stmt.setString(1, factura.getNumeroFactura());  // Primer ?
            stmt.setDate(2, Date.valueOf(factura.getFecha())); // Segundo ? - Convertimos LocalDate a java.sql.Date
            stmt.setDouble(3, factura.getMontoTotal());     // Tercer ?
            stmt.setInt(4, factura.getIdPaciente());        // Cuarto ?

            // Si es UPDATE, necesitamos el ID como quinto parametro
            if (!esNuevo) {
                stmt.setInt(5, factura.getIdFactura());     // Quinto ? (solo en UPDATE)
            }

            // Ejecutamos la consulta y obtenemos cuantas filas fueron afectadas
            int affectedRows = stmt.executeUpdate();

            // Si es INSERT y se inserto correctamente, obtenemos el ID generado
            if (esNuevo && affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // Asignamos el ID generado a la factura
                    factura.setIdFactura(rs.getInt(1));
                }
            }

            // Retornamos true si al menos 1 fila fue afectada
            return affectedRows > 0;

        } catch (SQLException e) {
            // Si hay error, lo imprimimos y retornamos false
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO ACTUALIZAR ==========

    /**
     * Actualiza una factura existente
     * Es un alias para el metodo guardar()
     *
     * @param factura La factura a actualizar (debe tener ID)
     * @return true si se actualizo correctamente
     *
     * EJEMPLO:
     * Factura factura = repo.buscarPorId(1);
     * factura.setMontoTotal(300.00);
     * repo.actualizar(factura);
     */
    public boolean actualizar(Factura factura) {
        return guardar(factura);
    }

    // ========== METODO ELIMINAR ==========

    /**
     * Elimina una factura de la base de datos por su ID
     *
     * @param id El ID de la factura a eliminar
     * @return true si se elimino correctamente, false si hubo error o no existia
     *
     * EJEMPLO:
     * boolean eliminado = repo.eliminar(5);
     * if (eliminado) {
     *     System.out.println("Factura eliminada");
     * }
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM facturas WHERE id_factura = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos el ID de la factura a eliminar
            stmt.setInt(1, id);

            // Ejecutamos y verificamos si se elimino alguna fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== METODO BUSCAR POR ID ==========

    /**
     * Busca una factura por su ID
     *
     * @param id El ID de la factura a buscar
     * @return La factura encontrada, o null si no existe
     *
     * EJEMPLO:
     * Factura factura = repo.buscarPorId(1);
     * if (factura != null) {
     *     System.out.println("Factura encontrada: " + factura.getNumeroFactura());
     * } else {
     *     System.out.println("No se encontro la factura");
     * }
     */
    public Factura buscarPorId(int id) {
        String sql = "SELECT * FROM facturas WHERE id_factura = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos el ID a buscar
            stmt.setInt(1, id);

            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            // Si hay resultado, lo mapeamos a un objeto Factura
            if (rs.next()) {
                return mapearFactura(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si no se encontro, retornamos null
        return null;
    }

    // ========== METODO OBTENER TODAS ==========

    /**
     * Obtiene todas las facturas de la base de datos
     * Las ordena por fecha descendente (mas recientes primero)
     *
     * @return Lista con todas las facturas
     *
     * EJEMPLO:
     * List<Factura> facturas = repo.obtenerTodas();
     * for (Factura f : facturas) {
     *     System.out.println(f.getNumeroFactura() + " - $" + f.getMontoTotal());
     * }
     */
    public List<Factura> obtenerTodas() {
        // Creamos una lista vacia para guardar las facturas
        List<Factura> facturas = new ArrayList<>();

        // ORDER BY fecha DESC: ordena por fecha, mas recientes primero
        String sql = "SELECT * FROM facturas ORDER BY fecha DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery();

            // Recorremos todos los resultados
            while (rs.next()) {
                // Mapeamos cada fila a un objeto Factura y lo agregamos a la lista
                facturas.add(mapearFactura(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornamos la lista (puede estar vacia si no hay facturas)
        return facturas;
    }

    // ========== METODO PRIVADO: MAPEAR RESULTSET A FACTURA ==========

    /**
     * METODO AUXILIAR PRIVADO
     * Convierte una fila del ResultSet a un objeto Factura
     *
     * Este metodo se usa internamente para no repetir codigo
     * Cada vez que leemos una factura de la BD, usamos este metodo
     *
     * @param rs ResultSet posicionado en una fila de resultados
     * @return Objeto Factura con los datos de esa fila
     * @throws SQLException Si hay error al leer los datos
     */
    private Factura mapearFactura(ResultSet rs) throws SQLException {
        // Creamos un nuevo objeto Factura vacio
        Factura factura = new Factura();

        // Extraemos cada columna y la asignamos al objeto
        // rs.getInt("nombre_columna") obtiene el valor entero de esa columna
        // rs.getString("nombre_columna") obtiene el valor texto de esa columna
        // rs.getDouble("nombre_columna") obtiene el valor decimal de esa columna
        // rs.getDate("nombre_columna") obtiene el valor fecha de esa columna

        factura.setIdFactura(rs.getInt("id_factura"));
        factura.setNumeroFactura(rs.getString("numero_factura"));

        // Convertimos java.sql.Date a LocalDate usando toLocalDate()
        factura.setFecha(rs.getDate("fecha").toLocalDate());

        factura.setMontoTotal(rs.getDouble("monto_total"));
        factura.setIdPaciente(rs.getInt("id_paciente"));

        // Retornamos el objeto con todos los datos
        return factura;
    }
}