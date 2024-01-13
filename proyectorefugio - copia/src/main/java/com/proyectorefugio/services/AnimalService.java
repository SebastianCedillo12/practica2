/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.services;

import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.models.Animal;

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
public class AnimalService {
    private final DatabaseController databaseController;

    public AnimalService(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void crearAnimal(String nombre, String especie, int edad, Date fechaIngreso,
                            boolean adoptado, Date fechaAdopcion, int adoptanteId, int insumoId) {
        databaseController.crearAnimal(nombre, especie, edad, fechaIngreso,
            adoptado, fechaAdopcion, adoptanteId, insumoId);
    }

    public Animal leerAnimalPorID(int animalId) {
        return databaseController.leerAnimalPorID(animalId);
    }

    public void actualizarAnimal(int animalId, String nombre, String especie, int edad,
                                 Date fechaIngreso, boolean adoptado, Date fechaAdopcion,
                                 int adoptanteId, int insumoId) {
        databaseController.actualizarAnimal(animalId, nombre, especie, edad, fechaIngreso,
            adoptado, fechaAdopcion, adoptanteId, insumoId);
    }

    public void eliminarAnimalPorID(int animalId) {
        databaseController.eliminarAnimalPorID(animalId);
    }
    
    public List<Animal> obtenerTodosLosAnimales() {
        return databaseController.obtenerTodosLosAnimales();
    }
    
    public List<Animal> verAnimalesAdoptados() {
        return databaseController.verAnimalesAdoptados();
    }
    
    public List<Animal> verAnimalesNoAdoptados() {
        return databaseController.verAnimalesNoAdoptados();
    }

    public void AdoptarAnimal(int animalId, Date fechaAdopcion, int adoptanteId) {
        databaseController.AdoptarAnimal(animalId, fechaAdopcion, adoptanteId);
    }
    
    public void generarInformeAnimalesPDF(List<Animal> animales, String rutaArchivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
                contentStream.showText("Informe de Animales");
                contentStream.endText();

                // Espacio vertical para la tabla
                float yStart = PDRectangle.A4.getHeight() - 100;

                // Encabezados de la tabla
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, yStart);
                contentStream.showText("ID");
                contentStream.newLineAtOffset(30, 0);
                contentStream.showText("Nombre");
                contentStream.newLineAtOffset(80, 0);
                contentStream.showText("Especie");
                contentStream.newLineAtOffset(80, 0);
                contentStream.showText("Edad");
                contentStream.newLineAtOffset(60, 0);
                contentStream.showText("Fecha Ingreso");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Adoptado");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("Fecha Adopcion");
                contentStream.endText();

                // Datos de los animales
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (Animal animal : animales) {
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
                        contentStream.showText("Informe de Animales");
                        contentStream.endText();
                        yStart = PDRectangle.A4.getHeight() - 100;
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yStart);
                    contentStream.showText(String.valueOf(animal.getId()));
                    contentStream.newLineAtOffset(30, 0);
                    contentStream.showText(animal.getNombre());
                    contentStream.newLineAtOffset(80, 0);
                    contentStream.showText(animal.getEspecie());
                    contentStream.newLineAtOffset(80, 0);
                    contentStream.showText(String.valueOf(animal.getEdad()));
                    contentStream.newLineAtOffset(60, 0);
                    contentStream.showText(dateFormat.format(animal.getFechaIngreso()));
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(animal.isAdoptado() ? "Sí" : "No");
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(animal.getFechaAdopcion() != null ? dateFormat.format(animal.getFechaAdopcion()) : "");
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
