import javax.swing.JOptionPane;
import java.util.List;

public class Interfaz {
    private SuscripcionServicio servicio;

    public Interfaz() {
        servicio = new SuscripcionServicio();
    }

    public void mostrarMenu() {
        String[] opciones = {
                "Registrar", "Eliminar", "Buscar", "Listar",
                "Ordenar", "Búsqueda Binaria", "Listar Recursivo",
                "Deshacer", "Notificaciones", "Buscar en Árbol",
                "DFS por Plan", "BFS por Plan", "Salir"
        };
        while (true) {
            int sel = JOptionPane.showOptionDialog(
                    null, "Seleccione una opción", "Menú",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]
            );
            if (sel == -1 || sel == 12) break;
            switch (sel) {
                case 0: registrar(); break;
                case 1: eliminar(); break;
                case 2: buscar(); break;
                case 3: listar(); break;
                case 4: ordenar(); break;
                case 5: busquedaBinaria(); break;
                case 6: listarRecursivo(); break;
                case 7: deshacer(); break;
                case 8: procesarNotificaciones(); break;
                case 9: buscarEnArbol(); break;
                case 10: dfsPorPlan(); break;
                case 11: bfsPorPlan(); break;
            }
        }
    }

    public void registrar() {
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String correo = JOptionPane.showInputDialog("Correo:");
        String plan = JOptionPane.showInputDialog("Plan (Básico, Premium, Pro):");
        boolean ok = servicio.registrarSuscriptor(nombre, correo, plan);
        JOptionPane.showMessageDialog(null, ok ? "Registrado" : "Error al registrar");
    }

    public void eliminar() {
        String correo = JOptionPane.showInputDialog("Correo a eliminar:");
        boolean ok = servicio.eliminarSuscriptor(correo);
        JOptionPane.showMessageDialog(null, ok ? "Eliminado" : "No encontrado");
    }

    public void buscar() {
        String correo = JOptionPane.showInputDialog("Correo a buscar:");
        Suscriptor s = servicio.buscarSuscriptor(correo);
        String msg = s != null
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s",
                s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        JOptionPane.showMessageDialog(null, msg);
    }

    public void listar() {
        String listado = servicio.listarSuscriptores();
        JOptionPane.showMessageDialog(null, listado);
    }

    public void ordenar() {
        List<Suscriptor> lista = servicio.obtenerTodos();
        OrdenadorSuscriptores.mergeSort(lista);
        StringBuilder sb = new StringBuilder();
        for (Suscriptor s : lista) {
            sb.append(String.format(
                    "ID: %s, Nombre: %s, Correo: %s, Plan: %s\n",
                    s.getId(), s.getNombre(), s.getCorreo(), s.getPlan()
            ));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void busquedaBinaria() {
        String correo = JOptionPane.showInputDialog("Correo a buscar:");
        Suscriptor s = servicio.buscarBinaria(correo);
        String msg = s != null
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s",
                s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        JOptionPane.showMessageDialog(null, msg);
    }

    public void listarRecursivo() {
        String listado = servicio.listarRecursivo();
        JOptionPane.showMessageDialog(null, listado);
    }

    public void deshacer() {
        boolean ok = servicio.deshacer();
        JOptionPane.showMessageDialog(null, ok ? "Deshecho" : "Nada que deshacer");
    }

    public void procesarNotificaciones() {
        String log = servicio.procesarNotificaciones();
        JOptionPane.showMessageDialog(null, log);
    }

    public void buscarEnArbol() {
        String correo = JOptionPane.showInputDialog("Correo a buscar en árbol:");
        Suscriptor s = servicio.buscarEnArbol(correo);
        String msg = s != null
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s",
                s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        JOptionPane.showMessageDialog(null, msg);
    }

    public void dfsPorPlan() {
        String correo = JOptionPane.showInputDialog("Correo de inicio para DFS:");
        Suscriptor inicio = servicio.buscarSuscriptor(correo);
        if (inicio == null) {
            JOptionPane.showMessageDialog(null, "No encontrado");
            return;
        }
        List<Suscriptor> dfs = servicio.dfs(inicio);
        StringBuilder sb = new StringBuilder("DFS desde " + correo + ":\n");
        for (Suscriptor s : dfs) {
            sb.append(s.getCorreo()).append(" (Plan: ").append(s.getPlan()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void bfsPorPlan() {
        String correo = JOptionPane.showInputDialog("Correo de inicio para BFS:");
        Suscriptor inicio = servicio.buscarSuscriptor(correo);
        if (inicio == null) {
            JOptionPane.showMessageDialog(null, "No encontrado");
            return;
        }
        List<Suscriptor> bfs = servicio.bfs(inicio);
        StringBuilder sb = new StringBuilder("BFS desde " + correo + ":\n");
        for (Suscriptor s : bfs) {
            sb.append(s.getCorreo()).append(" (Plan: ").append(s.getPlan()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void main(String[] args) {
        new Interfaz().mostrarMenu();
    }
}