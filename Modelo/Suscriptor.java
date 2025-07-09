public class Suscriptor {
    private final String id;
    private final String nombre;
    private final String correo;
    private final PlanSuscripcion plan;

    public Suscriptor(String id, String nombre, String correo, PlanSuscripcion plan) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.plan = plan;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public PlanSuscripcion getPlan() {
        return plan;
    }
}
