public class UtilRecursivo {
    public static String listarRecursivo(Object nodo) {
        StringBuilder sb = new StringBuilder();
        listar(sb, nodo);
        return sb.toString();
    }

    private static void listar(StringBuilder sb, Object nodo) {
        if (nodo == null) return;
        try {
            Class<?> clazz = nodo.getClass();

            var campoDato = clazz.getDeclaredField("dato");
            var campoSig = clazz.getDeclaredField("siguiente");
            campoDato.setAccessible(true);
            campoSig.setAccessible(true);

            Object dato = campoDato.get(nodo);
            Object siguiente = campoSig.get(nodo);

            if (dato instanceof Suscriptor s) {
                sb.append("ID: ").append(s.getId())
                        .append(", Nombre: ").append(s.getNombre())
                        .append(", Correo: ").append(s.getCorreo())
                        .append(", Plan: ").append(s.getPlan())
                        .append("\n");
            }

            listar(sb, siguiente);
        } catch (Exception e) {
            sb.append("Error: ").append(e.getMessage());
        }
    }
}