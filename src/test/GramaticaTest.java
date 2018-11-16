package test;

import gramatica.gramatica.FiltrarSimbolos;

public class GramaticaTest {
    public GramaticaTest() {
        FiltrarSimbolos a = new FiltrarSimbolos();
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
