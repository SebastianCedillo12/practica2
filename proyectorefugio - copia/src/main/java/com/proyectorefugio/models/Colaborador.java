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
public class Colaborador {

    private int id;
    private String nombre;
    private String cedula;
    private String puesto;
    private Date fechaContratacion;
    private double salario;
    private String password;
    private String rol;

    public Colaborador(int id, String nombre, String cedula, String puesto, Date fechaContratacion,
                       double salario, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.puesto = puesto;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
        this.password = password;
        this.rol = rol;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public double getSalario() {
        return salario;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }
    
    @Override
    public String toString() {
        return nombre + " - " + cedula; // Suponiendo que "nombre" es el atributo que contiene el nombre del colaborador
    }
}
