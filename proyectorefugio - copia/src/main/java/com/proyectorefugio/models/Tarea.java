/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.models;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author josef
 */
public class Tarea {

    private int id;
    private String descripcion;
    private Date fechaInicio;
    private Time horaInicio;
    private Date fechaFin;
    private Time horaFin;
    private int asignadaA;

    public Tarea(int id, String descripcion, Date fechaInicio, Time horaInicio,
                 Date fechaFin, Time horaFin, int asignadaA) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.asignadaA = asignadaA;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public int getAsignadaA() {
        return asignadaA;
    }
}
