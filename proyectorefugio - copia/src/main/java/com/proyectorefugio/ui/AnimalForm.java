/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.AnimalService;
import com.proyectorefugio.models.Animal;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

/**
 *
 * @author josef
 */
public class AnimalForm extends JDialog {

    private JTextField nombreField;
    private JComboBox<String> especieComboBox;
    private JTextField edadField;
    private JCheckBox adoptadoCheckBox;
    private JDateChooser fechaIngresoChooser;
    private JDateChooser fechaAdopcionChooser;
    private JButton guardarButton;
    private JButton cancelarButton;

    private AnimalService animalService;
    private AnimalFormListener animalFormListener;
    private int animalId;

    public AnimalForm(int animalId, AnimalService animalService) {
        this.animalId = animalId;
        this.animalService = animalService;
        initComponents();
        
        if (animalId > 0) {
            loadAnimalData();
        }
    }

    private void initComponents() {
        setTitle("Formulario de Animal");
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();

        JLabel especieLabel = new JLabel("Especie:");
        especieComboBox = new JComboBox<>(new String[]{"Perro", "Gato", "Otro"});

        JLabel edadLabel = new JLabel("Edad:");
        edadField = new JTextField();

        JLabel adoptadoLabel = new JLabel("Adoptado:");
        adoptadoCheckBox = new JCheckBox();

        JLabel fechaIngresoLabel = new JLabel("Fecha de Ingreso:");
        fechaIngresoChooser = new JDateChooser();

        JLabel fechaAdopcionLabel = new JLabel("Fecha de Adopción:");
        fechaAdopcionChooser = new JDateChooser();

        guardarButton = new JButton("Guardar");
        cancelarButton = new JButton("Cancelar");

        formPanel.add(nombreLabel);
        formPanel.add(nombreField);
        formPanel.add(especieLabel);
        formPanel.add(especieComboBox);
        formPanel.add(edadLabel);
        formPanel.add(edadField);
        formPanel.add(adoptadoLabel);
        formPanel.add(adoptadoCheckBox);
        formPanel.add(fechaIngresoLabel);
        formPanel.add(fechaIngresoChooser);
        formPanel.add(fechaAdopcionLabel);
        formPanel.add(fechaAdopcionChooser);
        formPanel.add(guardarButton);
        formPanel.add(cancelarButton);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAnimal();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(formPanel);
    }
    
    private void loadAnimalData() {
        Animal animal = animalService.leerAnimalPorID(animalId);
        nombreField.setText(animal.getNombre());
        especieComboBox.setSelectedItem(animal.getEspecie());
        edadField.setText(String.valueOf(animal.getEdad()));
        adoptadoCheckBox.setSelected(animal.isAdoptado());
        fechaIngresoChooser.setDate(animal.getFechaIngreso());

        if (animal.getFechaAdopcion() != null) {
            fechaAdopcionChooser.setDate(animal.getFechaAdopcion());
        }
    }

    private void guardarAnimal() {
        String nombre = nombreField.getText();
        String especie = (String) especieComboBox.getSelectedItem();
        int edad = Integer.parseInt(edadField.getText());
        boolean adoptado = adoptadoCheckBox.isSelected();
        Date fechaIngreso = new Date(fechaIngresoChooser.getDate().getTime());
        Date fechaAdopcion = null;

        if (fechaAdopcionChooser.getDate() != null) {
            fechaAdopcion = new Date(fechaAdopcionChooser.getDate().getTime());
        }


        if (animalId > 0) {
            animalService.actualizarAnimal(animalId, nombre, especie, edad, fechaIngreso, adoptado, fechaAdopcion, 0, 0);
        } else {
            animalService.crearAnimal(nombre, especie, edad, fechaIngreso, adoptado, fechaAdopcion, 0, 0);
        }

        if (animalFormListener != null) {
            animalFormListener.animalGuardado(animalId);
        }

        dispose();
    }

    public void setAnimalFormListener(AnimalFormListener listener) {
        this.animalFormListener = listener;
    }
}
