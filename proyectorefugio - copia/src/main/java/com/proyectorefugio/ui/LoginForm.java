/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.ui;

import com.proyectorefugio.services.ColaboradorService;
import com.proyectorefugio.models.Colaborador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author josef
 */
public class LoginForm extends JFrame {
    private JTextField cedulaField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private ColaboradorService colaboradorService;
    private Colaborador authenticatedColaborador;

    private LoginSuccessListener loginSuccessListener;

    public LoginForm(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;

        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel cedulaLabel = new JLabel("Cédula:");
        cedulaField = new JTextField();

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        panel.add(cedulaLabel);
        panel.add(cedulaField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
    }

    private void authenticate() {
        String cedula = cedulaField.getText();
        String password = new String(passwordField.getPassword());

        authenticatedColaborador = colaboradorService.verificarCredencialesColaborador(cedula, password);
        if (authenticatedColaborador != null) {
            if (loginSuccessListener != null) {
                loginSuccessListener.onLoginSuccess();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Colaborador getAuthenticatedColaborador() {
        return authenticatedColaborador;
    }

    public void addLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    public interface LoginSuccessListener {
        void onLoginSuccess();
    }

}
