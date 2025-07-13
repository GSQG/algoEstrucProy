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
                "Deshacer", "Notificaciones", "Salir"
        };
        while (true) {
            int sel = JOptionPane.showOptionDialog(
                    null, "Seleccione una opción", "Menú",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]
            );
            if (sel == -1 || sel == 9) break;
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

    public static void main(String[] args) {
        new Interfaz().mostrarMenu();
    }
}