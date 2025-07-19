import java.util.*;

public class GrafoUsuarios {
    private final Map<Suscriptor, List<Suscriptor>> adj = new HashMap<>();

    public void addUsuario(Suscriptor s) {
        adj.putIfAbsent(s, new ArrayList<>());
    }

    public void addRelacion(Suscriptor a, Suscriptor b) {
        if (!adj.containsKey(a)) addUsuario(a);
        if (!adj.containsKey(b)) addUsuario(b);
        if (!adj.get(a).contains(b)) adj.get(a).add(b);
        if (!adj.get(b).contains(a)) adj.get(b).add(a);
    }

    public List<Suscriptor> dfs(Suscriptor start) {
        List<Suscriptor> visitados = new ArrayList<>();
        Set<Suscriptor> vistos = new HashSet<>();
        dfsRec(start, vistos, visitados);
        return visitados;
    }

    private void dfsRec(Suscriptor curr, Set<Suscriptor> vistos, List<Suscriptor> out) {
        if (curr == null || vistos.contains(curr)) return;
        vistos.add(curr);
        out.add(curr);
        for (Suscriptor vecino : adj.getOrDefault(curr, Collections.emptyList())) {
            dfsRec(vecino, vistos, out);
        }
    }

    public List<Suscriptor> bfs(Suscriptor start) {
        List<Suscriptor> out = new ArrayList<>();
        Queue<Suscriptor> q = new LinkedList<>();
        Set<Suscriptor> vistos = new HashSet<>();
        q.add(start);
        vistos.add(start);
        while (!q.isEmpty()) {
            Suscriptor u = q.poll();
            out.add(u);
            for (Suscriptor v : adj.getOrDefault(u, Collections.emptyList())) {
                if (!vistos.contains(v)) {
                    vistos.add(v);
                    q.add(v);
                }
            }
        }
        return out;
    }

    public int obtenerCantidadUsuarios() {
        return adj.size();
    }
}
