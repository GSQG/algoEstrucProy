public class UtilRecursivo {
    public static String listarRecursivo(Nodo nodo) {
        if (nodo == null) return "";
        var s = nodo.dato;
        String actual = String.format(
                "ID: %s, Nombre: %s, Correo: %s, Plan: %s\n",
                s.getId(), s.getNombre(), s.getCorreo(), s.getPlan()
        );
        return actual + listarRecursivo(nodo.siguiente);
    }
}