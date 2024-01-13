/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;


import com.proyectorefugio.services.ColaboradorService;
import com.proyectorefugio.models.Colaborador;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 *
 * @author josef
 */
public class ColaboradorForm extends JFrame {

    private JTextField nombreField;
    private JTextField cedulaField;
    private JTextField puestoField;
    private JDateChooser fechaContratacionField;
    private JTextField salarioField;
    private JTextField rolField;
    private JButton guardarButton;

    private ColaboradorService colaboradorService;
    private ColaboradorFormListener colaboradorFormListener;
    private int colaboradorId;

    public ColaboradorForm(int colaboradorId, ColaboradorService colaboradorService) {
        this.colaboradorId = colaboradorId;
        this.colaboradorService = colaboradorService;
        initComponents();

        if (colaboradorId > 0) {
            cargarDatosColaborador(colaboradorId);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Formulario de Colaborador");
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        nombreField = new JTextField(20);
        cedulaField = new JTextField(20);
        puestoField = new JTextField(20);
        fechaContratacionField = new JDateChooser();
        salarioField = new JTextField(20);
        rolField = new JTextField(20);
        guardarButton = new JButton("Guardar");

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarColaborador();
            }
        });

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(nombreField);
        formPanel.add(new JLabel("Cédula:"));
        formPanel.add(cedulaField);
        formPanel.add(new JLabel("Puesto:"));
        formPanel.add(puestoField);
        formPanel.add(new JLabel("Fecha Contratación:"));
        formPanel.add(fechaContratacionField);
        formPanel.add(new JLabel("Salario:"));
        formPanel.add(salarioField);
        formPanel.add(new JLabel("Rol:"));
        formPanel.add(rolField);
        formPanel.add(new JLabel(""));
        formPanel.add(guardarButton);

        add(formPanel);
    }

    private void cargarDatosColaborador(int colaboradorId) {
        Colaborador colaborador = colaboradorService.leerColaboradorPorID(colaboradorId);
        if (colaborador != null) {
            nombreField.setText(colaborador.getNombre());
            cedulaField.setText(colaborador.getCedula());
            puestoField.setText(colaborador.getPuesto());
            fechaContratacionField.setDate(colaborador.getFechaContratacion());
            salarioField.setText(String.valueOf(colaborador.getSalario()));
            rolField.setText(colaborador.getRol());
        }
    }

    private void guardarColaborador() {
        String nombre = nombreField.getText();
        String cedula = cedulaField.getText();
        String puesto = puestoField.getText();
        java.util.Date fechaContratacionUtil = fechaContratacionField.getDate();
        double salario = Double.parseDouble(salarioField.getText());
        String rol = rolField.getText();
        
        java.sql.Date fechaContratacion = new java.sql.Date(fechaContratacionUtil.getTime());

        if (colaboradorId > 0) {
            colaboradorService.actualizarColaborador(colaboradorId,nombre, cedula, puesto,  fechaContratacion, salario,"", rol);
        } else {
            colaboradorService.crearColaborador(nombre, cedula, puesto,  fechaContratacion, salario,"", rol);
        }

        if (colaboradorFormListener != null) {
            colaboradorFormListener.colaboradorGuardado(colaboradorId);
        }

        dispose();
    }

    public void setColaboradorFormListener(ColaboradorFormListener listener) {
        this.colaboradorFormListener = listener;
    }
}
