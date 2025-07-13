import java.util.LinkedList;
import java.util.Queue;

public class ColaNotificaciones {
    public static class Notificacion {
        public final String correo;
        public final String mensaje;

        public Notificacion(String correo, String mensaje) {
            this.correo = correo;
            this.mensaje = mensaje;
        }
    }

    private final Queue<Notificacion> cola = new LinkedList<>();

    public void encolar(String correo, String mensaje) {
        cola.add(new Notificacion(correo, mensaje));
    }

    public Notificacion desencolar() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
}