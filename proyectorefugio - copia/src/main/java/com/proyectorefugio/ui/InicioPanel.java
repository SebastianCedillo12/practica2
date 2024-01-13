/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.models.Insumo;
import com.proyectorefugio.services.InsumoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class InicioPanel extends JPanel {

    private JLabel welcomeLabel;
    private JTable insumosTable;
    private JScrollPane tableScrollPane;
    private InsumoService insumoService;

    public InicioPanel(InsumoService insumoService) {
        this.insumoService = insumoService;
        initComponents();
        cargarInsumosProximosACaducar();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel("¡Bienvenido al Refugio de Animales!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        insumosTable = new JTable();
        tableScrollPane = new JScrollPane(insumosTable);

        add(welcomeLabel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private void cargarInsumosProximosACaducar() {
        List<Insumo> insumosProximosACaducar = insumoService.verInsumosProximosACaducar();

        if (!insumosProximosACaducar.isEmpty()) {
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 3) {
                        return Date.class;
                    }
                    return Object.class;
                }
            };
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Cantidad");
            model.addColumn("Fecha de Caducidad");
            model.addColumn("Unidad de Medida");

            for (Insumo insumo : insumosProximosACaducar) {
                model.addRow(new Object[]{
                        insumo.getId(),
                        insumo.getNombre(),
                        insumo.getCantidad(),
                        insumo.getFechaCaducidad(),
                        insumo.getUnidadMedida()
                });
            }

            insumosTable.setModel(model);
            insumosTable.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
            
            // Mostrar advertencia con cantidad de insumos a punto de caducar
            int cantidadInsumosACaducar = insumosProximosACaducar.size();
            JOptionPane.showMessageDialog(this,
                "¡Advertencia!\n" +
                "Hay " + cantidadInsumosACaducar + " insumos a punto de caducar en la próxima semana.",
                "Advertencia de Caducidad",
                JOptionPane.WARNING_MESSAGE);
        } else {
            JLabel emptyLabel = new JLabel("¡Felicitaciones! No hay insumos a punto de caducar.");
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            add(emptyLabel, BorderLayout.CENTER);
        }
    }
    
    // Clase DateRenderer para renderizar la columna de fecha en rojo
    class DateRenderer extends DefaultTableCellRenderer {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Date currentDate = new Date();
            Date dateValue = (Date) value;

            if (dateValue != null && dateValue.before(currentDate)) {
                cellComponent.setBackground(Color.RED);
            } else {
                cellComponent.setBackground(table.getBackground());
            }

            if (value instanceof Date) {
                setText(sdf.format(value));
            }

            return cellComponent;
        }
    }
}
