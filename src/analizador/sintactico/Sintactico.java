package analizador.sintactico;

import analizador.lexico.Lexico;
import archivo.Cadena;
import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import estructuras.piladinamica.Pila;
import estructuras.token.Token;
import gramatica.FiltrarSimbolos;
import java.io.File;

public class Sintactico {
    Pila automataPila = new Pila();
    Lista errorSintactico = new Lista();
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
    FiltrarSimbolos g;
    Lexico l;
    Cadena cad = new Cadena();
    String[] reservadas = {"begin", "end", "write", "read"};
    
    public Sintactico() {
        System.out.println("> Selecciona el archivo de la gramática");
        ManejadorArchivos ma = new ManejadorArchivos();
        File fileGramatica = ma.abrirGrafico();
        if (fileGramatica.exists()) {
            g = new FiltrarSimbolos(fileGramatica, reservadas);
            
            System.out.println("> Selecciona el archivo del programa");
            File filePrograma = ma.abrirGrafico();
            if (filePrograma.exists()) {
                Lista programa = ma.leer(filePrograma);
                l = new Lexico(programa, g);
                if(!programa.esVacia()) lldriver();
            } else {
                System.out.println("El archivo " + filePrograma.getName() + "no existe.");
            }
        } else {
            System.out.println("El archivo " + fileGramatica.getName() + "no existe.");
        }
    }

    public void lldriver() {
        Token objX = new Token();
        Token objA = new Token();
        int indX = 0, indA = 0;
        
        automataPila.insertar(g.getInicial());
        objX.setSimbolo((String) automataPila.obtCima());
        objA = l.scanner();
        
        while (!automataPila.esVacia()) {
            if (g.esNoTerminal(objX.getSimbolo())) {
                indX = g.localizaSimbolo(g.getNoTerminales(), objX.getSimbolo());
                indA = localizaA(objA.getSimbolo());
                if (matrizPredictiva[indX][indA] != 0) {
                    // reemplaza x con production[Predict[x,a]]
                    int indexProduccion = matrizPredictiva[indX][indA] - 1;
                    String nuevaProduccion = g.getLadoDerecho()[indexProduccion];
                    automataPila.eliminarCima();
                    automataPila.cicloPush(nuevaProduccion);
                } else {
                    errorSintactico.insertarF(objX);
                    System.out.println("ERROR - " + objX.getSimbolo());
                    automataPila.eliminarCima();
                }
            } else {
                if (objX.getSimbolo().equals(objA.getSimbolo())) {
                    automataPila.eliminarCima();
                    // regresar el siguiente token
                    objA = l.scanner();
                } else if (objX.getSimbolo().equals("")){
                    automataPila.limpiar();
                }else {
                    errorSintactico.insertarF(objX);
                    System.out.println("ERROR - " + objX.getSimbolo());
                    automataPila.eliminarCima();
                }
            }
            if (!automataPila.esVacia())
                objX.setSimbolo((String) automataPila.obtCima());
        }
        if (errorSintactico.esVacia()) System.out.println("Programa correcto");
    }

    /* Identifica en token de A dentro de las estructuras de la gramática */
    public int localizaA(String objA) {
        int indA = 0;
        if (g.esNoTerminal(objA))
            indA = g.localizaSimbolo(g.getNoTerminales(), objA);
        else indA = g.localizaSimbolo(g.getTerminales(), objA);
        return indA;
    }
}
