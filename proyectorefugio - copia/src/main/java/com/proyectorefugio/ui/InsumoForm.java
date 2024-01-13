/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.InsumoService;
import com.proyectorefugio.models.Insumo;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

/**
 *
 * @author josef
 */
public class InsumoForm extends JFrame {

    private JTextField nombreField;
    private JTextField cantidadField;
    private JDateChooser fechaCaducidadChooser;
    private JTextField unidadMedidaField;
    private JDateChooser fechaLoteChooser;
    private JButton guardarButton;

    private InsumoService insumoService;
    private InsumoFormListener insumoFormListener;
    private int insumoId;

    public InsumoForm(int insumoId, InsumoService insumoService) {
        this.insumoId = insumoId;
        this.insumoService = insumoService;
        initComponents();
        if (insumoId != 0) {
            cargarInsumoExistente(insumoId);
        }
    }

    private void initComponents() {
        setTitle("Formulario de Insumo");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        nombreField = new JTextField(15);
        cantidadField = new JTextField(15);
        fechaCaducidadChooser = new JDateChooser();
        unidadMedidaField = new JTextField(15);
        fechaLoteChooser = new JDateChooser();
        guardarButton = new JButton("Guardar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarInsumo();
            }
        });

        setLayout(new GridLayout(6, 2));
        add(new JLabel("Nombre:"));
        add(nombreField);
        add(new JLabel("Cantidad:"));
        add(cantidadField);
        add(new JLabel("Fecha Caducidad:"));
        add(fechaCaducidadChooser);
        add(new JLabel("Unidad Medida:"));
        add(unidadMedidaField);
        add(new JLabel("Fecha Lote:"));
        add(fechaLoteChooser);
        add(new JLabel());
        add(guardarButton);
    }

    private void cargarInsumoExistente(int insumoId) {
        Insumo insumo = insumoService.leerInsumoPorID(insumoId);
        if (insumo != null) {
            nombreField.setText(insumo.getNombre());
            cantidadField.setText(String.valueOf(insumo.getCantidad()));
            fechaCaducidadChooser.setDate(insumo.getFechaCaducidad());
            unidadMedidaField.setText(insumo.getUnidadMedida());
            fechaLoteChooser.setDate(insumo.getFechaLote());
        }
    }

    private void guardarInsumo() {
        String nombre = nombreField.getText();
        int cantidad = Integer.parseInt(cantidadField.getText());
        Date fechaCaducidad = new Date(fechaCaducidadChooser.getDate().getTime());
        String unidadMedida = unidadMedidaField.getText();
        Date fechaLote = new Date(fechaLoteChooser.getDate().getTime());

        if (insumoId > 0) {
            insumoService.actualizarInsumo(insumoId,nombre, cantidad, fechaCaducidad, unidadMedida, fechaLote);
        } else {
            insumoService.crearInsumo(nombre, cantidad, fechaCaducidad, unidadMedida, fechaLote);
        }

        if (insumoFormListener != null) {
            insumoFormListener.insumoGuardado(insumoId);
        }

        dispose();
    }

    public void setInsumoFormListener(InsumoFormListener listener) {
        this.insumoFormListener = listener;
    }
}
