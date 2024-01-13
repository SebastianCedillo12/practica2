/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.services;

import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.models.Tarea;

import java.sql.Date;
import java.sql.Time;
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
public class TareaService {

    private final DatabaseController databaseController;

    public TareaService(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void crearTarea(String descripcion, Date fechaInicio, Time horaInicio,
                           Date fechaFin, Time horaFin, int asignadaA) {
        databaseController.crearTarea(descripcion, fechaInicio, horaInicio, fechaFin, horaFin, asignadaA);
    }

    public Tarea leerTareaPorID(int tareaId) {
        return databaseController.leerTareaPorID(tareaId);
    }

    public void actualizarTarea(int tareaId, String descripcion, Date fechaInicio, Time horaInicio,
                                Date fechaFin, Time horaFin, int asignadaA) {
        databaseController.actualizarTarea(tareaId, descripcion, fechaInicio, horaInicio, fechaFin, horaFin, asignadaA);
    }

    public void eliminarTareaPorID(int tareaId) {
        databaseController.eliminarTareaPorID(tareaId);
    }

    public List<Tarea> VerTareasPorColaborador(int colaboradorId) {
        return databaseController.verTareasPorColaborador(colaboradorId);
    }
    
    public List<Tarea> obtenerTodasLasTareas() {
        return databaseController.obtenerTodasLasTareas();
    }
    

    public void generarInformeTareasPDF(List<Tarea> tareas, String rutaArchivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
                contentStream.showText("Informe de Tareas");
                contentStream.endText();

                // Espacio vertical para la tabla
                float yStart = PDRectangle.A4.getHeight() - 100;

                // Encabezados de la tabla
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, yStart);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText("Descripción");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("F Inicio");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("H Inicio");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("F Fin");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("H Fin");
                contentStream.endText();

                // Datos de las tareas
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (Tarea tarea : tareas) {
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
                        contentStream.showText("Informe de Tareas");
                        contentStream.endText();
                        yStart = PDRectangle.A4.getHeight() - 100;
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yStart);
                    contentStream.showText(String.valueOf(tarea.getId()));
                    contentStream.newLineAtOffset(50, 0);
                    contentStream.showText(tarea.getDescripcion());
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(tarea.getFechaInicio() + "");
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(tarea.getHoraInicio() + "");
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(tarea.getFechaFin() + "");
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(tarea.getHoraFin() + "");
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
