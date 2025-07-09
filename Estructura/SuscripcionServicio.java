public class SuscripcionServicio {
    private final ListaSuscriptores lista;

    public SuscripcionServicio() {
        lista = new ListaSuscriptores();
    }

    public boolean registrarSuscriptor(String nombre, String correo, String planTexto) {
        PlanSuscripcion plan = PlanSuscripcion.desdeTexto(planTexto);
        return lista.agregar(nombre, correo, plan);
    }

    public boolean eliminarSuscriptor(String correo) {
        return lista.eliminar(correo);
    }

    public Suscriptor buscarSuscriptor(String correo) {
        return lista.buscarPorCorreo(correo);
    }

    public String listarSuscriptores() {
        return lista.listar();
    }
}