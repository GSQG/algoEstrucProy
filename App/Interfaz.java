import javax.swing.*;
import java.awt.*;
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
                    null, "Seleccione una opción", "Menú Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]
            );
            if (sel == -1 || sel == 12) break;
            switch (sel) {
                case 0 -> registrar();
                case 1 -> eliminar();
                case 2 -> buscar();
                case 3 -> listar();
                case 4 -> ordenar();
                case 5 -> busquedaBinaria();
                case 6 -> listarRecursivo();
                case 7 -> deshacer();
                case 8 -> procesarNotificaciones();
                case 9 -> buscarEnArbol();
                case 10 -> dfsPorPlan();
                case 11 -> bfsPorPlan();
            }
        }
    }

    public void registrar() {
        JTextField nombreField = new JTextField();
        JTextField correoField = new JTextField();
        JComboBox<String> planBox = new JComboBox<>(new String[]{"Básico", "Premium", "Pro"});

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Plan:"));
        panel.add(planBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Suscriptor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String plan = (String) planBox.getSelectedItem();
            boolean ok = servicio.registrarSuscriptor(nombre, correo, plan);
            JOptionPane.showMessageDialog(null, ok ? "Registrado correctamente." : "Error al registrar.");
        }
    }

    public void eliminar() {
        String correo = inputCorreo("Eliminar Suscriptor", "Correo a eliminar:");
        if (correo != null) {
            boolean ok = servicio.eliminarSuscriptor(correo);
            JOptionPane.showMessageDialog(null, ok ? "Eliminado correctamente." : "No encontrado.");
        }
    }

    public void buscar() {
        String correo = inputCorreo("Buscar Suscriptor", "Correo a buscar:");
        if (correo != null) {
            Suscriptor s = servicio.buscarSuscriptor(correo);
            mostrarSuscriptor(s, "Resultado de Búsqueda");
        }
    }

    public void listar() {
        String listado = servicio.listarSuscriptores();
        JOptionPane.showMessageDialog(null, listado.isEmpty() ? "No hay suscriptores." : listado, "Listado", JOptionPane.INFORMATION_MESSAGE);
    }

    public void ordenar() {
        List<Suscriptor> lista = servicio.obtenerTodos();
        OrdenadorSuscriptores.mergeSort(lista);
        StringBuilder sb = new StringBuilder("Listado Ordenado por Nombre:\n");
        for (Suscriptor s : lista) {
            sb.append(String.format("ID: %s, Nombre: %s, Correo: %s, Plan: %s\n", s.getId(), s.getNombre(), s.getCorreo(), s.getPlan()));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void busquedaBinaria() {
        String correo = inputCorreo("Búsqueda Binaria", "Correo a buscar:");
        if (correo != null) {
            Suscriptor s = servicio.buscarBinaria(correo);
            mostrarSuscriptor(s, "Resultado Búsqueda Binaria");
        }
    }

    public void listarRecursivo() {
        String listado = servicio.listarRecursivo();
        JOptionPane.showMessageDialog(null, listado, "Listado Recursivo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deshacer() {
        boolean ok = servicio.deshacer();
        JOptionPane.showMessageDialog(null, ok ? "Operación deshecha correctamente." : "Nada que deshacer.");
    }

    public void procesarNotificaciones() {
        String log = servicio.procesarNotificaciones();
        JOptionPane.showMessageDialog(null, log.isEmpty() ? "No hay notificaciones." : log, "Notificaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    public void buscarEnArbol() {
        String correo = inputCorreo("Buscar en Árbol", "Correo a buscar:");
        if (correo != null) {
            Suscriptor s = servicio.buscarEnArbol(correo);
            mostrarSuscriptor(s, "Resultado Árbol Binario");
        }
    }

    public void dfsPorPlan() {
        String correo = inputCorreo("DFS por Plan", "Correo de inicio:");
        if (correo != null) {
            Suscriptor inicio = servicio.buscarSuscriptor(correo);
            if (inicio == null) {
                JOptionPane.showMessageDialog(null, "No encontrado");
                return;
            }
            List<Suscriptor> dfs = servicio.dfs(inicio);
            mostrarListaBusqueda("DFS desde " + correo, dfs);
        }
    }

    public void bfsPorPlan() {
        String correo = inputCorreo("BFS por Plan", "Correo de inicio:");
        if (correo != null) {
            Suscriptor inicio = servicio.buscarSuscriptor(correo);
            if (inicio == null) {
                JOptionPane.showMessageDialog(null, "No encontrado");
                return;
            }
            List<Suscriptor> bfs = servicio.bfs(inicio);
            mostrarListaBusqueda("BFS desde " + correo, bfs);
        }
    }

    private String inputCorreo(String titulo, String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    private void mostrarSuscriptor(Suscriptor s, String titulo) {
        String msg = (s != null)
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s",
                s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        JOptionPane.showMessageDialog(null, msg, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarListaBusqueda(String titulo, List<Suscriptor> lista) {
        StringBuilder sb = new StringBuilder(titulo + ":\n");
        for (Suscriptor s : lista) {
            sb.append(s.getCorreo()).append(" (").append(s.getPlan()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Interfaz().mostrarMenu();
    }
}
