package org.example;
import org.example.logica.GestionEmpleados;
import org.example.persistencia.ControladorPersistencia;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Metodo scanner para la entrada de datos por el teclado
        Scanner scanner = new Scanner(System.in);
        // Crear un objeto ControladorPersistencia para interactuar con dicha capa
        ControladorPersistencia controladorPersistencia = new ControladorPersistencia();
        // Creacion un objeto GestionEmpleados, pasando el scanner y el ControladorPersistencia como parametros
        GestionEmpleados gestionEmpleados = new GestionEmpleados(scanner, controladorPersistencia);
        // Llamada para ejecutar el menu
        gestionEmpleados.ejecutar();
    }
}
