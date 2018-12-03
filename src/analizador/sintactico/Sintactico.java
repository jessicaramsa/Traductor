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
    
    public Sintactico() {
        System.out.println("> Selecciona el archivo de la gramática");
        ManejadorArchivos ma = new ManejadorArchivos();
        File fileGramatica = ma.abrirGrafico();
        if (fileGramatica.exists()) {
            g = new FiltrarSimbolos(fileGramatica);
            
            System.out.println("> Selecciona el archivo del programa");
            File filePrograma = ma.abrirGrafico();
            if (filePrograma.exists()) {
                Lista programa = ma.leer(filePrograma);
                l = new Lexico(programa);
                if(!programa.esVacia()) lldriver();
            } else {
                System.out.println("El archivo " + filePrograma.getName() + "no existe.");
            }
        } else {
            System.out.println("El archivo " + fileGramatica.getName() + "no existe.");
        }
    }

    public void lldriver() {
        String objX, objA;
        int indX = 0, indA = 0;
        
        automataPila.insertar(g.getInicial());
        objX = (String) automataPila.obtCima();
        objA = l.scanner();
        
        while (!automataPila.esVacia()) {
            if (g.esNoTerminal(objX)) {
                indX = g.localizaSimbolo(g.getNoTerminales(), objX);
                indA = localizaA(objA);
                if (matrizPredictiva[indX][indA] != 0) {
                    // reemplaza x con production[Predict[x,a]]
                    int indexProduccion = matrizPredictiva[indX][indA];
                    String nuevaProduccion = g.getLadoDerecho()[indexProduccion];
                    automataPila.eliminarCima();
                    automataPila.cicloPush(nuevaProduccion);
                } else errorSintactico.insertarF(objX);
            } else {
                if (objX.equals(objA)) {
                    automataPila.eliminarCima();
                    //regresame el siguiente token, analizador lexico
                    objA = l.scanner();
                } else errorSintactico.insertarF(objX);
            }
            objX = (String) automataPila.obtCima();
        }
    }

    public int localizaA(String objA) {
        int indA = 0;
        if (g.esNoTerminal(objA))
            indA = g.localizaSimbolo(g.getNoTerminales(), objA);
        else indA = g.localizaSimbolo(g.getTerminales(), objA);
        return indA;
    }
}
