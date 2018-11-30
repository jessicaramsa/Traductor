package analizador.sintactico;

import analizador.lexico.Lexico;
import archivo.Cadena;
import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import estructuras.piladinamica.Pila;
import gramatica.FiltrarSimbolos;
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
    Lista producciones = new Lista();
    Lista ladoDerecho = new Lista();
    Lista noTerminales = new Lista();
    Lista terminales = new Lista();
    String simboloInicial = "";
    Lista simbolosPrograma = new Lista();
    FiltrarSimbolos fs = new FiltrarSimbolos();
    Cadena cad = new Cadena();
    
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
                if(!programa.esVacia()) lldriver(programa, simboloInicial);
            } else {
                System.out.println("El archivo " + filePrograma.getName() + "no existe.");
            }
        } else {
            System.out.println("El archivo " + fileGramatica.getName() + "no existe.");
        }
    }

    public void lldriver(Lista programa, String s) {
        String objX, objA;
        int x = 0, a = 0, simboloActual = 0;
        
        automataPila.insertar(s);
        objX = automataPila.obtCima().toString();
        x = noTerminales.localiza(objX);
        dividirSimbolosProgram(programa);
        objA = obtenerTokenEntrada(programa, simboloActual);
        a = simbolosPrograma.localiza(objA);
        
        while (!automataPila.esVacia()) {
            if (fs.esNoTerminal(objX, terminales)) {
                if (matrizPredictiva[x][a] != 0) {
                    int index = matrizPredictiva[x][a];
                    String linea = ladoDerecho.localiza(index).toString();
                    automataPila.eliminarCima();
                    automataPila.cicloPush(linea);
                } else erroresSintacticos.insertarF(x);
            } else {
                if (x == a) {
                    automataPila.eliminarCima();
                    //regresame el siguiente token, analizador lexico
                    simboloActual++;
                    objA = obtenerTokenEntrada(programa, simboloActual);
                    a = simbolosPrograma.localiza(objA);
                } else erroresSintacticos.insertarF(x);
            }
        }
    }
    
    public void dividirSimbolosProgram(Lista programa) {
        for (int i = 0; i < programa.longitud(); i++) {
            Lista linea = cad.dividirCadena((String) programa.localiza(i));
            for (int j = 0; j < linea.longitud(); j++) {
                simbolosPrograma.insertarF(linea.localiza(j));
            }
        }
    }
    
    public String obtenerTokenEntrada(Lista programa, int index) {
        Lexico l = new Lexico();
        String simbolo = simbolosPrograma.localiza(index).toString();
        return simbolo;
    }
}
