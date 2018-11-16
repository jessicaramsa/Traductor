package gramatica.gramatica;

import estructuras.piladinamica.Pila;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FiltrarSimbolos {
    public Pila producciones = new Pila();
    public Pila noTerminales = new Pila();
    public Pila ladoDerecho = new Pila();
    public Pila terminales = new Pila();
    public String simboloInicial = null;
    private File file;
    
    private Pila tempTerminales = new Pila();
    private Pila tempNoTerminales = new Pila();
    
    public FiltrarSimbolos() {}
    
    public FiltrarSimbolos(File f) {
        file = f;
        if (file.exists()) {
            if (leer(file)) {
                clasificarNoTerminales(tempNoTerminales);
                buscarInicial(ladoDerecho, tempNoTerminales);
                clasificarTerminales(tempTerminales, noTerminales);
            } else {
                System.out.println("El archivo está vacío.");
            }
        } else System.out.println("El archivo " + file.getName() + "no existe.");
    }
    
    /* Lee el archivo seleccionado donde se encuentra la gramática. */
    public boolean leer(File arch) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(arch);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String l = br.readLine();
            if (l != null) {
                while (l != null) {
                    producciones.insertar(l);
                    dividirProduccion(l);
                    l = br.readLine();
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error al leer. " + e);
            return false;
        } finally {
            try {
                is.close();
                isr.close();
                br.close();
            } catch (IOException e) { System.out.println("Error cerrar. " + e); }
        }
    }
    
    /*
        linea - linea completa de producción
        1. Se divide la producción de la gramática por espacios entre cada símbolo
        2. El primer símbolo de la división se agregará al lado izquierdo
        3. El resto de símbolos pasarán a dos estructuras:
            a. tempTerminales - donde se guardarán los símbolos del lado
                izquierdo sin clasificar
            b. ladoDerecho - esta estructura guarda el lado derecho de cada
                producción
    */
    public void dividirProduccion(String linea) {
        String[] produccion = linea.split(" ");
        String derecha = "";
        for (int i = 0; i < produccion.length; i++) {
            if (i > 0) {
                derecha += produccion[i] + " ";
                String[] izquierda = produccion[i].split(" ");
                for (String izquierda1 : izquierda) {
                    tempTerminales.insertar(izquierda1);
                }
            } else tempNoTerminales.insertar(produccion[i].trim());
        }
        derecha = derecha.trim();
        ladoDerecho.insertar(derecha);
    }
    
    /*
        Buscar el inicial comprobando estos criterios:
        1. No debe encontrarse del lado derecho de las producciones
        2. No debe ser un símbolo repetido del lado izquierdo de las producciones
        3. De no cumplir con ninguna de los dos criterios anteriores:
            a. Se tomará el primer símbolo del lado izquierdo de la primera
                producción encontrada dentro de la gramática
    */
    public void buscarInicial(Pila derecha, Pila noTerminales) {
        int indexNT = 0;
        String simbolo = noTerminales.buscarPosicion(indexNT).toString();
        boolean esDerecha = false;
        boolean esRepetido = false;
        
        while (indexNT < noTerminales.longitud() && esDerecha) {
            simbolo = noTerminales.buscarPosicion(indexNT).toString();
            esDerecha = estaDerecha(simbolo, derecha, noTerminales);
            indexNT++;
        }
        indexNT = 0;
        while (indexNT < noTerminales.longitud() && esRepetido) {
            simbolo = noTerminales.buscarPosicion(indexNT).toString();
            esRepetido = esRepetido(simbolo, noTerminales);
            indexNT++;
        }
        
        if (!esDerecha && !esRepetido) {
            simboloInicial = simbolo;
        } else simboloInicial = noTerminales.buscarPosicion(0).toString();
    }
    
    /*  Comprueba que el simbolo t de los no terminales, no se encuentre en el
        laado derecho de las producciones. */
    public boolean estaDerecha(String nt, Pila derecha, Pila noTerminales) {
        boolean esDerecha = false;
        int indexD = 0;
        while (indexD < derecha.longitud() && !esDerecha) {
            String linea = derecha.buscarPosicion(indexD).toString();
            String[] simbolos = linea.split(" ");
            int s = 0;
            while (!esDerecha && s < simbolos.length) {
                esDerecha = nt.equals(simbolos[s]);
                s++;
            }
            indexD++;
        }
        return esDerecha;
    }
    
    /*  Comprueba que el simbolo t de los no terminales no se encuentre repetido
        del lado izquierdo de las producciones. */
    public boolean esRepetido(String nt, Pila noTerminales) {
        boolean esRepetido = false;
        int indexNT = 0, repeticiones = 0;
        while (indexNT < noTerminales.longitud() && esRepetido
                                && (repeticiones > 1 || repeticiones == 0)) {
            esRepetido = noTerminales.buscarElemento(nt);
            if (esRepetido) repeticiones++;
            indexNT++;
        }
        return esRepetido;
    }
    
    /* Se comprueba si el elemento a insertar ya había sido insertado. */
    public void quitarRepetidos(Pila estructura, Object elemento) {
        if (!estructura.buscarElemento(elemento)) {
            estructura.insertar(elemento);
        }
    }
    
    /* Comprueba que el simbolo objT sea o no un simbolo terminal. */
    public boolean esNoTerminal(String objT, Pila nT) {
        int indexNT = 0;
        String objNT = nT.buscarPosicion(indexNT).toString();
        boolean noTerminal = objNT.equals(objT);
        
        while (indexNT < nT.longitud() && !noTerminal) {
            objNT = nT.buscarPosicion(indexNT).toString();
            noTerminal = objT.equals(objNT);
            indexNT++;
        }
        return noTerminal;
    }
    
    /* Se quitan los repetidos de la estructura de símbolos no terminales. */
    public void clasificarNoTerminales(Pila nT) {
        for (int i = 0; i < nT.longitud(); i++) {
            quitarRepetidos(noTerminales, nT.buscarPosicion(i));
        }
    }
    
    /*
        t - estructura de simbolos terminales sin clasificar
        nT - estructura de simbolos no terminales
        1. Se comprueba que no haya símbolos repetidos dentro de cada estructura
        2. Se empieza la clasificación para que el resultado de la estructura
            sean únicamente símbolos terminales sin repetir.
            a. Se recorre la estructura de simbolos terminales que se obtuvo de
                dividir palabra por palabra cada línea del lado derecho de la
                gramática leída.
            b. Se recorren los símbolos no terminales para comparar que del lado
                derecho no se encuentren símbolos no terminales.
            c. Al no pertenecer al conjunto de símbolos no terminales, dicho
                símbolo se insertará en la estructura de símbolos terminales.
    */
    public void clasificarTerminales(Pila t, Pila nT) {
        /* comprobar que no hay simbolos repetidos */
        Pila nuevosT = new Pila();
        Pila nuevosNT = new Pila();
        for (int i = 0; i < t.longitud(); i++) {
            quitarRepetidos(nuevosT, t.buscarPosicion(i));
        }
        for (int j = 0; j < nT.longitud(); j++) {
            quitarRepetidos(nuevosNT, nT.buscarPosicion(j));
        }
        
        /*  recorrer cada simbolo del lado derecho y comparar si son o no
            simbolos no terminales. */
        for (int indexT = 0; indexT < nuevosT.longitud(); indexT++) {
            String objT = nuevosT.buscarPosicion(indexT).toString();
            if (!esNoTerminal(objT, nuevosNT)) quitarRepetidos(terminales, objT);
        }
    }
}
