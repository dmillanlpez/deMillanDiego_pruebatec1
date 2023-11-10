package org.example.logica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity // Reconocida por el JPA
public class Empleados {

    // Constructor empty
    public Empleados() {
    }
    // Constructor completo
    public Empleados(int id, String nombre, String apellidos, String cargo, double salario, LocalDate fechaInicio) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
    }

    // Getters || Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    // --------- FIN GETTERS Y SETTERS ------------

    // Metodo toString() para muestra de datos por pantalla cuando sea necesario
    @Override
    public String toString() {
        return String.format(
                "Empleado: %s | Apellido: %s | Cargo: %s | Salario: %.2f euros | Fecha Inicio Puesto: %s",
                nombre, apellidos, cargo, salario, fechaInicio
        );
    }


    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generacion automatica de PK
    private int id;
    private String nombre; // Nombre del empleado
    private String apellidos; // Apellido del empleado
    private String cargo; // Cargo del empleado
    private double salario; // Salario del empleado
    private LocalDate fechaInicio; // Fecha en la que el trabajador inicio su puesto
}
