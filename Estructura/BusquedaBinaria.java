import java.util.List;

public class BusquedaBinaria {

    public static Suscriptor buscarPorCorreo(List<Suscriptor> lista, String correo) {
        int low = 0;
        int high = lista.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = lista.get(mid).getCorreo().compareToIgnoreCase(correo);
            if (cmp == 0) {
                return lista.get(mid);
            }
            if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
}