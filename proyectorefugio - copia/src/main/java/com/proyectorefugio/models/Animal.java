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
public class Animal {

    private int id;
    private String nombre;
    private String especie;
    private int edad;
    private Date fechaIngreso;
    private boolean adoptado;
    private Date fechaAdopcion;
    private int adoptanteId;
    private int insumoId;

    public Animal(int id, String nombre, String especie, int edad, Date fechaIngreso,
                  boolean adoptado, Date fechaAdopcion, int adoptanteId, int insumoId) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.fechaIngreso = fechaIngreso;
        this.adoptado = adoptado;
        this.fechaAdopcion = fechaAdopcion;
        this.adoptanteId = adoptanteId;
        this.insumoId = insumoId;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public int getEdad() {
        return edad;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isAdoptado() {
        return adoptado;
    }

    public Date getFechaAdopcion() {
        return fechaAdopcion;
    }

    public int getAdoptanteId() {
        return adoptanteId;
    }

    public int getInsumoId() {
        return insumoId;
    }
}
