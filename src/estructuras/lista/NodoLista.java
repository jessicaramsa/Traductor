package estructuras.lista;

public class NodoLista {
    Object elemento;
    NodoLista siguiente = null;

    public NodoLista() {}

    public NodoLista(Object e) {
        this.elemento = e;
    }

    public NodoLista(Object e, NodoLista n) {
        this.elemento = e;
        this.siguiente = n;
    }

    public void setElemento(Object e) {
        this.elemento = e;
    }

    public void setSiguiente(NodoLista s) {
        this.siguiente = s;
    }

    public Object getElemento() {
        return elemento;
    }

    public NodoLista getSiguiente() {
        return siguiente;
    }
}
