package org.example.persistencia;
import org.example.logica.Empleados;
import org.example.persistencia.exceptions.NonexistentEntityException;
import java.util.List;

public class ControladorPersistencia {

    EmpleadosJpaController empleadoJpa = new EmpleadosJpaController();
    // Metodos CRUD

    // Creacion empleado
    public void crearEmpleado(Empleados empleados){
        empleadoJpa.create(empleados);
    }

    // Eliminacion empleado
    public void eliminarEmpleado(int id) throws NonexistentEntityException {
        empleadoJpa.destroy(id);
    }

    // Edicion - update empleado
    public void updateEmpleado(Empleados empleados) throws Exception {
        empleadoJpa.edit(empleados);
    }

    // Dentro de la clase ControladorPersistencia
    // Nuevo método para buscar empleado por ID
    public Empleados findEmpleado(int id) {
        return empleadoJpa.findEmpleados(id);
    }

    // Método para listar empleados
    public List<Empleados> listarEmpleados() {
        return empleadoJpa.findEmpleadosEntities();
    }

    // Nuevo método para buscar empleados por cargo
    public List<Empleados> buscarPorCargo(String cargo) {
        return empleadoJpa.findEmpleadosByCargo(cargo);
    }

    public List<Empleados> buscarEmpleadoPorInformacion(String nombre, String apellidos, String cargo) {
        return empleadoJpa.buscarEmpleadoPorInformacion(nombre, apellidos, cargo);
    }

}
