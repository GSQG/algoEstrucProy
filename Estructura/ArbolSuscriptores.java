public class ArbolSuscriptores {

    private class NodoA {
        Suscriptor dato;
        NodoA izq;
        NodoA der;

        NodoA(Suscriptor dato) {
            this.dato = dato;
        }
    }

    private NodoA raiz;

    public void insertar(Suscriptor s) {
        raiz = insertarRec(raiz, s);
    }

    private NodoA insertarRec(NodoA nodo, Suscriptor s) {
        if (nodo == null) {
            return new NodoA(s);
        }
        if (s.getCorreo().compareToIgnoreCase(nodo.dato.getCorreo()) < 0) {
            nodo.izq = insertarRec(nodo.izq, s);
        } else {
            nodo.der = insertarRec(nodo.der, s);
        }
        return nodo;
    }

    public Suscriptor buscar(String correo) {
        return buscarRec(raiz, correo);
    }

    private Suscriptor buscarRec(NodoA nodo, String correo) {
        if (nodo == null) {
            return null;
        }
        int cmp = correo.compareToIgnoreCase(nodo.dato.getCorreo());
        if (cmp == 0) {
            return nodo.dato;
        }
        if (cmp < 0) {
            return buscarRec(nodo.izq, correo);
        } else {
            return buscarRec(nodo.der, correo);
        }
    }
}