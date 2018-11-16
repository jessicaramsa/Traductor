package estructuras.piladinamica;

public class NodoPila {
    Object elemento;
    NodoPila siguiente;

    public NodoPila() {}

    public NodoPila(Object e) {
        this.elemento = e;
    }

    public NodoPila(Object e, NodoPila n) {
        this.elemento = e;
        this.siguiente = n;
    }

    public void setElemento(Object e) {
        this.elemento = e;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setSiguiente(NodoPila s) {
        this.siguiente = s;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }
}