package test;

import estructuras.piladinamica.Pila;

public class PilaTest {
    public static void main(String[] args) {
        Pila p = new Pila();
        for (int i = 0; i < 20; i++) {
            p.insertar(i + 1);
        }
        System.out.println("Pila");
        p.visualiza();
        System.out.println("Longitud pila: " + p.longitud());
        System.out.println("Posicion 2: " + p.buscarPosicion(2));
        System.out.println("Posicion 10: " + p.buscarPosicion(10));
        System.out.println("Posicion 18: " + p.buscarPosicion(18));
        System.out.println("Posicion 20: " + p.buscarPosicion(20));
        System.out.println("Posicion 200: " + p.buscarPosicion(200));
        
        System.out.println("Elemento 2: " + p.buscarElemento(2));
        System.out.println("Elemento 10: " + p.buscarElemento(10));
        System.out.println("Elemento 18: " + p.buscarElemento(18));
        System.out.println("Elemento 20: " + p.buscarElemento(20));
        System.out.println("Elemento 200: " + p.buscarElemento(200));
    }
}
