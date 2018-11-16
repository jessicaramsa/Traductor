package estructuras.piladinamica;

public class Pila {
    private NodoPila cima;
    private int longitud;
    private final int capacidad;

    public Pila() {
        this.capacidad = 200;
    }

    public Pila(int c) {
        this.cima = null;
        this.longitud = 0;
        this.capacidad = c;
    }

    public Object obtCima() {
        return cima.elemento;
    }

    public int longitud() {
        return longitud;
    }

    public int capMaxima() {
        return capacidad;
    }

    public void insertar(Object e) {
        if (pLlena())
            System.out.println("Desbordamiento de pila.");
        else {
            NodoPila nuevo = new NodoPila(e);
            nuevo.siguiente = cima;
            cima = nuevo;
            longitud++;
        }
    }

    public void eliminarCima() {
        if (!esVacia()) {
            NodoPila antiguo = cima;
            cima = cima.siguiente;
            antiguo.siguiente = null;
            longitud--;
        } else
            System.out.println("Pila vacía, operación no posible.");
    }
    
    public boolean buscarElemento(Object e) {
        if (!esVacia() && e != null) {
            NodoPila posicion = this.cima;
            boolean encontrado = posicion.elemento.equals(e);
            int cont = 0;
            while (cont < longitud && !encontrado) {
                cont++;
                encontrado = posicion.elemento.equals(e);
                if (!encontrado) posicion = posicion.siguiente;
            }
            return encontrado;
        } else return false;
    }
    
    public int buscarElementoPosicion(Object e) {
        if (!esVacia() && e != null) {
            NodoPila posicion = this.cima;
            boolean encontrado = posicion.elemento.equals(e);
            int cont = 0;
            while (cont < longitud && !encontrado) {
                cont++;
                encontrado = posicion.elemento.equals(e);
                if (!encontrado) posicion = posicion.siguiente;
            }
            return encontrado ? cont : null;
        } else return 0;
    }
    
    public Object buscarPosicion(int index) {
        if (!esVacia()) {
            index = this.longitud - (index);
            NodoPila posicion = cima;
            boolean encontrado = index == 0;
            int cont = 0;
            while (cont < longitud && !encontrado) {
                cont++;
                encontrado = index == cont;
                if (!encontrado) posicion = posicion.siguiente;
            }
            return encontrado ? posicion.elemento : null;
        } else return null;
    }
    
    public boolean pLlena() {
        return longitud == capacidad;
    }

    public boolean esVacia() {
        return cima == null;
    }

    public void limpiar() {
        cima = null;
        longitud = 0;
    }

    public void visualiza() {
        if (!esVacia()) {
            NodoPila posicion = cima;
            int cont = 0;
            while (cont < longitud && posicion != null) {
                System.out.println((this.longitud - cont) + "." + posicion.elemento);
                cont++;
                posicion = posicion.siguiente;
            }
        } else System.out.println("Pila vacía.");
    }
}