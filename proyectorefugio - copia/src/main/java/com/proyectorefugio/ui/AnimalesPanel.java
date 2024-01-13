/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.AnimalService;
import com.proyectorefugio.models.Animal;
import com.proyectorefugio.services.AdoptanteService;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 *
 * @author josef
 */
public class AnimalesPanel extends JPanel {

    private JButton nuevoAnimalButton;
    private JButton verAnimalesAdoptadosButton;
    private JButton verAnimalesNoAdoptadosButton;
    private JTable animalesTable;
    private JScrollPane tableScrollPane;
    
    private AdoptanteService adoptanteService;
    private AnimalService animalService;

    public AnimalesPanel(AnimalService animalService,AdoptanteService adoptanteService) {
        this.animalService = animalService;
        this.adoptanteService = adoptanteService;
        initComponents();
        loadAllAnimales();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        nuevoAnimalButton = new JButton("Nuevo Animal");
        verAnimalesAdoptadosButton = new JButton("Animales adoptados");
        verAnimalesNoAdoptadosButton = new JButton("Animales disponibles");
        animalesTable = new JTable();
        tableScrollPane = new JScrollPane(animalesTable);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nuevoAnimalButton);
        buttonsPanel.add(verAnimalesAdoptadosButton);
        buttonsPanel.add(verAnimalesNoAdoptadosButton);

        add(buttonsPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        nuevoAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    AnimalForm form = new AnimalForm(0, animalService);
                    form.setAnimalFormListener(new AnimalFormListener() {
                        @Override
                        public void animalGuardado(int animalId) {
                            loadAllAnimales();
                        }
                    });
                    form.setVisible(true);
                });
            }
        });
        
        verAnimalesAdoptadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAnimalesAdoptados();
            }
        });
        
        verAnimalesNoAdoptadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAnimalesNoAdoptados();
            }
        });

        loadAllAnimales();
    }
    
    private void loadAllAnimales() {
        List<Animal> animales = animalService.obtenerTodosLosAnimales();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Edad");
        model.addColumn("Especie");
        model.addColumn("Fecha de ingreso");
        model.addColumn("Adoptado");
        model.addColumn(""); // Columna para el botón de editar
        model.addColumn(""); // Columna para el botón de borrar
        model.addColumn(""); // Columna para el botón de adopcion

        for (Animal animal : animales) {
            model.addRow(new Object[]{
                    animal.getId(),
                    animal.getNombre(),
                    animal.getEdad(),
                    animal.getEspecie(),
                    animal.getFechaIngreso(),
                    animal.getFechaAdopcion(),
                    "Editar",
                    "Borrar",
                    "Adopcion"
            });
        }

        animalesTable.setModel(model);
        animalesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Editar"));
        animalesTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Borrar"));
        animalesTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Adopcion"));
        animalesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));
        animalesTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
        animalesTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Adopcion"));
    }
    
    private void loadAnimalesAdoptados() {
        List<Animal> animales = animalService.verAnimalesAdoptados();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Edad");
        model.addColumn("Especie");
        model.addColumn("Fecha de ingreso");
        model.addColumn("Adoptado");
        model.addColumn(""); // Columna para el botón de editar
        model.addColumn(""); // Columna para el botón de borrar
        model.addColumn(""); // Columna para el botón de adopcion

        for (Animal animal : animales) {
            model.addRow(new Object[]{
                    animal.getId(),
                    animal.getNombre(),
                    animal.getEdad(),
                    animal.getEspecie(),
                    animal.getFechaIngreso(),
                    animal.getFechaAdopcion(),
                    "Editar",
                    "Borrar",
                    "Adopcion"
            });
        }

        animalesTable.setModel(model);
        animalesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Editar"));
        animalesTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Borrar"));
        animalesTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Adopcion"));
        animalesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));
        animalesTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
        animalesTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Adopcion"));
    }

    private void loadAnimalesNoAdoptados() {
        List<Animal> animales = animalService.verAnimalesNoAdoptados();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Edad");
        model.addColumn("Especie");
        model.addColumn("Fecha de ingreso");
        model.addColumn("Adoptado");
        model.addColumn(""); // Columna para el botón de editar
        model.addColumn(""); // Columna para el botón de borrar
        model.addColumn(""); // Columna para el botón de adopcion

        for (Animal animal : animales) {
            model.addRow(new Object[]{
                    animal.getId(),
                    animal.getNombre(),
                    animal.getEdad(),
                    animal.getEspecie(),
                    animal.getFechaIngreso(),
                    animal.getFechaAdopcion(),
                    "Editar",
                    "Borrar",
                    "Adopcion"
            });
        }

        animalesTable.setModel(model);
        animalesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Editar"));
        animalesTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Borrar"));
        animalesTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Adopcion"));
        animalesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));
        animalesTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
        animalesTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Adopcion"));
    }

    // Clase ButtonRenderer para renderizar los botones de editar y borrar
    class ButtonRenderer extends JButton implements TableCellRenderer {
        private String buttonText;

        public ButtonRenderer(String buttonText) {
            this.buttonText = buttonText;
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(buttonText);
            return this;
        }
    }

    // Clase ButtonEditor para manejar los eventos de edición de los botones
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean clicked;
        private int row, column;
        private String buttonText;

        public ButtonEditor(JCheckBox checkBox, String buttonText) {
            super(checkBox);
            this.buttonText = buttonText;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            this.column = column;
            clicked = true;
            button.setText(buttonText);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                SwingUtilities.invokeLater(() -> {
                    if (buttonText.equals("Editar")) {
                        int animalId = (int) animalesTable.getValueAt(row, 0);
                        AnimalForm form = new AnimalForm(animalId, animalService);
                        form.setAnimalFormListener(new AnimalFormListener() {
                            @Override
                            public void animalGuardado(int animalId) {
                                loadAllAnimales();
                            }
                        });
                        form.setVisible(true);
                    } else if (buttonText.equals("Borrar")) {
                        int animalId = (int) animalesTable.getValueAt(row, 0);
                        int confirm = JOptionPane.showConfirmDialog(
                                AnimalesPanel.this,
                                "¿Estás seguro de que deseas borrar este animal?",
                                "Confirmar Borrado",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            animalService.eliminarAnimalPorID(animalId);
                            loadAllAnimales();
                        }
                    } else if (buttonText.equals("Adopcion")) {
                        int animalId = (int) animalesTable.getValueAt(row, 0);
                        AdopcionForm form = new AdopcionForm(animalId, animalService, adoptanteService);
                        form.setAdopcionFormListener(new AdopcionFormListener() {
                            @Override
                            public void adopcionGuardada(int animalId) {
                                loadAllAnimales();
                            }
                        });
                        form.setVisible(true);
                    }
                });
            }
            clicked = false;
            return "";
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
