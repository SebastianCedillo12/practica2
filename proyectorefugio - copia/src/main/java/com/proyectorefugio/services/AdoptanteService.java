/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.services;

import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.models.Adoptante;
import com.proyectorefugio.models.Animal;

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
public class AdoptanteService {

    private final DatabaseController databaseController;

    public AdoptanteService(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void crearAdoptante(String nombre, String direccion, String telefono, String correo) {
        databaseController.crearAdoptante(nombre, direccion, telefono, correo);
    }

    public Adoptante leerAdoptantePorID(int adoptanteId) {
        return databaseController.leerAdoptantePorID(adoptanteId);
    }

    public void actualizarAdoptante(int adoptanteId, String nombre, String direccion,
                                    String telefono, String correo) {
        databaseController.actualizarAdoptante(adoptanteId, nombre, direccion, telefono, correo);
    }

    public void eliminarAdoptantePorID(int adoptanteId) {
        databaseController.eliminarAdoptantePorID(adoptanteId);
    }

    public List<Adoptante> obtenerTodosLosAdoptantes() {
        return databaseController.obtenerTodosLosAdoptantes();
    }
    
    
    public List<Animal> verAnimalesAdoptadosPorAdoptante(int adoptanteId) {
        return databaseController.verAnimalesAdoptadosPorAdoptante(adoptanteId);
    }
    
    public void generarInformeAdoptantesPDF(List<Adoptante> adoptantes, String rutaArchivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
                contentStream.showText("Informe de Adoptantes");
                contentStream.endText();

                // Espacio vertical para la tabla
                float yStart = PDRectangle.A4.getHeight() - 100;

                // Encabezados de la tabla
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, yStart);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText("Nombre");
                contentStream.newLineAtOffset(180, 0);
                contentStream.showText("Dirección");
                contentStream.newLineAtOffset(120, 0);
                contentStream.showText("Teléfono");
                contentStream.newLineAtOffset(120, 0);
                contentStream.showText("Correo");
                contentStream.endText();

                // Datos de los adoptantes
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (Adoptante adoptante : adoptantes) {
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
                        contentStream.showText("Informe de Adoptantes");
                        contentStream.endText();
                        yStart = PDRectangle.A4.getHeight() - 100;
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yStart);
                    contentStream.showText(String.valueOf(adoptante.getId()));
                    contentStream.newLineAtOffset(50, 0);
                    contentStream.showText(adoptante.getNombre());
                    contentStream.newLineAtOffset(180, 0);
                    contentStream.showText(adoptante.getDireccion());
                    contentStream.newLineAtOffset(120, 0);
                    contentStream.showText(adoptante.getTelefono());
                    contentStream.newLineAtOffset(120, 0);
                    contentStream.showText(adoptante.getCorreo());
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
