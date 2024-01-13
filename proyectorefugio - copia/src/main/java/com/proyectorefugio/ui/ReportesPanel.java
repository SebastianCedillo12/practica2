/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.*;
import com.proyectorefugio.models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReportesPanel extends JPanel {

    private JButton reporteAdoptantesButton;
    private JButton reporteAnimalesButton;
    private JButton reporteColaboradoresButton;
    private JButton reporteInsumosButton;
    private JButton reporteTareasButton;
    private AdoptanteService adoptanteService;
    private ColaboradorService colaboradorService;
    private AnimalService animalService;
    private InsumoService insumoService;
    private TareaService tareaService;

    public ReportesPanel(AdoptanteService adoptanteService, ColaboradorService colaboradorService,
                         AnimalService animalService, InsumoService insumoService, TareaService tareaService) {
        this.adoptanteService = adoptanteService;
        this.colaboradorService = colaboradorService;
        this.animalService = animalService;
        this.insumoService = insumoService;
        this.tareaService = tareaService;

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 1, 10, 10));

        reporteAdoptantesButton = new JButton("Generar Reporte de Adoptantes");
        reporteAnimalesButton = new JButton("Generar Reporte de Animales");
        reporteColaboradoresButton = new JButton("Generar Reporte de Colaboradores");
        reporteInsumosButton = new JButton("Generar Reporte de Insumos");
        reporteTareasButton = new JButton("Generar Reporte de Tareas");

        add(reporteAdoptantesButton);
        add(reporteAnimalesButton);
        add(reporteColaboradoresButton);
        add(reporteInsumosButton);
        add(reporteTareasButton);

        reporteAdoptantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Adoptante> adoptantes = adoptanteService.obtenerTodosLosAdoptantes();
                String rutaArchivo = "InformeAdoptantes.pdf"; // Ruta y nombre del archivo de salida

                adoptanteService.generarInformeAdoptantesPDF(adoptantes, rutaArchivo);
                
                JOptionPane.showMessageDialog(ReportesPanel.this, "Reporte de Adoptantes generado.");
            }
        });

        reporteAnimalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Animal> animales = animalService.obtenerTodosLosAnimales();
                String rutaArchivo = "InformeAnimales.pdf"; // Ruta y nombre del archivo de salida

                animalService.generarInformeAnimalesPDF(animales, rutaArchivo);

                JOptionPane.showMessageDialog(ReportesPanel.this, "Reporte de Animales generado.");
            }
        });

        reporteColaboradoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                List<Colaborador> colaboradores = colaboradorService.obtenerTodosLosColaboradores();
                String rutaArchivo = "InformeColaboradores.pdf"; // Ruta y nombre del archivo de salida

                colaboradorService.generarInformeColaboradoresPDF(colaboradores, rutaArchivo);
                
                JOptionPane.showMessageDialog(ReportesPanel.this, "Reporte de Colaboradores generado.");
            }
        });

        reporteInsumosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Insumo> insumos = insumoService.obtenerTodosLosInsumos();
                String rutaArchivo = "InformeInsumos.pdf"; // Ruta y nombre del archivo de salida

                insumoService.generarInformeInsumosPDF(insumos, rutaArchivo);
                
                JOptionPane.showMessageDialog(ReportesPanel.this, "Reporte de Insumos generado.");
            }
        });

        reporteTareasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Tarea> tareas = tareaService.obtenerTodasLasTareas();
                String rutaArchivo = "InformeTareas.pdf"; // Ruta y nombre del archivo de salida

                tareaService.generarInformeTareasPDF(tareas, rutaArchivo);
                
                JOptionPane.showMessageDialog(ReportesPanel.this, "Reporte de Tareas generado.");
            }
        });
    }
}
