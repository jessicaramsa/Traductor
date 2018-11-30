package estructuras.lista;

public class NodoLista<Tipo> {
    Tipo elemento;
    NodoLista siguiente = null;

    public NodoLista() {}

    public NodoLista(Tipo e) {
        this.elemento = e;
    }

    public NodoLista(Tipo e, NodoLista n) {
        this.elemento = e;
        this.siguiente = n;
    }

    public void setElemento(Tipo e) {
        this.elemento = e;
    }

    public void setSiguiente(NodoLista s) {
        this.siguiente = s;
    }

    public Tipo getElemento() {
        return elemento;
    }

    public NodoLista getSiguiente() {
        return siguiente;
    }
}
