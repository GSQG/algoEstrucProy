import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class SuscripcionServicio {
    private ConexionMySQL conexionMySQL;
    private Connection conexion;
    private Nodo cabeza;
    private int contador;
    private PilaOperaciones pilaOps;
    private ColaNotificaciones colaNotif;
    private ArbolSuscriptores arbol;
    private GrafoUsuarios grafo;

    public SuscripcionServicio() {
        conexionMySQL = new ConexionMySQL();
        conexion = conexionMySQL.getConexion();
        pilaOps = new PilaOperaciones();
        colaNotif = new ColaNotificaciones();
        arbol = new ArbolSuscriptores();
        grafo = new GrafoUsuarios();
        cabeza = null;
        contador = 1;
        try (PreparedStatement pst = conexion.prepareStatement("SELECT id, nombre, correo, plan FROM suscriptores");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Suscriptor s = new Suscriptor(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        PlanSuscripcion.desdeTexto(rs.getString("plan"))
                );
                Nodo nuevo = new Nodo(s);
                nuevo.siguiente = cabeza;
                cabeza = nuevo;
                arbol.insertar(s);
                grafo.addUsuario(s);
            }
            conectarUsuariosPorPlan();
            contador = grafo.obtenerCantidadUsuarios() + 1;
        } catch (SQLException e) {}
    }

    private void conectarUsuariosPorPlan() {
        List<Suscriptor> lista = obtenerTodos();
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(i).getPlan().equals(lista.get(j).getPlan())) {
                    grafo.addRelacion(lista.get(i), lista.get(j));
                }
            }
        }
    }

    public boolean registrarSuscriptor(String nombre, String correo, String planTexto) {
        String id = String.format("S%03d", contador++);
        PlanSuscripcion plan = PlanSuscripcion.desdeTexto(planTexto);
        try (PreparedStatement pst = conexion.prepareStatement("INSERT INTO suscriptores (id, nombre, correo, plan) VALUES (?, ?, ?, ?)")) {
            pst.setString(1, id);
            pst.setString(2, nombre);
            pst.setString(3, correo);
            pst.setString(4, plan.toString());
            if (pst.executeUpdate() > 0) {
                Suscriptor s = new Suscriptor(id, nombre, correo, plan);
                Nodo nuevo = new Nodo(s);
                nuevo.siguiente = cabeza;
                cabeza = nuevo;
                pilaOps.push(PilaOperaciones.Operacion.REGISTRAR, correo);
                colaNotif.encolar(correo, "Registrado");
                arbol.insertar(s);
                grafo.addUsuario(s);
                for (Suscriptor otro : obtenerTodos()) {
                    if (!otro.getCorreo().equalsIgnoreCase(s.getCorreo()) && otro.getPlan().equals(s.getPlan())) {
                        grafo.addRelacion(s, otro);
                    }
                }
                return true;
            }
        } catch (SQLException e) {}
        return false;
    }

    public boolean eliminarSuscriptor(String correo) {
        Nodo curr = cabeza;
        Nodo prev = null;
        while (curr != null && !curr.dato.getCorreo().equalsIgnoreCase(correo)) {
            prev = curr;
            curr = curr.siguiente;
        }
        if (curr == null) return false;
        String id = curr.dato.getId();
        try (PreparedStatement pst = conexion.prepareStatement("DELETE FROM suscriptores WHERE id = ?")) {
            pst.setString(1, id);
            if (pst.executeUpdate() > 0) {
                if (prev == null) cabeza = curr.siguiente;
                else prev.siguiente = curr.siguiente;
                pilaOps.push(PilaOperaciones.Operacion.ELIMINAR, correo);
                colaNotif.encolar(correo, "Eliminado");
                return true;
            }
        } catch (SQLException e) {}
        return false;
    }

    public Suscriptor buscarSuscriptor(String correo) {
        Nodo curr = cabeza;
        while (curr != null) {
            if (curr.dato.getCorreo().equalsIgnoreCase(correo)) return curr.dato;
            curr = curr.siguiente;
        }
        return null;
    }

    public String listarSuscriptores() {
        StringBuilder sb = new StringBuilder();
        Nodo curr = cabeza;
        while (curr != null) {
            Suscriptor s = curr.dato;
            sb.append("ID: ").append(s.getId())
                    .append(", Nombre: ").append(s.getNombre())
                    .append(", Correo: ").append(s.getCorreo())
                    .append(", Plan: ").append(s.getPlan())
                    .append("\n");
            curr = curr.siguiente;
        }
        return sb.toString();
    }

    public List<Suscriptor> obtenerTodos() {
        List<Suscriptor> lista = new ArrayList<>();
        Nodo curr = cabeza;
        while (curr != null) {
            lista.add(curr.dato);
            curr = curr.siguiente;
        }
        return lista;
    }

    public String listarRecursivo() {
        return UtilRecursivo.listarRecursivo(cabeza);
    }

    public Suscriptor buscarBinaria(String correo) {
        List<Suscriptor> lista = obtenerTodos();
        OrdenadorSuscriptores.mergeSort(lista);
        return BusquedaBinaria.buscarPorCorreo(lista, correo);
    }

    public boolean deshacer() {
        PilaOperaciones.Registro reg = pilaOps.pop();
        if (reg == null) return false;
        if (reg.operacion == PilaOperaciones.Operacion.REGISTRAR) {
            return eliminarSuscriptor(reg.correo);
        }
        return false;
    }

    public String procesarNotificaciones() {
        StringBuilder sb = new StringBuilder();
        while (!colaNotif.estaVacia()) {
            ColaNotificaciones.Notificacion n = colaNotif.desencolar();
            sb.append(n.correo).append(": ").append(n.mensaje).append("\n");
        }
        return sb.toString();
    }

    public Suscriptor buscarEnArbol(String correo) {
        return arbol.buscar(correo);
    }

    public List<Suscriptor> dfs(Suscriptor inicio) {
        return grafo.dfs(inicio);
    }

    public List<Suscriptor> bfs(Suscriptor inicio) {
        return grafo.bfs(inicio);
    }

    private static class Nodo {
        Suscriptor dato;
        Nodo siguiente;
        Nodo(Suscriptor dato) { this.dato = dato; }
    }
}