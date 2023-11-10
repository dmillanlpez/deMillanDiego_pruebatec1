package org.example.logica;
import org.example.persistencia.ControladorPersistencia;
import org.example.persistencia.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class GestionEmpleados {

    private final Scanner scanner;
    private final ControladorPersistencia controladorPersistencia;
    private Empleados empleado;

    public GestionEmpleados(Scanner scanner, ControladorPersistencia controladorPersistencia) {
        this.scanner = scanner;
        this.controladorPersistencia = controladorPersistencia;
    }

    public void ejecutar() {
        boolean salir = false;
        int opcion;

        do {
            mostrarMenu();
            opcion = obtenerOpcion();
            ejecutarOpcion(opcion);

            if (opcion == 0) {
                salir = true;
            }

        } while (!salir);
    }

    private void mostrarMenu() {
        System.out.println("\n---- Bienvenido al Sistema de Gestion de Empleados ----\n");
        System.out.println("¿Que operacion deseas realizar?");
        System.out.println("1. Agregar un nuevo empleado");
        System.out.println("2. Listar todos los empleados");
        System.out.println("3. Actualizar información de un empleado");
        System.out.println("4. Eliminar a un empleado");
        System.out.println("5. Buscar empleados por su cargo");
        System.out.println("0. Salir de la aplicacion");
        System.out.print("\nIngrese el numero correspondiente a su eleccion: ");
    }

    private int obtenerOpcion() {
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea pendiente
        return opcion;
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarEmpleado();
                break;
            case 2:
                listarEmpleados();
                break;
            case 3:
                actualizarEmpleado();
                break;
            case 4:
                eliminarEmpleado();
                break;
            case 5:
                buscarPorCargo();
                break;
            case 0:
                System.out.println("Saliendo de la aplicación. Ten un muy buen dia!");
                break;
            default:
                System.err.println("Opcion no valida. Inténtelo de nuevo.");
        }
    }

    public void agregarEmpleado() {
        System.out.println("\n---- Agregar un nuevo empleado ----\n");

        // Obtener informacion del nuevo empleado desde el usuario
        scanner.nextLine();  // Limpieza de buffer por el salto de linea
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese los apellidos del empleado: ");
        String apellidos = scanner.nextLine();

        System.out.print("Ingrese el cargo del empleado: ");
        String cargo = scanner.nextLine();

        // Verifico si ya existe un usuario con la misma informacion en la bd
        if (existeEmpleado(nombre, apellidos, cargo)) {
            System.err.println("Ya existe un empleado con la misma informacion. No se pueden agregar duplicados.");
            return;  // Salir del metodo en el caso de que ya exista alguien con la misma informacion
        }

        System.out.print("Ingrese el salario del empleado: ");
        double salario = scanner.nextDouble();

        // Obtener la fecha de ingreso
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Ingrese la fecha de ingreso (formato dd/MM/yyyy): ");
        String fechaIngresoStr = scanner.nextLine();

        LocalDate fechaIngreso = null;
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaIngreso = LocalDate.parse(fechaIngresoStr, dateFormat);
        } catch (Exception e) {
            System.err.println("Formato de fecha incorrecto. El empleado se creara con la fecha actual.");
            fechaIngreso = LocalDate.now();  // Fecha actual si hay un error en el formato
        }

        // creacion del objeto empleado con la informacion introducida anteriormente
        empleado = new Empleados(0, nombre, apellidos, cargo, salario, fechaIngreso);

        try {
            // Llamar al metodo de ControladorPersistencia para crear el empleado
            controladorPersistencia.crearEmpleado(empleado);
            System.out.println("¡Empleado agregado con éxito!");
        } catch (Exception e) {
            System.err.println("Error al agregar el empleado.");
            e.printStackTrace();
        }
    }

    private boolean existeEmpleado(String nombre, String apellidos, String cargo) {
        try {
            // Llamar al metodo de ControladorPersistencia para buscar empleados por informacion
            List<Empleados> empleados = controladorPersistencia.buscarEmpleadoPorInformacion(nombre, apellidos, cargo);
            return !empleados.isEmpty();  // Devolver true si la lista no esta vacia (ya existe un empleado)
        } catch (Exception e) {
            System.err.println("Error al verificar la existencia del empleado.");
            e.printStackTrace();
            return false;  // En caso de error, se asume que no existe para evitar duplicados
        }
    }

    private void listarEmpleados() {
        System.out.println("\n---- Listar empleados ----\n");

        try {
            // Obtener la lista de empleados desde el controlador de persistencia
            List<Empleados> empleados = controladorPersistencia.listarEmpleados();

            if (empleados == null) {
                System.err.println("Error al obtener la lista de empleados desde el controlador de persistencia.");
            } else if (empleados.isEmpty()) {
                System.err.println("No hay empleados registrados en el sistema.");
            } else {
                // Mostrar la informacion de cada empleado
                for (Empleados empleado : empleados) {
                    System.out.println(empleado);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
    }

    // Actualizacion de un empleado
    private void actualizarEmpleado() {
        System.out.println("\n---- Actualizar un empleado ----\n");

        // Obtener el ID del empleado a actualizar
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int id = scanner.nextInt();

        try {
            // Obtener el empleado existente
            Empleados empleadoExistente = controladorPersistencia.findEmpleado(id);

            if (empleadoExistente != null) {
                // Mostrar informacion del empleado antes de la actualizacion
                System.out.println("\nInformación actual del empleado:");
                System.out.println(empleadoExistente);

                // Obtener la nueva informacion del empleado desde el usuario
                scanner.nextLine();  // Limpiar el buffer
                System.out.print("\nIngresa el nuevo nombre del empleado: ");
                String nuevoNombre = scanner.nextLine();

                System.out.print("Ingresa los nuevos apellidos del empleado: ");
                String nuevosApellidos = scanner.nextLine();

                System.out.print("Ingresa el nuevo cargo del empleado: ");
                String nuevoCargo = scanner.nextLine();

                System.out.print("Ingresa el nuevo salario del empleado: ");
                double nuevoSalario = scanner.nextDouble();

                // Crear el objeto Empleados con la informacion actualizada
                Empleados empleadoActualizado = new Empleados(id, nuevoNombre, nuevosApellidos, nuevoCargo, nuevoSalario, empleadoExistente.getFechaInicio());

                // Llamar al metodo de ControladorPersistencia para actualizar al empleado
                controladorPersistencia.updateEmpleado(empleadoActualizado);
                System.out.println("\n¡Empleado actualizado con exito!");
            } else {
                System.err.println("\nNo se encontro un empleado con el ID proporcionado.");
            }
        } catch (NonexistentEntityException nee) {
            System.err.println("\nError al actualizar el empleado: El empleado en cuestion no existe.");
            nee.printStackTrace();
        } catch (Exception e) {
            System.err.println("\nError al actualizar el empleado.");
            e.printStackTrace();
        }
    }

    // Eliminacion de un empleado
    private void eliminarEmpleado() {
        System.out.println("\n---- Eliminar un empleado ----\n");

        // Obtengo el ID del empleado para que este sea eliminado
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int idEmpleado = scanner.nextInt();

        try {
            // Llamar al metodo de ControladorPersistencia para eliminar al empleado
            controladorPersistencia.eliminarEmpleado(idEmpleado);
            System.out.println("¡Empleado eliminado con éxito!");
        } catch (NonexistentEntityException e) {
            System.err.println("Error al eliminar el empleado. No se encontro el empleado con el ID proporcionado.");
            e.printStackTrace();
        }
    }

    // Buscar por los diferentes cargos de trabajo
    private void buscarPorCargo() {
        System.out.print("Ingrese el cargo a buscar: ");
        String cargo = scanner.nextLine();

        try {
            // Llamar al método de ControladorPersistencia para buscar empleados por cargo
            List<Empleados> empleadosPorCargo = controladorPersistencia.buscarPorCargo(cargo);

            if (empleadosPorCargo.isEmpty()) {
                System.err.println("No se encontraron empleados con el cargo especificado.");
            } else {
                System.out.println("\nEmpleados con el cargo '" + cargo + "':\n");
                for (Empleados empleado : empleadosPorCargo) {
                    System.out.println(empleado);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar empleados por cargo.");
            e.printStackTrace();
        }
    }
}
