/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.services;

import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.models.Colaborador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
public class ColaboradorService {

    private final DatabaseController databaseController;

    public ColaboradorService(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void crearColaborador(String nombre, String cedula, String puesto, Date fechaContratacion,
                                 double salario, String password, String rol) {
        databaseController.crearColaborador(nombre, cedula, puesto, fechaContratacion, salario, password, rol);
    }

    public Colaborador leerColaboradorPorID(int colaboradorId) {
        return databaseController.leerColaboradorPorID(colaboradorId);
    }

    public void actualizarColaborador(int colaboradorId, String nombre, String cedula, String puesto,
                                      Date fechaContratacion, double salario, String password, String rol) {
        databaseController.actualizarColaborador(colaboradorId, nombre, cedula, puesto, fechaContratacion, salario, password, rol);
    }

    public void eliminarColaboradorPorID(int colaboradorId) {
        databaseController.eliminarColaboradorPorID(colaboradorId);
    }

    public List<Colaborador> obtenerTodosLosColaboradores() {
        return databaseController.obtenerTodosLosColaboradores();
    }
    
    public Colaborador verificarCredencialesColaborador(String cedula, String password) {
        String encryptedPassword = encryptPasswordWithMD5(password);
        return databaseController.verificarCredencialesColaborador(cedula, encryptedPassword);
    }

    private String encryptPasswordWithMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void generarInformeColaboradoresPDF(List<Colaborador> colaboradores, String rutaArchivo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, PDRectangle.A4.getHeight() - 50);
                contentStream.showText("Informe de Colaboradores");
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
                contentStream.newLineAtOffset(150, 0);
                contentStream.showText("Cédula");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("Puesto");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("F. Contrato");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("Salario");
                contentStream.newLineAtOffset(70, 0);
                contentStream.showText("Rol");
                contentStream.endText();

                // Datos de los colaboradores
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                for (Colaborador colaborador : colaboradores) {
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
                        contentStream.showText("Informe de Colaboradores");
                        contentStream.endText();
                        yStart = PDRectangle.A4.getHeight() - 100;
                    }

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yStart);
                    contentStream.showText(String.valueOf(colaborador.getId()));
                    contentStream.newLineAtOffset(50, 0);
                    contentStream.showText(colaborador.getNombre());
                    contentStream.newLineAtOffset(150, 0);
                    contentStream.showText(colaborador.getCedula());
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(colaborador.getPuesto());
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(dateFormat.format(colaborador.getFechaContratacion()));
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(String.valueOf(colaborador.getSalario()));
                    contentStream.newLineAtOffset(70, 0);
                    contentStream.showText(colaborador.getRol());
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
