import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Interfaz {
    private SuscripcionServicio servicio;
    private final JFrame frame;

    public Interfaz() {
        servicio = new SuscripcionServicio();
        frame = new JFrame("Sistema de Suscripciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
    }

    public void mostrarMenu() {
        String[] opciones = {
                "Registrar", "Eliminar", "Buscar", "Listar",
                "Ordenar", "Búsqueda Binaria", "Listar Recursivo",
                "Deshacer", "Notificaciones", "Buscar en Árbol",
                "DFS por Plan", "BFS por Plan", "Salir"
        };

        JPanel panel = new JPanel(new GridLayout(5, 3, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(240, 245, 250));

        Dimension btnSize = new Dimension(200, 50);
        Font font = new Font("Segoe UI", Font.BOLD, 16);

        for (int i = 0; i < opciones.length; i++) {
            final int index = i;
            JButton boton = new JButton(opciones[i]);
            boton.setPreferredSize(btnSize);
            boton.setBackground(new Color(33, 97, 140));
            boton.setForeground(Color.WHITE);
            boton.setFocusPainted(false);
            boton.setFont(font);
            boton.addActionListener(e -> ejecutarOpcion(index));
            panel.add(boton);
        }

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void ejecutarOpcion(int sel) {
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
            case 12 -> System.exit(0);
        }
    }

    public void registrar() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBackground(new Color(255, 255, 240));
        JTextField nombreField = new JTextField();
        JTextField correoField = new JTextField();
        String[] planes = {"Básico", "Premium", "Pro"};
        JComboBox<String> comboPlan = new JComboBox<>(planes);

        nombreField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        correoField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboPlan.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Plan:"));
        panel.add(comboPlan);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Registrar Suscriptor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String plan = (String) comboPlan.getSelectedItem();
            boolean ok = servicio.registrarSuscriptor(nombre, correo, plan);
            mostrarMensaje(ok ? "Registrado correctamente." : "Error al registrar.", ok);
        }
    }

    public void eliminar() {
        String correo = JOptionPane.showInputDialog(frame, "Correo a eliminar:", "Eliminar Suscriptor", JOptionPane.QUESTION_MESSAGE);
        boolean ok = servicio.eliminarSuscriptor(correo);
        mostrarMensaje(ok ? "Eliminado correctamente." : "Correo no encontrado.", ok);
    }

    public void buscar() {
        String correo = JOptionPane.showInputDialog(frame, "Correo a buscar:", "Buscar Suscriptor", JOptionPane.QUESTION_MESSAGE);
        Suscriptor s = servicio.buscarSuscriptor(correo);
        String msg = s != null
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s", s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        mostrarMensaje(msg, s != null);
    }

    public void listar() {
        String listado = servicio.listarSuscriptores();
        mostrarMensaje(listado, true);
    }

    public void ordenar() {
        List<Suscriptor> lista = servicio.obtenerTodos();
        OrdenadorSuscriptores.mergeSort(lista);
        StringBuilder sb = new StringBuilder();
        for (Suscriptor s : lista) {
            sb.append(String.format("ID: %s, Nombre: %s, Correo: %s, Plan: %s\n", s.getId(), s.getNombre(), s.getCorreo(), s.getPlan()));
        }
        mostrarMensaje(sb.toString(), true);
    }

    public void busquedaBinaria() {
        String correo = JOptionPane.showInputDialog(frame, "Correo a buscar:", "Búsqueda Binaria", JOptionPane.QUESTION_MESSAGE);
        Suscriptor s = servicio.buscarBinaria(correo);
        String msg = s != null
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s", s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        mostrarMensaje(msg, s != null);
    }

    public void listarRecursivo() {
        String listado = servicio.listarRecursivo();
        mostrarMensaje(listado, true);
    }

    public void deshacer() {
        boolean ok = servicio.deshacer();
        mostrarMensaje(ok ? "Última acción deshecha." : "No hay acciones para deshacer.", ok);
    }

    public void procesarNotificaciones() {
        String log = servicio.procesarNotificaciones();
        mostrarMensaje(log, true);
    }

    public void buscarEnArbol() {
        String correo = JOptionPane.showInputDialog(frame, "Correo a buscar en árbol:", "Buscar en Árbol", JOptionPane.QUESTION_MESSAGE);
        Suscriptor s = servicio.buscarEnArbol(correo);
        String msg = s != null
                ? String.format("ID: %s\nNombre: %s\nCorreo: %s\nPlan: %s", s.getId(), s.getNombre(), s.getCorreo(), s.getPlan())
                : "No encontrado";
        mostrarMensaje(msg, s != null);
    }

    public void dfsPorPlan() {
        String correo = JOptionPane.showInputDialog(frame, "Correo de inicio para DFS:", "DFS por Plan", JOptionPane.QUESTION_MESSAGE);
        Suscriptor inicio = servicio.buscarSuscriptor(correo);
        if (inicio == null) {
            mostrarMensaje("No encontrado.", false);
            return;
        }
        List<Suscriptor> dfs = servicio.dfs(inicio);
        StringBuilder sb = new StringBuilder("DFS desde " + correo + ":\n");
        for (Suscriptor s : dfs) {
            sb.append(s.getCorreo()).append(" (Plan: ").append(s.getPlan()).append(")\n");
        }
        mostrarMensaje(sb.toString(), true);
    }

    public void bfsPorPlan() {
        String correo = JOptionPane.showInputDialog(frame, "Correo de inicio para BFS:", "BFS por Plan", JOptionPane.QUESTION_MESSAGE);
        Suscriptor inicio = servicio.buscarSuscriptor(correo);
        if (inicio == null) {
            mostrarMensaje("No encontrado.", false);
            return;
        }
        List<Suscriptor> bfs = servicio.bfs(inicio);
        StringBuilder sb = new StringBuilder("BFS desde " + correo + ":\n");
        for (Suscriptor s : bfs) {
            sb.append(s.getCorreo()).append(" (Plan: ").append(s.getPlan()).append(")\n");
        }
        mostrarMensaje(sb.toString(), true);
    }

    private void mostrarMensaje(String msg, boolean exito) {
        JOptionPane.showMessageDialog(frame, new JScrollPane(new JTextArea(msg, 15, 50)), exito ? "Éxito" : "Aviso", exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 13));
        SwingUtilities.invokeLater(() -> new Interfaz().mostrarMenu());
    }
}
