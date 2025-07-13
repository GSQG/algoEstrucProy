public class ListaSuscriptores {
    private Nodo cabeza;
    private int contador;

    public ListaSuscriptores() {
        contador = 1;
    }

    public boolean agregar(String nombre, String correo, PlanSuscripcion plan) {
        if (buscarPorCorreo(correo) != null) return false;
        String id = "S" + String.format("%03d", contador++);
        Nodo nuevo = new Nodo(new Suscriptor(id, nombre, correo, plan));
        if (cabeza == null) cabeza = nuevo;
        else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) actual = actual.siguiente;
            actual.siguiente = nuevo;
        }
        return true;
    }

    public boolean eliminar(String correo) {
        if (cabeza == null) return false;
        if (cabeza.dato.getCorreo().equalsIgnoreCase(correo)) {
            cabeza = cabeza.siguiente;
            return true;
        }
        Nodo actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.getCorreo().equalsIgnoreCase(correo)) {
            actual = actual.siguiente;
        }
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            return true;
        }
        return false;
    }

    public Suscriptor buscarPorCorreo(String correo) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.getCorreo().equalsIgnoreCase(correo)) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    public String listar() {
        StringBuilder sb = new StringBuilder();
        Nodo actual = cabeza;
        while (actual != null) {
            Suscriptor s = actual.dato;
            sb.append("ID: ").append(s.getId())
                    .append(", Nombre: ").append(s.getNombre())
                    .append(", Correo: ").append(s.getCorreo())
                    .append(", Plan: ").append(s.getPlan())
                    .append("\n");
            actual = actual.siguiente;
        }
        return sb.length() == 0 ? "No hay suscriptores." : sb.toString();
    }

    public Nodo getCabeza() {
        return cabeza;
    }
}