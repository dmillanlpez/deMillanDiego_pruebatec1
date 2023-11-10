package org.example.persistencia;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.example.logica.Empleados;
import org.example.persistencia.exceptions.NonexistentEntityException;

public class EmpleadosJpaController implements Serializable {

    // Conexion a la unidad de persistencia
    public EmpleadosJpaController(){
        this.emf = Persistence.createEntityManagerFactory("empresaPU");
    }
    private EntityManagerFactory emf = null;

    // Gestor de entidades
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // ------ INICIO CRUD (generados mediante Netbeans)-----

    // Creacion de empleados
    public void create(Empleados empleados) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Edicion de empleados
    public void edit(Empleados empleados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            empleados = em.merge(empleados);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = empleados.getId();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("El empleado con el ID " + id + " ya no existe.");
                }
            }
            throw new Exception("Error al intentar editar el empleado.", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Borrado de empleados
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El empleado con el id " + id + " no existe.", enfe);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Busqueda de empleados
    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // Busqueda de empleados
    public Empleados findEmpleados(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }
    // Busqueda de empleados por su cargo en la empresa
    public List<Empleados> findEmpleadosByCargo(String cargo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Empleados> query = em.createQuery("SELECT e FROM Empleados e WHERE e.cargo = :cargo", Empleados.class);
            query.setParameter("cargo", cargo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Mostrar la cuenta de los empleados
    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    // Metodo para evitar duplicados
    public List<Empleados> buscarEmpleadoPorInformacion(String nombre, String apellidos, String cargo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT e FROM Empleados e WHERE e.nombre = :nombre AND e.apellidos = :apellidos AND e.cargo = :cargo");
            query.setParameter("nombre", nombre);
            query.setParameter("apellidos", apellidos);
            query.setParameter("cargo", cargo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }


}
