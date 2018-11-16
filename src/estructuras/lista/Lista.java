package estructuras.lista;

public class Lista {
    private NodoLista cabeza;
    private int longitud;

    public Lista() {
        cabeza = null;
        longitud = 0;
    }

    public void setCabeza(NodoLista c) {
        this.cabeza = c;
    }

    public NodoLista getCabeza() {
        return cabeza;
    }

    public int longitud() {
        return longitud;
    }

    public boolean esVacia() {
        return cabeza == null;
    }

    public Lista insertarI(Object in) {
        NodoLista nuevo = new NodoLista(in);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        longitud++;
        return this;
    }

    public Lista insertar(Object e, int p) {
        NodoLista nuevo = new NodoLista(e);
        if (cabeza == null) cabeza = nuevo;
        else {
            NodoLista puntero = cabeza;
            int c = 0;
            while (c < p && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            nuevo.siguiente = puntero.siguiente;
            puntero.siguiente = nuevo;
        }
        longitud++;
        return this;
    }

    public Lista insertarF(Object f) {
        NodoLista nuevo = new NodoLista(f);
        if (cabeza == null) cabeza = nuevo;
        else {
            NodoLista p = cabeza;
            while (p.siguiente != null)
                p = p.siguiente;
            p.siguiente = nuevo;
        }
        longitud++;
        return this;
    }

    public int localiza(Object e) {
        if (cabeza != null) {
            NodoLista puntero = cabeza;
            int c = 0;
            while (c < longitud && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c < longitud) return c;
            else if (c != longitud) return Integer.parseInt(null);
        }
        return Integer.parseInt(null);
    }

    public Object localiza(int p) {
        if (cabeza == null)
            return "La lista solo tiene " + longitud + " elementos.";
        else {
            NodoLista puntero = cabeza;
            int c = 0;
            while (c < p && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c != p) return "La lista no contiene la posición " + p + ".";
            else return puntero.elemento;
        }
    }

    public Object anterior(int p) {
        NodoLista puntero = cabeza;
        int c = 0;

        if (!esVacia()) {
            while (c < longitud && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c != p) return "La lista no contiene la posición " + p + ".";
            else return puntero;
        } else return "La lista está vacía.";
    }

    public Object siguiente(int p) {
        NodoLista puntero = cabeza;
        int c = 0;

        if (!esVacia()) {
            while (c < longitud && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c < longitud) return puntero;
            else return "La lista no contiene la posición " + p + ".";
        } else return "La lista está vacía.";
    }

    public void suprimir(Object e) {
        if (cabeza != null) {
            if (e == cabeza.getElemento()) cabeza = cabeza.getSiguiente();
            else {
                NodoLista elim = cabeza;
                for (int i = 0; i < longitud; i++)
                    elim = elim.getSiguiente();
                NodoLista sig = elim.getSiguiente();
                elim.setSiguiente(sig.getSiguiente());
            }
            longitud--;
        }
    }

    public void suprimir(int p) {
        if (p >= 0 && p < longitud) {
            if (p == 0) cabeza = cabeza.getSiguiente();
            else {
                NodoLista elim = cabeza;
                for (int i = 0; i < (p - 1); i++)
                    elim = elim.getSiguiente();
                NodoLista sig = elim.getSiguiente();
                elim.setSiguiente(sig.getSiguiente());
            }
            longitud--;
        }
    }

    public void anula() {
        if (!esVacia()) {
            NodoLista puntero = cabeza;
            int c = 0;
            while (puntero.siguiente == null) {
                NodoLista elim = puntero;
                puntero = puntero.siguiente;
                elim = null;
                c++;
            }
            longitud = 0;
        } else System.out.println("La lista esta vacía.");
    }

    public void visualiza() {
        if (!esVacia()) {
            NodoLista in = cabeza;
            int c = 0;
            while (c < longitud && in != null) {
                System.out.println(c + ". " + in.getElemento());
                in = in.getSiguiente();
                c++;
            }
        } else System.out.println("Lista vacía.");
    }
}
