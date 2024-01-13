/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.models;

import java.sql.Date;

/**
 *
 * @author josef
 */
public class Insumo {

    private int id;
    private String nombre;
    private int cantidad;
    private Date fechaCaducidad;
    private String unidadMedida;
    private Date fechaLote;

    public Insumo(int id, String nombre, int cantidad, Date fechaCaducidad,
                  String unidadMedida, Date fechaLote) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
        this.unidadMedida = unidadMedida;
        this.fechaLote = fechaLote;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public Date getFechaLote() {
        return fechaLote;
    }
}
