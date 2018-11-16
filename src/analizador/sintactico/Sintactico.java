package analizador.sintactico;

import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import estructuras.piladinamica.Pila;
import gramatica.gramatica.FiltrarSimbolos;
import java.io.File;

public class Sintactico {
    Pila automataPila = new Pila();
    int[][] matrizPredictiva = {{1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,2,0,0,2,0,0,2,0,0,0,0,0},
                                {0,4,3,0,0,3,0,0,3,0,0,0,0,0},
                                {0,0,5,0,0,6,0,0,7,0,0,0,0,0},
                                {0,0,8,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,10,0,9,0,0,0,0},
                                {0,0,11,0,0,0,11,0,0,0,11,0,0,0},
                                {0,0,0,0,0,0,0,13,0,12,0,0,0,0},
                                {0,0,14,0,0,0,14,0,0,0,14,0,0,0},
                                {0,0,0,0,16,0,0,16,0,16,0,16,0,15,15,0},
                                {0,0,18,0,0,0,17,0,0,0,19,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,20,21,0},
                                {22,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    Lista erroresSintacticos = new Lista();
    Pila producciones = new Pila();
    Pila ladoDerecho = new Pila();
    Pila noTerminales = new Pila();
    Pila terminales = new Pila();
    String simboloInicial = "";
    
    public Sintactico() {
        System.out.println("> Selecciona el archivo de la gramática");
        ManejadorArchivos ma = new ManejadorArchivos();
        File fileGramatica = ma.abrir();
        if (fileGramatica.exists()) {
            FiltrarSimbolos gramatica = new FiltrarSimbolos(fileGramatica);
            producciones = gramatica.producciones;
            ladoDerecho = gramatica.ladoDerecho;
            noTerminales = gramatica.noTerminales;
            terminales = gramatica.terminales;
            simboloInicial = gramatica.simboloInicial;
            
            System.out.println("> Selecciona el archivo del programa");
            File filePrograma = ma.abrir();
            if (filePrograma.exists()) {
                Lista programa = ma.leer(filePrograma);
                if(!programa.esVacia()) leerPrograma(programa, simboloInicial);
            } else {
                System.out.println("El archivo " + filePrograma.getName() + "no existe.");
            }
        } else {
            System.out.println("El archivo " + fileGramatica.getName() + "no existe.");
        }
    }

    public void leerPrograma(Lista programa, String s) {
        automataPila.insertar(s);
        String objX, objA;
        int x = 0;
        int a = 0;
        
        automataPila.insertar(s);
        objX = automataPila.obtCima().toString();
        x = noTerminales.buscarElementoPosicion(objX);
        objA = obtenerTokenEntrada(programa, 0);
        a = terminales.buscarElementoPosicion(objA);
        while (!automataPila.esVacia()) {
            if (true) {
                if (matrizPredictiva[x][a] != 0) {
                    int index = matrizPredictiva[x][a];
                    String linea = ladoDerecho.buscarPosicion(index).toString();
                    automataPila.eliminarCima();
                    cicloPush(linea);
                } else {
                    erroresSintacticos.insertarF(x);
                }
            } else {
                if (x == a) {
                    automataPila.eliminarCima();
                    //regresame el siguiente token analizador lexico
                    objA = obtenerTokenEntrada(programa, 0);
                    a = terminales.buscarElementoPosicion(objA);
                } else {
                    erroresSintacticos.insertarF(x);
                }
            }
        }
    }
    
    public String obtenerTokenEntrada(Lista programa, int index) {
        return "";
    }
    
    public void leerLinea(String linea) {
        String[] simbolos = linea.split(" ");
        for (int i = 0; i < simbolos.length; i++) {
            //
        }
    }
    
    /*  Inserta los simbolos que encuentre en una línea de programa:
            1.Ingresa el último simbolo encontrado en la línea del programa
            2.El símbolo que queda en la cima de la pila será el que se
                encuentre más a la izquierda
    */
    public void cicloPush(String linea) {
        String[] simbolos = linea.split(" ");
        for (int i = simbolos.length; i > 0; i--) {
            automataPila.insertar(simbolos[i]);
        }
    }
}
