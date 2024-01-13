/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.models.Adoptante;
import com.proyectorefugio.models.Animal;
import com.proyectorefugio.services.AdoptanteService;

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
public class AdoptantesPanel extends JPanel implements AdoptanteFormListener {

    private JButton nuevoAdoptanteButton;
    private JTable adoptantesTable;
    private JScrollPane tableScrollPane;

    private AdoptanteService adoptanteService;

    public AdoptantesPanel(AdoptanteService adoptanteService) {
        this.adoptanteService = adoptanteService;
        initComponents();
        loadAllAdoptantes();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        nuevoAdoptanteButton = new JButton("Nuevo Adoptante");
        adoptantesTable = new JTable();
        tableScrollPane = new JScrollPane(adoptantesTable);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(nuevoAdoptanteButton);

        add(buttonsPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        nuevoAdoptanteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    AdoptanteForm form = new AdoptanteForm(0, adoptanteService); // Crear nuevo adoptante
                    form.setAdoptanteFormListener(AdoptantesPanel.this);
                    form.setVisible(true);
                });
            }
        });

        // Configuración de la tabla
        adoptantesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadAllAdoptantes();
    }

    private void loadAllAdoptantes() {
        List<Adoptante> adoptantes = adoptanteService.obtenerTodosLosAdoptantes();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Dirección");
        model.addColumn("Teléfono");
        model.addColumn("Correo");
        model.addColumn("Editar");
        model.addColumn("Borrar");
        model.addColumn("Animales");

        for (Adoptante adoptante : adoptantes) {
            model.addRow(new Object[]{
                    adoptante.getId(),
                    adoptante.getNombre(),
                    adoptante.getDireccion(),
                    adoptante.getTelefono(),
                    adoptante.getCorreo(),
                    "Editar",
                    "Borrar",
                    "Animales"
            });
        }

        adoptantesTable.setModel(model);

        // Configuración del botón de edición en la tabla
        adoptantesTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Editar"));
        adoptantesTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));

        // Configuración del botón de borrar en la tabla
        adoptantesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Borrar"));
        adoptantesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), "Borrar"));
        
        adoptantesTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Animales"));
        adoptantesTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Animales"));
    }

    @Override
    public void adoptanteGuardado(int adoptanteId) {
        loadAllAdoptantes(); // Actualizar la tabla de adoptantes
    }

    // Clase ButtonRenderer para renderizar los botones
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setOpaque(true);
            setText(text);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private void mostrarTablaAnimalesAdoptados(List<Animal> animales) {
        JDialog dialog = new JDialog((Frame) null, "Animales Adoptados", true);
        dialog.setLayout(new BorderLayout());

        JTable animalesTable = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Especie");
        // ... Agregar más columnas según tus necesidades

        for (Animal animal : animales) {
            model.addRow(new Object[]{
                    animal.getId(),
                    animal.getNombre(),
                    animal.getEspecie(),
                    // ... Agregar más valores según tus necesidades
            });
        }

        animalesTable.setModel(model);
        dialog.add(new JScrollPane(animalesTable), BorderLayout.CENTER);

        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(cerrarButton, BorderLayout.SOUTH);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    // Clase ButtonEditor para manejar el evento de edición de los botones
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean clicked;
        private int row, column;
        private String buttonText;
        private int adoptanteId;

        public ButtonEditor(JCheckBox checkBox, String buttonText) {
            super(checkBox);
            this.buttonText = buttonText;
            button = new JButton(buttonText);
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
            this.adoptanteId = (int) adoptantesTable.getValueAt(row, 0);
            button.setText(buttonText);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                SwingUtilities.invokeLater(() -> {
                    if (buttonText.equals("Editar")) {
                        AdoptanteForm form = new AdoptanteForm(adoptanteId, adoptanteService); // Editar adoptante existente
                        form.setAdoptanteFormListener(AdoptantesPanel.this);
                        form.setVisible(true);
                    } else if (buttonText.equals("Borrar")) {
                        int confirm = JOptionPane.showConfirmDialog(
                                AdoptantesPanel.this,
                                "¿Estás seguro de que deseas borrar este adoptante?",
                                "Confirmar Borrado",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirm == JOptionPane.YES_OPTION) {
                            adoptanteService.eliminarAdoptantePorID(adoptanteId);
                            loadAllAdoptantes(); // Actualizar la tabla después de borrar
                        }
                    } else if(buttonText.equals("Animales")){
                        List<Animal> animales = adoptanteService.verAnimalesAdoptadosPorAdoptante(adoptanteId);
                        mostrarTablaAnimalesAdoptados(animales); // Mostrar la tabla con los animales adoptados
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
