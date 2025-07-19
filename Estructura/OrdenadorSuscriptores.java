import java.util.ArrayList;
import java.util.List;

public class OrdenadorSuscriptores {

    public static void mergeSort(List<Suscriptor> lista) {
        if (lista.size() < 2) return;
        int mid = lista.size() / 2;
        List<Suscriptor> left = lista.subList(0, mid);
        List<Suscriptor> right = lista.subList(mid, lista.size());
        mergeSort(left);
        mergeSort(right);
        mezclar(left, right, lista);
    }

    private static void mezclar(List<Suscriptor> L, List<Suscriptor> R, List<Suscriptor> out) {
        int i = 0, j = 0, k = 0;
        while (i < L.size() && j < R.size()) {
            if (L.get(i).getNombre().compareToIgnoreCase(R.get(j).getNombre()) <= 0) {
                out.set(k++, L.get(i++));
            } else {
                out.set(k++, R.get(j++));
            }
        }
        while (i < L.size()) {
            out.set(k++, L.get(i++));
        }
        while (j < R.size()) {
            out.set(k++, R.get(j++));
        }
    }

    public static void mergeSortPorCorreo(List<Suscriptor> lista) {
        if (lista.size() < 2) return;
        int mid = lista.size() / 2;
        List<Suscriptor> left = new ArrayList<>(lista.subList(0, mid));
        List<Suscriptor> right = new ArrayList<>(lista.subList(mid, lista.size()));
        mergeSortPorCorreo(left);
        mergeSortPorCorreo(right);
        mezclarPorCorreo(left, right, lista);
    }

    private static void mezclarPorCorreo(List<Suscriptor> L, List<Suscriptor> R, List<Suscriptor> out) {
        int i = 0, j = 0, k = 0;
        while (i < L.size() && j < R.size()) {
            if (L.get(i).getCorreo().compareToIgnoreCase(R.get(j).getCorreo()) <= 0) {
                out.set(k++, L.get(i++));
            } else {
                out.set(k++, R.get(j++));
            }
        }
        while (i < L.size()) out.set(k++, L.get(i++));
        while (j < R.size()) out.set(k++, R.get(j++));
    }

}