public class UtilRecursivo {
    public static String listarRecursivo(Object nodoObj) {
        StringBuilder sb = new StringBuilder();
        listar(sb, nodoObj);
        return sb.toString();
    }

    private static void listar(StringBuilder sb, Object nodoObj) {
        try {
            if (nodoObj == null) return;
            Class<?> clazz = nodoObj.getClass();
            Object dato = clazz.getField("dato").get(nodoObj);
            Object siguiente = clazz.getField("siguiente").get(nodoObj);
            if (dato instanceof Suscriptor s) {
                sb.append("ID: ").append(s.getId())
                        .append(", Nombre: ").append(s.getNombre())
                        .append(", Correo: ").append(s.getCorreo())
                        .append(", Plan: ").append(s.getPlan())
                        .append("\n");
            }
            listar(sb, siguiente);
        } catch (Exception e) {
            sb.append("Error al recorrer: ").append(e.getMessage());
        }
    }
}