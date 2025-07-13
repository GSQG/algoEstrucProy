import java.util.Stack;

public class PilaOperaciones {
    public enum Operacion { REGISTRAR, ELIMINAR }
    private final Stack<Registro> pila = new Stack<>();

    public void push(Operacion operacion, String correo) {
        pila.push(new Registro(operacion, correo));
    }

    public Registro pop() {
        return pila.isEmpty() ? null : pila.pop();
    }

    public static class Registro {
        public final Operacion operacion;
        public final String correo;

        public Registro(Operacion operacion, String correo) {
            this.operacion = operacion;
            this.correo = correo;
        }
    }
}