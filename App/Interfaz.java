import javax.swing.*;

public class Interfaz {
    private final SuscripcionServicio servicio;

    public Interfaz() {
        servicio = new SuscripcionServicio();
    }

    public void mostrarMenu() {
        String[] opciones = {"Registrar", "Eliminar", "Buscar", "Listar", "Salir"};
        int opcion;
        do {
            opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Gestión de Suscripciones",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            switch (opcion) {
                case 0 -> registrar();
                case 1 -> eliminar();
                case 2 -> buscar();
                case 3 -> listar();
            }
        } while (opcion != 4);
    }

    private void registrar() {
        String nombre = JOptionPane.showInputDialog("Nombre:");
        String correo = JOptionPane.showInputDialog("Correo:");
        String[] planes = {"Básico", "Premium", "Pro"};
        String plan = (String) JOptionPane.showInputDialog(null, "Plan:", "Seleccionar Plan",
                JOptionPane.QUESTION_MESSAGE, null, planes, planes[0]);
        boolean exito = servicio.registrarSuscriptor(nombre, correo, plan);
        JOptionPane.showMessageDialog(null, exito ? "Registrado." : "Correo ya registrado.");
    }

    private void eliminar() {
        String correo = JOptionPane.showInputDialog("Correo a eliminar:");
        boolean exito = servicio.eliminarSuscriptor(correo);
        JOptionPane.showMessageDialog(null, exito ? "Eliminado." : "No encontrado.");
    }

    private void buscar() {
        String correo = JOptionPane.showInputDialog("Correo a buscar:");
        Suscriptor s = servicio.buscarSuscriptor(correo);
        String mensaje = s == null ? "No encontrado." :
                "ID: " + s.getId() + ", Nombre: " + s.getNombre() + ", Correo: " + s.getCorreo() + ", Plan: " + s.getPlan();
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private void listar() {
        String lista = servicio.listarSuscriptores();
        JOptionPane.showMessageDialog(null, lista);
    }
}
