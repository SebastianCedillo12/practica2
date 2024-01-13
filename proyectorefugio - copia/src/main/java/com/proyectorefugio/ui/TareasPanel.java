/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.TareaService;
import com.proyectorefugio.models.Tarea;
import com.proyectorefugio.services.ColaboradorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author josef
 */
public class TareasPanel extends JPanel {

    private JButton nuevaTareaButton;
    private JTable tareasTable;
    private JScrollPane tableScrollPane;

    private TareaService tareaService;
    private ColaboradorService colaboradorService;

    public TareasPanel(TareaService tareaService, ColaboradorService colaboradorService) {
        this.tareaService = tareaService;
        this.colaboradorService = colaboradorService;
        initComponents();
        loadAllTareas();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        nuevaTareaButton = new JButton("Nueva Tarea");
        tareasTable = new JTable();
        tableScrollPane = new JScrollPane(tareasTable);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nuevaTareaButton);

        add(buttonsPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        nuevaTareaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    TareaForm form = new TareaForm(0, tareaService, colaboradorService);
                    form.setTareaFormListener(new TareaFormListener() {
                        @Override
                        public void tareaGuardada(int tareaId) {
                            loadAllTareas();
                        }
                    });
                    form.setVisible(true);
                });
            }
        });

        loadAllTareas();
    }

    private void loadAllTareas() {
        List<Tarea> tareas = tareaService.obtenerTodasLasTareas();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Descripción");
        model.addColumn("Fecha de Inicio");
        model.addColumn("Hora de Inicio");
        model.addColumn("Fecha de Fin");
        model.addColumn("Hora de Fin");
        model.addColumn("Asignada a");
        model.addColumn(""); // Columna para el botón de editar
        model.addColumn(""); // Columna para el botón de borrar

        for (Tarea tarea : tareas) {
            model.addRow(new Object[]{
                    tarea.getId(),
                    tarea.getDescripcion(),
                    tarea.getFechaInicio(),
                    tarea.getHoraInicio(),
                    tarea.getFechaFin(),
                    tarea.getHoraFin(),
                    tarea.getAsignadaA(),
                    "Editar",
                    "Borrar"
            });
        }

        tareasTable.setModel(model);
        tareasTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Editar"));
        tareasTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Borrar"));
        tareasTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));
        tareasTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
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
                        int tareaId = (int) tareasTable.getValueAt(row, 0);
                        TareaForm form = new TareaForm(tareaId, tareaService, colaboradorService);
                        form.setTareaFormListener(new TareaFormListener() {
                            @Override
                            public void tareaGuardada(int tareaId) {
                                loadAllTareas();
                            }
                        });
                        form.setVisible(true);
                    } else if (buttonText.equals("Borrar")) {
                        int tareaId = (int) tareasTable.getValueAt(row, 0);
                        int confirm = JOptionPane.showConfirmDialog(
                                TareasPanel.this,
                                "¿Estás seguro de que deseas borrar esta tarea?",
                                "Confirmar Borrado",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            tareaService.eliminarTareaPorID(tareaId);
                            loadAllTareas();
                        }
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
