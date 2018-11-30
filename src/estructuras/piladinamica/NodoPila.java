package estructuras.piladinamica;

public class NodoPila<Tipo> {
    Tipo elemento;
    NodoPila siguiente;

    public NodoPila() {}

    public NodoPila(Tipo e) {
        this.elemento = e;
    }

    public NodoPila(Tipo e, NodoPila n) {
        this.elemento = e;
        this.siguiente = n;
    }

    public void setElemento(Tipo e) {
        this.elemento = e;
    }

    public Tipo getElemento() {
        return elemento;
    }

    public void setSiguiente(NodoPila s) {
        this.siguiente = s;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }
}