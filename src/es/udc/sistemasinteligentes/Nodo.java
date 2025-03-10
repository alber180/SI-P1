package es.udc.sistemasinteligentes;

public class Nodo implements Comparable<Nodo> {

    private Estado estado;
    private Nodo padre;
    private Accion accion;

    // Costes para A*
    private float g;  // coste del camino hasta este nodo
    private float h;  // heurística estimada
    private float f;  // f = g + h

    /**
     * Constructor tradicional (sin costes),
     * para no romper compatibilidad con el resto del código.
     */
    public Nodo(Estado estado, Nodo padre, Accion accion) {
        this(estado, padre, accion, 0, 0);
    }

    /**
     * Constructor para A*: incluye g y h, y calcula f = g + h.
     */
    public Nodo(Estado estado, Nodo padre, Accion accion, float g, float h) {
        this.estado = estado;
        this.padre = padre;
        this.accion = accion;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public Estado getEstado() {
        return estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public Accion getAccion() {
        return accion;
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }

    public float getF() {
        return f;
    }

    @Override
    public String toString() {
        return "( Estado: " + estado +
                " | Padre: " + (padre != null ? padre.getEstado() : "null ") +
                " | Acción: " + (accion != null ? accion : "null ") +
                " | g=" + g + ", h=" + h + ", f=" + f + " )\n";
    }

    /**
     * Para poder usar este Nodo en una cola de prioridad (A*),
     * comparamos según f = g + h.
     */
    @Override
    public int compareTo(Nodo otro) {
        return Float.compare(this.f, otro.f);
    }
}
