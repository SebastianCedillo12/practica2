/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.InsumoService;
import com.proyectorefugio.models.Insumo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author josef
 */
public class InsumosPanel extends JPanel {

    private JButton nuevoInsumoButton;
    private JTable insumosTable;
    private JScrollPane tableScrollPane;

    private InsumoService insumoService;

    public InsumosPanel(InsumoService insumoService) {
        this.insumoService = insumoService;
        initComponents();
        loadAllInsumos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        nuevoInsumoButton = new JButton("Nuevo Insumo");
        insumosTable = new JTable();
        tableScrollPane = new JScrollPane(insumosTable);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nuevoInsumoButton);

        add(buttonsPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        nuevoInsumoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    InsumoForm form = new InsumoForm(0, insumoService);
                    form.setInsumoFormListener(new InsumoFormListener() {
                        @Override
                        public void insumoGuardado(int insumoId) {
                            loadAllInsumos();
                        }
                    });
                    form.setVisible(true);
                });
            }
        });

        loadAllInsumos();
    }

    private void loadAllInsumos() {
        List<Insumo> insumos = insumoService.obtenerTodosLosInsumos();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Cantidad");
        model.addColumn("Unidad de Medida");
        model.addColumn("Fecha de Caducidad");
        model.addColumn(""); // Columna para el botón de editar
        model.addColumn(""); // Columna para el botón de borrar

        for (Insumo insumo : insumos) {
            model.addRow(new Object[]{
                    insumo.getId(),
                    insumo.getNombre(),
                    insumo.getCantidad(),
                    insumo.getUnidadMedida(),
                    insumo.getFechaCaducidad(),
                    "Editar",
                    "Borrar"
            });
        }

        insumosTable.setModel(model);
        insumosTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Editar"));
        insumosTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Borrar"));
        insumosTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));
        insumosTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
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
                        int insumoId = (int) insumosTable.getValueAt(row, 0);
                        InsumoForm form = new InsumoForm(insumoId, insumoService);
                        form.setInsumoFormListener(new InsumoFormListener() {
                            @Override
                            public void insumoGuardado(int insumoId) {
                                loadAllInsumos();
                            }
                        });
                        form.setVisible(true);
                    } else if (buttonText.equals("Borrar")) {
                        int insumoId = (int) insumosTable.getValueAt(row, 0);
                        int confirm = JOptionPane.showConfirmDialog(
                                InsumosPanel.this,
                                "¿Estás seguro de que deseas borrar este insumo?",
                                "Confirmar Borrado",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            insumoService.eliminarInsumoPorID(insumoId);
                            loadAllInsumos();
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
