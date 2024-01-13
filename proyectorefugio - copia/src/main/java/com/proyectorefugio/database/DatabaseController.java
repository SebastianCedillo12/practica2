/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectorefugio.database;

import com.proyectorefugio.models.Adoptante;
import com.proyectorefugio.models.Animal;
import com.proyectorefugio.models.Colaborador;
import com.proyectorefugio.models.Insumo;
import com.proyectorefugio.models.Tarea;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josef
 */
public class DatabaseController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/proyectorefugio";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection connection;

    public DatabaseController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Aquí puedes agregar métodos para llamar a los procedimientos almacenados
    public List<Animal> obtenerTodosLosAnimales() {
        List<Animal> animales = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM animales");
            while (resultSet.next()) {
                Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("especie"),
                    resultSet.getInt("edad"),
                    resultSet.getDate("fecha_ingreso"),
                    resultSet.getBoolean("adoptado"),
                    resultSet.getDate("fecha_adopcion"),
                    resultSet.getInt("adoptante_id"),
                    resultSet.getInt("insumo_id")
                );
                animales.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }
    
    public void crearAnimal(String nombre, String especie, int edad, Date fechaIngreso,
                            boolean adoptado, Date fechaAdopcion, int adoptanteId, int insumoId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL CrearAnimal(?, ?, ?, ?, ?, ?, null, null)}");
            statement.setString(1, nombre);
            statement.setString(2, especie);
            statement.setInt(3, edad);
            statement.setDate(4, fechaIngreso);
            statement.setBoolean(5, adoptado);
            statement.setDate(6, fechaAdopcion);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Animal leerAnimalPorID(int animalId) {
        Animal animal = null;
        try {
            CallableStatement statement = connection.prepareCall("{CALL LeerAnimalPorID(?)}");
            statement.setInt(1, animalId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("especie"),
                    resultSet.getInt("edad"),
                    resultSet.getDate("fecha_ingreso"),
                    resultSet.getBoolean("adoptado"),
                    resultSet.getDate("fecha_adopcion"),
                    resultSet.getInt("adoptante_id"),
                    resultSet.getInt("insumo_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }

    public void actualizarAnimal(int animalId, String nombre, String especie, int edad,
                                 Date fechaIngreso, boolean adoptado, Date fechaAdopcion,
                                 int adoptanteId, int insumoId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL ActualizarAnimal(?, ?, ?, ?, ?, ?, ?, null, null)}");
            statement.setInt(1, animalId);
            statement.setString(2, nombre);
            statement.setString(3, especie);
            statement.setInt(4, edad);
            statement.setDate(5, fechaIngreso);
            statement.setBoolean(6, adoptado);
            statement.setDate(7, fechaAdopcion);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AdoptarAnimal(int animalId, Date fechaAdopcion, int adoptanteId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL AdoptarAnimal(?, ?, ?)}");
            statement.setInt(1, animalId);
            statement.setDate(2, fechaAdopcion);
            statement.setInt(3, adoptanteId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarAnimalPorID(int animalId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL EliminarAnimalPorID(?)}");
            statement.setInt(1, animalId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Adoptante> obtenerTodosLosAdoptantes() {
        List<Adoptante> adoptantes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM adoptantes");
            while (resultSet.next()) {
                Adoptante adoptante = new Adoptante(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("direccion"),
                    resultSet.getString("telefono"),
                    resultSet.getString("correo")
                );
                adoptantes.add(adoptante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adoptantes;
    }
    
    public void crearAdoptante(String nombre, String direccion, String telefono, String correo) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL CrearAdoptante(?, ?, ?, ?)}");
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, correo);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Adoptante leerAdoptantePorID(int adoptanteId) {
        Adoptante adoptante = null;
        try {
            CallableStatement statement = connection.prepareCall("{CALL LeerAdoptantePorID(?)}");
            statement.setInt(1, adoptanteId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                adoptante = new Adoptante(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("direccion"),
                    resultSet.getString("telefono"),
                    resultSet.getString("correo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adoptante;
    }

    public void actualizarAdoptante(int adoptanteId, String nombre, String direccion, String telefono, String correo) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL ActualizarAdoptante(?, ?, ?, ?, ?)}");
            statement.setInt(1, adoptanteId);
            statement.setString(2, nombre);
            statement.setString(3, direccion);
            statement.setString(4, telefono);
            statement.setString(5, correo);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarAdoptantePorID(int adoptanteId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL EliminarAdoptantePorID(?)}");
            statement.setInt(1, adoptanteId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Colaborador> obtenerTodosLosColaboradores() {
        List<Colaborador> colaboradores = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM colaboradores");
            while (resultSet.next()) {
                Colaborador colaborador = new Colaborador(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("cedula"),
                    resultSet.getString("puesto"),
                    resultSet.getDate("fecha_contratacion"),
                    resultSet.getDouble("salario"),
                    resultSet.getString("password"),
                    resultSet.getString("rol")
                );
                colaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colaboradores;
    }
    
    public void crearColaborador(String nombre, String cedula, String puesto, Date fechaContratacion,
                                 double salario, String password, String rol) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL CrearColaborador(?, ?, ?, ?, ?, ?, ?)}");
            statement.setString(1, nombre);
            statement.setString(2, cedula);
            statement.setString(3, puesto);
            statement.setDate(4, fechaContratacion);
            statement.setDouble(5, salario);
            statement.setString(6, password);
            statement.setString(7, rol);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Colaborador leerColaboradorPorID(int colaboradorId) {
        Colaborador colaborador = null;
        try {
            CallableStatement statement = connection.prepareCall("{CALL LeerColaboradorPorID(?)}");
            statement.setInt(1, colaboradorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                colaborador = new Colaborador(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("cedula"),
                    resultSet.getString("puesto"),
                    resultSet.getDate("fecha_contratacion"),
                    resultSet.getDouble("salario"),
                    resultSet.getString("password"),
                    resultSet.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colaborador;
    }

    public void actualizarColaborador(int colaboradorId, String nombre, String cedula, String puesto,
                                      Date fechaContratacion, double salario,
                                      String password, String rol) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL ActualizarColaborador(?, ?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, colaboradorId);
            statement.setString(2, nombre);
            statement.setString(3, cedula);
            statement.setString(4, puesto);
            statement.setDate(5, fechaContratacion);
            statement.setDouble(6, salario);
            statement.setString(7, password);
            statement.setString(8, rol);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarColaboradorPorID(int colaboradorId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL EliminarColaboradorPorID(?)}");
            statement.setInt(1, colaboradorId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Insumo> obtenerTodosLosInsumos() {
        List<Insumo> insumos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM insumos");
            while (resultSet.next()) {
                Insumo insumo = new Insumo(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("cantidad"),
                    resultSet.getDate("fecha_caducidad"),
                    resultSet.getString("unidad_medida"),
                    resultSet.getDate("fecha_lote")
                );
                insumos.add(insumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insumos;
    }
    
    public void crearInsumo(String nombre, int cantidad, Date fechaCaducidad,
                            String unidadMedida, Date fechaLote) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL CrearInsumo(?, ?, ?, ?, ?)}");
            statement.setString(1, nombre);
            statement.setInt(2, cantidad);
            statement.setDate(3, fechaCaducidad);
            statement.setString(4, unidadMedida);
            statement.setDate(5, fechaLote);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Insumo leerInsumoPorID(int insumoId) {
        Insumo insumo = null;
        try {
            CallableStatement statement = connection.prepareCall("{CALL LeerInsumoPorID(?)}");
            statement.setInt(1, insumoId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                insumo = new Insumo(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("cantidad"),
                    resultSet.getDate("fecha_caducidad"),
                    resultSet.getString("unidad_medida"),
                    resultSet.getDate("fecha_lote")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insumo;
    }

    public void actualizarInsumo(int insumoId, String nombre, int cantidad,
                                 Date fechaCaducidad, String unidadMedida, Date fechaLote) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL ActualizarInsumo(?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, insumoId);
            statement.setString(2, nombre);
            statement.setInt(3, cantidad);
            statement.setDate(4, fechaCaducidad);
            statement.setString(5, unidadMedida);
            statement.setDate(6, fechaLote);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarInsumoPorID(int insumoId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL EliminarInsumoPorID(?)}");
            statement.setInt(1, insumoId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarea> obtenerTodasLasTareas() {
        List<Tarea> tareas = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tareas");
            while (resultSet.next()) {
                Tarea tarea = new Tarea(
                    resultSet.getInt("id"),
                    resultSet.getString("descripcion"),
                    resultSet.getDate("fecha_inicio"),
                    resultSet.getTime("hora_inicio"),
                    resultSet.getDate("fecha_fin"),
                    resultSet.getTime("hora_fin"),
                    resultSet.getInt("asignada_a")
                );
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tareas;
    }
    
    public void crearTarea(String descripcion, Date fechaInicio, Time horaInicio,
                            Date fechaFin, Time horaFin, int asignadaA) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL CrearTarea(?, ?, ?, ?, ?, ?)}");
            statement.setString(1, descripcion);
            statement.setDate(2, fechaInicio);
            statement.setTime(3, horaInicio);
            statement.setDate(4, fechaFin);
            statement.setTime(5, horaFin);
            statement.setInt(6, asignadaA);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Tarea leerTareaPorID(int tareaId) {
        Tarea tarea = null;
        try {
            CallableStatement statement = connection.prepareCall("{CALL LeerTareaPorID(?)}");
            statement.setInt(1, tareaId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tarea = new Tarea(
                    resultSet.getInt("id"),
                    resultSet.getString("descripcion"),
                    resultSet.getDate("fecha_inicio"),
                    resultSet.getTime("hora_inicio"),
                    resultSet.getDate("fecha_fin"),
                    resultSet.getTime("hora_fin"),
                    resultSet.getInt("asignada_a")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarea;
    }

    public void actualizarTarea(int tareaId, String descripcion, Date fechaInicio, Time horaInicio,
                                Date fechaFin, Time horaFin, int asignadaA) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL ActualizarTarea(?, ?, ?, ?, ?, ?, ?)}");
            statement.setInt(1, tareaId);
            statement.setString(2, descripcion);
            statement.setDate(3, fechaInicio);
            statement.setTime(4, horaInicio);
            statement.setDate(5, fechaFin);
            statement.setTime(6, horaFin);
            statement.setInt(7, asignadaA);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarTareaPorID(int tareaId) {
        try {
            CallableStatement statement = connection.prepareCall("{CALL EliminarTareaPorID(?)}");
            statement.setInt(1, tareaId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    //////////////////////
    
    public List<Animal> verAnimalesNoAdoptados() {
        List<Animal> animales = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL VerAnimalesNoAdoptados()");
            while (resultSet.next()) {
                Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("especie"),
                    resultSet.getInt("edad"),
                    resultSet.getDate("fecha_ingreso"),
                    resultSet.getBoolean("adoptado"),
                    resultSet.getDate("fecha_adopcion"),
                    resultSet.getInt("adoptante_id"),
                    resultSet.getInt("insumo_id")
                );
                animales.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animal> verAnimalesAdoptados() {
        List<Animal> animales = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL VerAnimalesAdoptados()");
            while (resultSet.next()) {
                Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("especie"),
                    resultSet.getInt("edad"),
                    resultSet.getDate("fecha_ingreso"),
                    resultSet.getBoolean("adoptado"),
                    resultSet.getDate("fecha_adopcion"),
                    resultSet.getInt("adoptante_id"),
                    resultSet.getInt("insumo_id")
                );
                animales.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animal> verAnimalesAdoptadosPorAdoptante(int adoptanteId) {
        List<Animal> animales = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL VerAnimalesAdoptadosPorAdoptante(?)}");
            statement.setInt(1, adoptanteId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("especie"),
                    resultSet.getInt("edad"),
                    resultSet.getDate("fecha_ingreso"),
                    resultSet.getBoolean("adoptado"),
                    resultSet.getDate("fecha_adopcion"),
                    resultSet.getInt("adoptante_id"),
                    resultSet.getInt("insumo_id")
                );
                animales.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animal> verAnimalesPorInsumo(int insumoId) {
        List<Animal> animales = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL VerAnimalesPorInsumo(?)}");
            statement.setInt(1, insumoId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Animal animal = new Animal(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("especie"),
                    resultSet.getInt("edad"),
                    resultSet.getDate("fecha_ingreso"),
                    resultSet.getBoolean("adoptado"),
                    resultSet.getDate("fecha_adopcion"),
                    resultSet.getInt("adoptante_id"),
                    resultSet.getInt("insumo_id")
                );
                animales.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }
    
    public List<Tarea> verTareasPorColaborador(int colaboradorId) {
        List<Tarea> tareas = new ArrayList<>();
        try {
            CallableStatement statement = connection.prepareCall("{CALL VerTareasPorColaborador(?)}");
            statement.setInt(1, colaboradorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tarea tarea = new Tarea(
                    resultSet.getInt("id"),
                    resultSet.getString("descripcion"),
                    resultSet.getDate("fecha_inicio"),
                    resultSet.getTime("hora_inicio"),
                    resultSet.getDate("fecha_fin"),
                    resultSet.getTime("hora_fin"),
                    resultSet.getInt("asignada_a")
                );
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tareas;
    }

    public List<Insumo> verInsumosProximosACaducar() {
        List<Insumo> insumos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL VerInsumosProximosACaducar()");
            while (resultSet.next()) {
                Insumo insumo = new Insumo(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("cantidad"),
                    resultSet.getDate("fecha_caducidad"),
                    resultSet.getString("unidad_medida"),
                    resultSet.getDate("fecha_lote")
                );
                insumos.add(insumo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insumos;
    }

    public boolean verificarInsumoProximoACaducar(int insumoId) {
        boolean isProximo = false;
        try {
            CallableStatement statement = connection.prepareCall("{CALL VerificarInsumoProximoACaducar(?)}");
            statement.setInt(1, insumoId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isProximo = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isProximo;
    }
    
    public Colaborador verificarCredencialesColaborador(String cedula, String password) {
        Colaborador colaborador = null;
        try {
            CallableStatement statement = connection.prepareCall("{CALL VerificarCredencialesColaborador(?, ?)}");
            statement.setString(1, cedula);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                colaborador = new Colaborador(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("cedula"),
                    resultSet.getString("puesto"),
                    resultSet.getDate("fecha_contratacion"),
                    resultSet.getDouble("salario"),
                    resultSet.getString("password"),
                    resultSet.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colaborador;
    }
}
