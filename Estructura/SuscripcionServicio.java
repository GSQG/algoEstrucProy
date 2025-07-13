import java.util.List;
import java.util.ArrayList;

public class SuscripcionServicio {
    private final ListaSuscriptores lista;
    private final PilaOperaciones pilaOps = new PilaOperaciones();
    private final ColaNotificaciones colaNotif = new ColaNotificaciones();
    private final ArbolSuscriptores arbol = new ArbolSuscriptores();
    private final GrafoUsuarios grafo = new GrafoUsuarios();

    public SuscripcionServicio() {
        lista = new ListaSuscriptores();
    }

    public boolean registrarSuscriptor(String nombre, String correo, String planTexto) {
        PlanSuscripcion plan = PlanSuscripcion.desdeTexto(planTexto);
        boolean exito = lista.agregar(nombre, correo, plan);
        if (exito) {
            pilaOps.push(PilaOperaciones.Operacion.REGISTRAR, correo);
            colaNotif.encolar(correo, "Registrado");
            Suscriptor s = lista.buscarPorCorreo(correo);
            arbol.insertar(s);
            grafo.addUsuario(s);
        }
        return exito;
    }

    public boolean eliminarSuscriptor(String correo) {
        boolean exito = lista.eliminar(correo);
        if (exito) {
            pilaOps.push(PilaOperaciones.Operacion.ELIMINAR, correo);
            colaNotif.encolar(correo, "Eliminado");
        }
        return exito;
    }

    public Suscriptor buscarSuscriptor(String correo) {
        return lista.buscarPorCorreo(correo);
    }

    public String listarSuscriptores() {
        return lista.listar();
    }

    public List<Suscriptor> obtenerTodos() {
        List<Suscriptor> salida = new ArrayList<>();
        Nodo actual = lista.getCabeza();
        while (actual != null) {
            salida.add(actual.dato);
            actual = actual.siguiente;
        }
        return salida;
    }

    public String listarRecursivo() {
        return UtilRecursivo.listarRecursivo(lista.getCabeza());
    }

    public Suscriptor buscarBinaria(String correo) {
        List<Suscriptor> copia = obtenerTodos();
        OrdenadorSuscriptores.mergeSort(copia);
        return BusquedaBinaria.buscarPorCorreo(copia, correo);
    }

    public boolean deshacer() {
        PilaOperaciones.Registro reg = pilaOps.pop();
        if (reg == null) return false;
        if (reg.operacion == PilaOperaciones.Operacion.REGISTRAR) {
            lista.eliminar(reg.correo);
            return true;
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
}