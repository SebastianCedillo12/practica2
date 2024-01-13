/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.models.Colaborador;
import com.proyectorefugio.services.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author josef
 */
public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem animalesMenuItem;
    private JMenuItem inicioMenuItem;
    private JMenuItem adoptantesMenuItem;
    private JMenuItem colaboradoresMenuItem;
    private JMenuItem insumosMenuItem;
    private JMenuItem tareasMenuItem;
    private JMenuItem reportesMenuItem;
    private AdoptanteService adoptanteService;
    private DatabaseController databaseController;
    private ColaboradorService colaboradorService;
    private InsumoService insumoService;
    private AnimalService animalService;
    private TareaService tareaService;

    private Colaborador loggedInColaborador;

    public MainFrame(Colaborador loggedInColaborador) {
        databaseController = new DatabaseController();
        adoptanteService = new AdoptanteService(databaseController);
        colaboradorService = new ColaboradorService(databaseController);
        insumoService = new InsumoService(databaseController);
        animalService = new AnimalService(databaseController);
        tareaService = new TareaService(databaseController);
        
        this.loggedInColaborador = loggedInColaborador;

        setTitle("Sistema de Refugio de Animales - " + loggedInColaborador.getNombre() + " (" + loggedInColaborador.getCedula() + ")");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Maximizar la ventana por defecto
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initUI();
        
        // Mostrar el panel InicioPanel al inicio
        showInicioPanel();
    }

    private void initUI() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");

        inicioMenuItem = new JMenuItem("Inicio");
        reportesMenuItem = new JMenuItem("Reportes");
        animalesMenuItem = new JMenuItem("Animales");
        adoptantesMenuItem = new JMenuItem("Adoptantes");
        colaboradoresMenuItem = new JMenuItem("Colaboradores");
        insumosMenuItem = new JMenuItem("Insumos");
        tareasMenuItem = new JMenuItem("Tareas");

        animalesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAnimalesPanel();
            }
        });
        inicioMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInicioPanel();
            }
        });
        
        reportesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportesPanel();
            }
        });

        adoptantesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdoptantesPanel();
            }
        });

        colaboradoresMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showColaboradoresPanel();
            }
        });

        insumosMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInsumosPanel();
            }
        });

        tareasMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTareasPanel();
            }
        });

        menu.add(inicioMenuItem);
        menu.add(reportesMenuItem);
        menu.add(animalesMenuItem);
        menu.add(adoptantesMenuItem);
        menu.add(colaboradoresMenuItem);
        menu.add(insumosMenuItem);
        menu.add(tareasMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    private void showAnimalesPanel() {
        // Lógica para mostrar el panel de animales
        AnimalesPanel animalesPanel = new AnimalesPanel(animalService,adoptanteService); // Crea una instancia del panel de animales
        setContentPane(animalesPanel); // Establece el contenido del JFrame como el panel de animales
        revalidate(); // Actualiza la interfaz para que se muestre el nuevo contenido
    }
    
    private void showAdoptantesPanel() {
        AdoptantesPanel adoptantesPanel = new AdoptantesPanel(adoptanteService);
        setContentPane(adoptantesPanel);
        revalidate();
    }

    private void showColaboradoresPanel() {
        ColaboradoresPanel colaboradoresPanel = new ColaboradoresPanel(colaboradorService);
        setContentPane(colaboradoresPanel);
        revalidate();
    }

    private void showInsumosPanel() {
        InsumosPanel insumosPanel = new InsumosPanel(insumoService);
        setContentPane(insumosPanel);
        revalidate();
    }

    private void showTareasPanel() {
        TareasPanel tareasPanel = new TareasPanel(tareaService,colaboradorService);
        setContentPane(tareasPanel);
        revalidate();
    }

    private void showInicioPanel() {
        InicioPanel inicioPanel = new InicioPanel(insumoService);
        setContentPane(inicioPanel);
        revalidate();
    }
    
    private void showReportesPanel() {
        ReportesPanel reportesPanel = new ReportesPanel(adoptanteService,colaboradorService,animalService,insumoService,tareaService);
        setContentPane(reportesPanel);
        revalidate();
    }

    
}
