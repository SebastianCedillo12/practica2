/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.models.Adoptante;
import com.proyectorefugio.services.AdoptanteService;
import com.proyectorefugio.services.AnimalService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

/**
 *
 * @author josef
 */
public class AdopcionForm extends JFrame {

    private JDateChooser fechaAdopcionChooser;
    private JComboBox<Adoptante> adoptanteComboBox;
    private JButton guardarButton;

    private AdoptanteService adoptanteService;
    private AnimalService animalService;
    private AdopcionFormListener adopcionFormListener;
    private int animalId;

    public AdopcionForm(int animalId, AnimalService animalService, AdoptanteService adoptanteService) {
        this.animalId = animalId;
        this.adoptanteService = adoptanteService;
        this.animalService = animalService;
        initComponents();
    }

    private void initComponents() {
        setTitle("Formulario de Adopción");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        fechaAdopcionChooser = new JDateChooser();
        adoptanteComboBox = new JComboBox<>();
        List<Adoptante> adoptantes = adoptanteService.obtenerTodosLosAdoptantes();
        for (Adoptante adoptante : adoptantes) {
            adoptanteComboBox.addItem(adoptante);
        }
        guardarButton = new JButton("Guardar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAdopcion();
            }
        });

        setLayout(new GridLayout(3, 2));
        add(new JLabel("Fecha de Adopción:"));
        add(fechaAdopcionChooser);
        add(new JLabel("Adoptante:"));
        add(adoptanteComboBox);
        add(new JLabel());
        add(guardarButton);
    }

    private void guardarAdopcion() {
        Date fechaAdopcionUtil  = fechaAdopcionChooser.getDate();
        Adoptante adoptanteSeleccionado = (Adoptante) adoptanteComboBox.getSelectedItem();
        int adoptanteId = adoptanteSeleccionado.getId();

        // Guardar la adopción (código necesario)
        java.sql.Date fechaAdopcionSql = new java.sql.Date(fechaAdopcionUtil.getTime());
        animalService.AdoptarAnimal(animalId, fechaAdopcionSql, adoptanteId);

        if (adopcionFormListener != null) {
            adopcionFormListener.adopcionGuardada(animalId);
        }

        dispose();
    }

    public void setAdopcionFormListener(AdopcionFormListener listener) {
        this.adopcionFormListener = listener;
    }
}
