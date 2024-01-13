/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.services;

import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.models.Insumo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author josef
 */
public class InsumoService {

    private final DatabaseController databaseController;

    public InsumoService(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void crearInsumo(String nombre, int cantidad, Date fechaCaducidad,
                            String unidadMedida, Date fechaLote) {
        databaseController.crearInsumo(nombre, cantidad, fechaCaducidad, unidadMedida, fechaLote);
    }

    public Insumo leerInsumoPorID(int insumoId) {
        return databaseController.leerInsumoPorID(insumoId);
    }

    public void actualizarInsumo(int insumoId, String nombre, int cantidad,
                                 Date fechaCaducidad, String unidadMedida, Date fechaLote) {
        databaseController.actualizarInsumo(insumoId, nombre, cantidad, fechaCaducidad, unidadMedida, fechaLote);
    }

    public void eliminarInsumoPorID(int insumoId) {
        databaseController.eliminarInsumoPorID(insumoId);
    }

    public List<Insumo> obtenerTodosLosInsumos() {
        return databaseController.obtenerTodosLosInsumos();
    }

    public List<Insumo> verInsumosProximosACaducar() {
        return databaseController.verInsumosProximosACaducar();
    }
    
    public void generarInformeInsumosPDF(List<Insumo> insumos, String rutaArchivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
                contentStream.showText("Informe de Insumos");
                contentStream.endText();

                // Espacio vertical para la tabla
                float yStart = PDRectangle.A4.getHeight() - 100;

                // Encabezados de la tabla
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, yStart);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Nombre");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("Cantidad");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("F Caducidad");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("U Medida");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("Fecha Lote");
                contentStream.endText();

                // Datos de los insumos
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                for (Insumo insumo : insumos) {
                    yStart -= 20; // Espacio entre filas

                    if (yStart < 50) {
                        // Nueva página si el espacio es insuficiente
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream.moveTo(0, PDRectangle.A4.getHeight() - 50);
                        contentStream.lineTo(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight() - 50);
                        contentStream.stroke();
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
                        contentStream.showText("Informe de Insumos");
                        contentStream.endText();
                        yStart = PDRectangle.A4.getHeight() - 100;
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yStart);
                    contentStream.showText(String.valueOf(insumo.getId()));
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(insumo.getNombre());
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(String.valueOf(insumo.getCantidad()));
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(dateFormat.format(insumo.getFechaCaducidad()));
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(insumo.getUnidadMedida());
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(dateFormat.format(insumo.getFechaLote()));
                    contentStream.endText();
                }

                // Guardar el documento en el archivo especificado
                contentStream.close(); // Cerrar el flujo de contenido antes de guardar
                document.save(rutaArchivo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
