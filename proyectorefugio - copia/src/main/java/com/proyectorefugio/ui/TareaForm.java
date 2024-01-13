/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.TareaService;
import com.proyectorefugio.models.Tarea;
import com.proyectorefugio.models.Colaborador;
import com.proyectorefugio.services.ColaboradorService;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author josef
 */
public class TareaForm extends JFrame {

    private JTextField descripcionField;
    private JDateChooser fechaInicioChooser;
    private JSpinner horaInicioChooser;
    private JDateChooser fechaFinChooser;
    private JSpinner horaFinChooser;
    private JTextField asignadaAField;
    private JButton guardarButton;
    private JComboBox<Colaborador> colaboradorComboBox;


    private TareaService tareaService;
    private ColaboradorService colaboradorService;
    private TareaFormListener tareaFormListener;
    private int tareaId;

    public TareaForm(int tareaId, TareaService tareaService, ColaboradorService colaboradorService) {
        this.tareaId = tareaId;
        this.tareaService = tareaService;
        this.colaboradorService = colaboradorService;
        colaboradorComboBox = new JComboBox<>();
        List<Colaborador> colaboradores = colaboradorService.obtenerTodosLosColaboradores(); // Cambia esto según cómo obtienes la lista de colaboradores
        for (Colaborador colaborador : colaboradores) {
            colaboradorComboBox.addItem(colaborador);
        }

        initComponents();
        if (tareaId != 0) {
            cargarTareaExistente(tareaId);
        }
    }

    private void initComponents() {
        setTitle("Formulario de Tarea");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        descripcionField = new JTextField(20);
        fechaInicioChooser = new JDateChooser();
        horaInicioChooser = new JSpinner(new SpinnerDateModel());
        fechaFinChooser = new JDateChooser();
        horaFinChooser = new JSpinner(new SpinnerDateModel());
        asignadaAField = new JTextField(20);
        guardarButton = new JButton("Guardar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarTarea();
            }
        });

        setLayout(new GridLayout(7, 2));
        add(new JLabel("Descripción:"));
        add(descripcionField);
        add(new JLabel("Fecha de Inicio:"));
        add(fechaInicioChooser);
        add(new JLabel("Hora de Inicio:"));
        add(horaInicioChooser);
        add(new JLabel("Fecha de Fin:"));
        add(fechaFinChooser);
        add(new JLabel("Hora de Fin:"));
        add(horaFinChooser);
        add(new JLabel("Asignada A:"));
        add(colaboradorComboBox);
        add(new JLabel());
        add(guardarButton);
    }

    private void cargarTareaExistente(int tareaId) {
        Tarea tarea = tareaService.leerTareaPorID(tareaId);
        if (tarea != null) {
            descripcionField.setText(tarea.getDescripcion());
            fechaInicioChooser.setDate(tarea.getFechaInicio());
            horaInicioChooser.setValue(new Time(tarea.getHoraInicio().getTime()));
            fechaFinChooser.setDate(tarea.getFechaFin());
            horaFinChooser.setValue(new Time(tarea.getHoraFin().getTime()));
            asignadaAField.setText(String.valueOf(tarea.getAsignadaA()));
        }
    }

    private void guardarTarea() {
        String descripcion = descripcionField.getText();
        Colaborador colaboradorSeleccionado = (Colaborador) colaboradorComboBox.getSelectedItem();
        int asignadaA = colaboradorSeleccionado.getId();
        
        Date horaInicioDate = (Date) horaInicioChooser.getValue();
        Date horaFinDate = (Date) horaFinChooser.getValue();
        
        Time horaInicio = new Time(horaInicioDate.getTime());
        Time horaFin = new Time(horaFinDate.getTime());
        
        java.util.Date fechaFinDate = fechaFinChooser.getDate();
        
        java.sql.Date fechaFin = null;

        // Verificar si se seleccionó una fecha
        if (fechaFinDate != null) {
            // Convertir la fecha seleccionada a java.sql.Date
            fechaFin = new java.sql.Date(fechaFinDate.getTime());

            // Resto del código ...
        } else {
            // Si no se seleccionó ninguna fecha, usar null
            fechaFin = null;

            // Resto del código ...
        }
        
        java.util.Date fechaInicioDate = fechaFinChooser.getDate();
        java.sql.Date fechaInicio = null;

        // Verificar si se seleccionó una fecha
        if (fechaFinDate != null) {
            // Convertir la fecha seleccionada a java.sql.Date
            fechaInicio = new java.sql.Date(fechaInicioDate.getTime());

            // Resto del código ...
        } else {
            // Si no se seleccionó ninguna fecha, usar null
            fechaInicio = null;

            // Resto del código ...
        }
        

        if (tareaId > 0) {
            tareaService.actualizarTarea(tareaId, descripcion, fechaInicio, horaInicio, fechaFin, horaFin, asignadaA);
        } else {
            tareaService.crearTarea(descripcion, fechaInicio, horaInicio, fechaFin, horaFin, asignadaA);
        }

        if (tareaFormListener != null) {
            tareaFormListener.tareaGuardada(tareaId);
        }

        dispose();
    }

    public void setTareaFormListener(TareaFormListener listener) {
        this.tareaFormListener = listener;
    }
}
