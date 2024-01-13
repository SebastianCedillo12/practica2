/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.models.Adoptante;
import com.proyectorefugio.services.AdoptanteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author josef
 */
public class AdoptanteForm extends JFrame {

    private int adoptanteId;
    private AdoptanteService adoptanteService;
    private AdoptanteFormListener adoptanteFormListener;

    private JTextField nombreField;
    private JTextField direccionField;
    private JTextField telefonoField;
    private JTextField correoField;
    private JButton guardarButton;

    public AdoptanteForm(int adoptanteId, AdoptanteService adoptanteService) {
        this.adoptanteId = adoptanteId;
        this.adoptanteService = adoptanteService;
        initComponents();
        if (adoptanteId > 0) {
            loadAdoptanteData();
        }
    }

    private void initComponents() {
        setTitle("Formulario de Adoptante");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre:");
        nombreField = new JTextField();
        JLabel lblDireccion = new JLabel("Dirección:");
        direccionField = new JTextField();
        JLabel lblTelefono = new JLabel("Teléfono:");
        telefonoField = new JTextField();
        JLabel lblCorreo = new JLabel("Correo:");
        correoField = new JTextField();
        guardarButton = new JButton("Guardar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String direccion = direccionField.getText();
                String telefono = telefonoField.getText();
                String correo = correoField.getText();

                if (adoptanteId > 0) {
                    // Actualizar adoptante existente
                    adoptanteService.actualizarAdoptante(adoptanteId,nombre,direccion,telefono,correo);
                } else {
                    // Crear nuevo adoptante
                    adoptanteService.crearAdoptante(nombre,direccion,telefono,correo);
                }

                if (adoptanteFormListener != null) {
                    adoptanteFormListener.adoptanteGuardado(adoptanteId);
                }

                dispose(); // Cerrar el formulario después de guardar
            }
        });

        // Configurar el diseño del formulario
        contentPane.add(lblNombre);
        contentPane.add(nombreField);
        contentPane.add(lblDireccion);
        contentPane.add(direccionField);
        contentPane.add(lblTelefono);
        contentPane.add(telefonoField);
        contentPane.add(lblCorreo);
        contentPane.add(correoField);
        contentPane.add(new JLabel());
        contentPane.add(guardarButton);

        // Configurar el diseño del formulario
        pack();
    }

    private void loadAdoptanteData() {
        Adoptante adoptante = adoptanteService.leerAdoptantePorID(adoptanteId);
        if (adoptante != null) {
            nombreField.setText(adoptante.getNombre());
            direccionField.setText(adoptante.getDireccion());
            telefonoField.setText(adoptante.getTelefono());
            correoField.setText(adoptante.getCorreo());
        }
    }

    public void setAdoptanteFormListener(AdoptanteFormListener listener) {
        this.adoptanteFormListener = listener;
    }

    // Métodos adicionales del formulario
    // ...
}
