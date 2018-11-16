package test;

import archivo.ManejadorArchivos;
import gramatica.gramatica.FiltrarSimbolos;
import java.io.File;

public class GramaticaTest {
    public GramaticaTest() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrir();
        FiltrarSimbolos a = new FiltrarSimbolos(file);
        System.out.println("Producciones:");
        a.producciones.visualiza();
        System.out.println("\nLado izquierdo sin repetir (símbolos no terminales):");
        a.noTerminales.visualiza();
        System.out.println("\nLado derecho:");
        a.ladoDerecho.visualiza();
        System.out.println("\nLado derecho sin repetir (símbolos terminales):");
        a.terminales.visualiza();
        System.out.println("\nSímbolo inicial: " + a.simboloInicial);
    }
    
    public static void main(String[] args) {
        GramaticaTest test = new GramaticaTest();
    }
}
