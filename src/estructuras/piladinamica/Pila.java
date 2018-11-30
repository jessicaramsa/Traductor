package estructuras.piladinamica;

public class Pila<Tipo> {
    private NodoPila<Tipo> cima;
    private int longitud;

    public Pila() {
        this.cima = null;
    }

    public Pila(int c) {
        this.cima = null;
        this.longitud = 0;
    }

    public Tipo obtCima() {
        return cima.elemento;
    }

    public void insertar(Tipo e) {
        NodoPila nuevo = new NodoPila(e);
        nuevo.siguiente = cima;
        cima = nuevo;
        longitud++;
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