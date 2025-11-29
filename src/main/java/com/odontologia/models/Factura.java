package com.odontologia.models;

import java.time.LocalDate;

/**
 * MODELO FACTURA
 * Esta clase representa una factura de servicios
 * Cada objeto = 1 fila en la tabla "facturas"
 */
public class Factura {

    // ========== ATRIBUTOS ==========

    /**
     * ID unico de la factura (clave primaria)
     */
    private Integer idFactura;

    /**
     * Numero de factura (ej: "FAC-001")
     */
    private String numeroFactura;

    /**
     * Fecha de emision de la factura
     */
    private LocalDate fecha;

    /**
     * Monto total de la factura
     */
    private Double montoTotal;

    /**
     * ID del paciente al que pertenece la factura
     */
    private Integer idPaciente;

    // ========== CONSTRUCTORES ==========

    /**
     * Constructor vacio
     */
    public Factura() {
    }

    /**
     * Constructor sin ID (para nuevas facturas)
     */
    public Factura(String numeroFactura, LocalDate fecha, Double montoTotal, Integer idPaciente) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.idPaciente = idPaciente;
    }

    /**
     * Constructor completo con ID (para actualizar)
     */
    public Factura(Integer idFactura, String numeroFactura, LocalDate fecha, Double montoTotal, Integer idPaciente) {
        this.idFactura = idFactura;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.idPaciente = idPaciente;
    }

    // ========== GETTERS ==========

    public Integer getIdFactura() {
        return idFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    // ========== SETTERS ==========

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    // ========== METODOS OVERRIDE ==========

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", fecha=" + fecha +
                ", montoTotal=" + montoTotal +
                ", idPaciente=" + idPaciente +
                '}';
    }
}