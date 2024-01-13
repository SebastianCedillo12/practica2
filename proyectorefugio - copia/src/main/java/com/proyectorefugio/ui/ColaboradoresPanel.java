/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.ColaboradorService;
import com.proyectorefugio.models.Colaborador;
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
public class ColaboradoresPanel extends JPanel {

    private JButton nuevoColaboradorButton;
    private JTable colaboradoresTable;
    private JScrollPane tableScrollPane;

    private ColaboradorService colaboradorService;

    public ColaboradoresPanel(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
        initComponents();
        loadAllColaboradores();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        nuevoColaboradorButton = new JButton("Nuevo Colaborador");
        colaboradoresTable = new JTable();
        tableScrollPane = new JScrollPane(colaboradoresTable);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nuevoColaboradorButton);

        add(buttonsPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        nuevoColaboradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    ColaboradorForm form = new ColaboradorForm(0, colaboradorService);
                    form.setColaboradorFormListener(new ColaboradorFormListener() {
                        @Override
                        public void colaboradorGuardado(int colaboradorId) {
                            loadAllColaboradores();
                        }
                    });
                    form.setVisible(true);
                });
            }
        });

        loadAllColaboradores();
    }

    private void loadAllColaboradores() {
        List<Colaborador> colaboradores = colaboradorService.obtenerTodosLosColaboradores();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Cédula");
        model.addColumn("Puesto");
        model.addColumn("Fecha Contratación");
        model.addColumn("Salario");
        model.addColumn("Rol");
        model.addColumn(""); // Columna para el botón de editar
        model.addColumn(""); // Columna para el botón de borrar

        for (Colaborador colaborador : colaboradores) {
            model.addRow(new Object[]{
                    colaborador.getId(),
                    colaborador.getNombre(),
                    colaborador.getCedula(),
                    colaborador.getPuesto(),
                    colaborador.getFechaContratacion(),
                    colaborador.getSalario(),
                    colaborador.getRol(),
                    "Editar",
                    "Borrar"
            });
        }

        colaboradoresTable.setModel(model);
        colaboradoresTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Editar"));
        colaboradoresTable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer("Borrar"));
        colaboradoresTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));
        colaboradoresTable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
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
                        int colaboradorId = (int) colaboradoresTable.getValueAt(row, 0);
                        ColaboradorForm form = new ColaboradorForm(colaboradorId, colaboradorService);
                        form.setColaboradorFormListener(new ColaboradorFormListener() {
                            @Override
                            public void colaboradorGuardado(int colaboradorId) {
                                loadAllColaboradores();
                            }
                        });
                        form.setVisible(true);
                    } else if (buttonText.equals("Borrar")) {
                        int colaboradorId = (int) colaboradoresTable.getValueAt(row, 0);
                        int confirm = JOptionPane.showConfirmDialog(
                                ColaboradoresPanel.this,
                                "¿Estás seguro de que deseas borrar este colaborador?",
                                "Confirmar Borrado",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            colaboradorService.eliminarColaboradorPorID(colaboradorId);
                            loadAllColaboradores();
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
