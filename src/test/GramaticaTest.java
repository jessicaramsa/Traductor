package test;

import archivo.ManejadorArchivos;
import gramatica.FiltrarSimbolos;
import java.io.File;

public class GramaticaTest {
    public GramaticaTest() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrir();
        FiltrarSimbolos a = new FiltrarSimbolos(file);
        System.out.println("Producciones:");
        for (int i = 0; i < a.getProducciones().length; i++) {
            System.out.println((i + 1) + "." + a.getProducciones()[i]);
        }
        System.out.println("\nLado izquierdo sin repetir (NT):");
        for (int i = 0; i < a.getNoTerminales().length; i++) {
            System.out.println((i + 1) + "." + a.getNoTerminales()[i]);
        }
        System.out.println("\nLado derecho:");
        for (int i = 0; i < a.getLadoDerecho().length; i++) {
            System.out.println((i + 1) + "." + a.getLadoDerecho()[i]);
        }
        System.out.println("\nLado derecho sin repetir (T):");
        for (int i = 0; i < a.getTerminales().length; i++) {
            System.out.println((i + 1) + "." + a.getTerminales()[i]);
        }
        System.out.println("\nSímbolo inicial: " + a.simboloInicial);
    }
    
    public static void main(String[] args) {
        GramaticaTest test = new GramaticaTest();
    }
}
