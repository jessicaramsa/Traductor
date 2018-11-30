package estructuras.lista;

public class Lista<Tipo> {
    private NodoLista cabeza;
    private int longitud;

    public Lista() {
        cabeza = null;
        longitud = 0;
    }

    public void setCabeza(NodoLista c) { this.cabeza = c; }

    public NodoLista getCabeza() { return cabeza; }

    public int longitud() { return longitud; }

    public boolean esVacia() { return cabeza == null; }

    public Lista insertarI(Tipo in) {
        NodoLista nuevo = new NodoLista(in);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        longitud++;
        return this;
    }

    public Lista insertar(Tipo e, int p) {
        NodoLista nuevo = new NodoLista(e);
        if (esVacia()) cabeza = nuevo;
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

    public Lista insertarF(Tipo f) {
        NodoLista nuevo = new NodoLista(f);
        if (esVacia()) cabeza = nuevo;
        else {
            NodoLista p = cabeza;
            while (p.siguiente != null) p = p.siguiente;
            p.siguiente = nuevo;
        }
        longitud++;
        return this;
    }

    public int localiza(Tipo e) {
        if (!esVacia()) {
            NodoLista puntero = cabeza;
            int c = 0;
            while (c < longitud && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c < longitud) return c;
            else if (c != longitud) return Integer.parseInt(null);
        }
        return -1;
    }

    public Tipo localiza(int p) {
        if (esVacia())
            return (Tipo) ("La lista solo tiene " + longitud + " elementos.");
        else {
            NodoLista puntero = cabeza;
            int c = 0;
            while (c < p && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c != p)
                return (Tipo) ("La lista no contiene la posición " + p + ".");
            else return (Tipo) puntero.elemento;
        }
    }

    public boolean estaElemento(Tipo elemento) {
        if (esVacia()) return false;
        else {
            NodoLista puntero = cabeza;
            boolean encontrado = false;
            while (puntero != null && !encontrado) {
                encontrado = puntero.elemento.equals(elemento);
                puntero = puntero.siguiente;
            }
            return encontrado;
        }
    }
    
    public Tipo anterior(int p) {
        NodoLista puntero = cabeza;
        int c = 0;

        if (!esVacia()) {
            while (c < longitud && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c != p)
                return (Tipo) ("La lista no contiene la posición " + p + ".");
            else return (Tipo) puntero;
        } else return (Tipo) "La lista está vacía.";
    }

    public Tipo siguiente(int p) {
        NodoLista puntero = cabeza;
        int c = 0;

        if (!esVacia()) {
            while (c < longitud && puntero.siguiente != null) {
                puntero = puntero.siguiente;
                c++;
            }
            if (c < longitud) return (Tipo) puntero;
            else return (Tipo) ("La lista no contiene la posición " + p + ".");
        } else return (Tipo) "La lista está vacía.";
    }

    public void suprimir(Tipo e) {
        if (!esVacia()) {
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
                for (int i = 0; i < (p - 1); i++) elim = elim.getSiguiente();
                NodoLista sig = elim.getSiguiente();
                elim.setSiguiente(sig.getSiguiente());
            }
            longitud--;
        }
    }

    public void anula() {
        if (!esVacia()) {
            NodoLista puntero = cabeza;
            while (puntero.siguiente == null) {
                NodoLista elim = puntero;
                puntero = puntero.siguiente;
                elim = null;
            }
            longitud = 0;
        } else System.out.println("La lista esta vacía.");
    }

    public void visualiza() {
        if (!esVacia()) {
            NodoLista posicion = cabeza;
            int c = 0;
            while (c < longitud && posicion != null) {
                System.out.println((c + 1) + "." + posicion.getElemento());
                c++;
                posicion = posicion.siguiente;
            }
        } else System.out.println("Lista vacía.");
    }
}
