package com.odontologia.models;

/**
 * MODELO MEDICAMENTOS
 * Esta clase representa un medicamento del inventario
 * Cada objeto = 1 fila en la tabla "medicamentos"
 */
public class Medicamentos {

    // ========== ATRIBUTOS ==========

    /**
     * ID unico del medicamento (clave primaria)
     */
    private long idMedicamento;

    /**
     * Nombre del medicamento
     * Ejemplo: "Ibuprofeno 400mg"
     */
    private String nombre;

    /**
     * Descripcion del medicamento
     * Ejemplo: "Antiinflamatorio no esteroideo"
     */
    private String descripcion;

    /**
     * Cantidad en stock (inventario disponible)
     */
    private long stock;

    // ========== CONSTRUCTORES ==========

    /**
     * Constructor vacio
     */
    public Medicamentos() {
    }

    /**
     * Constructor completo
     */
    public Medicamentos(long idMedicamento, String nombre, String descripcion, long stock) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    // ========== GETTERS ==========

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public long getStock() {
        return stock;
    }

    // ========== SETTERS ==========

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    // ========== METODOS OVERRIDE ==========

    @Override
    public String toString() {
        return "Medicamentos{" +
                "idMedicamento=" + idMedicamento +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", stock=" + stock +
                '}';
    }
}