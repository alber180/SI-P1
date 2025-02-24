package es.udc.sistemasinteligentes;

public class Nodo {

    private Estado estado;
    private Nodo padre;
    private Accion accion;

    public Nodo (Estado estado, Nodo padre, Accion accion) {
        this.estado = estado;
        this.padre = padre;
        this.accion = accion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    @Override
    public String toString() {
        return  "( " + estado +
                ", " + padre  +
                ", " + accion +
                ')';
    }
}