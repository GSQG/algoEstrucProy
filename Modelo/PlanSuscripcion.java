public enum PlanSuscripcion {
    BASICO("Básico"),
    PREMIUM("Premium"),
    PRO("Pro");

    private final String nombre;

    PlanSuscripcion(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public static PlanSuscripcion desdeTexto(String texto) {
        for (PlanSuscripcion p : values()) {
            if (p.nombre.equalsIgnoreCase(texto)) {
                return p;
            }
        }
        return BASICO;
    }
}