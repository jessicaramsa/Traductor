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
        a.getProducciones().visualiza();
        System.out.println("\nLado izquierdo sin repetir (NT):");
        a.getNoTerminales().visualiza();
        System.out.println("\nLado derecho:");
        a.getLadoDerecho().visualiza();
        System.out.println("\nLado derecho sin repetir (T):");
        a.getTerminales().visualiza();
        System.out.println("\nSímbolo inicial: " + a.simboloInicial);
    }
    
    public static void main(String[] args) {
        GramaticaTest test = new GramaticaTest();
    }
}
