/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyectorefugio;

import com.proyectorefugio.services.ColaboradorService;
import com.proyectorefugio.database.DatabaseController;
import com.proyectorefugio.ui.LoginForm;
import com.proyectorefugio.ui.MainFrame;
import com.proyectorefugio.models.Colaborador;

import javax.swing.*;

/**
 *
 * @author josef
 */
public class Proyectorefugio {

    private static Colaborador loggedInColaborador; // Variable para almacenar el colaborador autenticado

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showLoginForm();
        });
    }

    private static void showLoginForm() {
        DatabaseController databaseController = new DatabaseController(/* Conexión a la base de datos */);
        ColaboradorService colaboradorService = new ColaboradorService(databaseController);

        LoginForm loginForm = new LoginForm(colaboradorService);
        loginForm.setVisible(true);

        loginForm.addLoginSuccessListener(() -> {
            loggedInColaborador = loginForm.getAuthenticatedColaborador(); // Guardar el colaborador autenticado
            loginForm.dispose();
            showMainFrame();
        });
    }

    private static void showMainFrame() {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new MainFrame(loggedInColaborador); // Pasar el colaborador autenticado al MainFrame
            mainFrame.setVisible(true);
        });
    }
}
