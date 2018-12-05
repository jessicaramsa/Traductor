package gramatica;

import analizador.lexico.Lexico;
import archivo.Cadena;
import estructuras.lista.Lista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FiltrarSimbolos {
    private Lista producciones = new Lista();
    private Lista noTerminales = new Lista();
    private Lista ladoDerecho = new Lista();
    private Lista terminales = new Lista();
    private Lista reservadas = new Lista();
    private String simboloInicial = null;
    private File file;
    Cadena cad = new Cadena();
    
    private Lista tempTerminales = new Lista();
    private Lista tempNoTerminales = new Lista();
    
    public FiltrarSimbolos() {}
    
    public FiltrarSimbolos(File f) {
        file = f;
        if (file.exists()) {
            if (leer(file)) {
                clasificarNoTerminales(tempNoTerminales);
                buscarInicial(ladoDerecho, noTerminales, tempNoTerminales);
                clasificarTerminales(tempTerminales, noTerminales);
                clasificarReservadas(terminales);
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
                    producciones.insertarF(l);
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
        Lista produccion = cad.dividirCadena(linea);
        
        String derecha = "";
        for (int i = 0; i < produccion.longitud(); i++) {
            if (i > 0) {
                derecha += produccion.localiza(i) + " ";
                Lista izquierda = cad.dividirCadena((String) produccion.localiza(i));
                
                for (int j = 0; j < izquierda.longitud(); j++) {
                    tempTerminales.insertarF(izquierda.localiza(j));
                }
            } else
                tempNoTerminales.insertarF(produccion.localiza(i).toString().trim());
        }
        derecha = derecha.trim();
        ladoDerecho.insertarF(derecha);
    }
    
    /*
        Buscar el inicial comprobando estos criterios:
        1. No debe encontrarse del lado derecho de las producciones
        2. No debe ser un símbolo repetido del lado izquierdo de las producciones
        3. De no cumplir con ninguna de los dos criterios anteriores:
            a. Se tomará el primer símbolo del lado izquierdo de la primera
                producción encontrada dentro de la gramática
    */
    public void buscarInicial(Lista producciones, Lista noTerminales, Lista noTR) {
        int[] repetido = estaRepetido(tempNoTerminales, noTerminales).clone();
        boolean[] derecha = estaDerecha(producciones, noTerminales).clone();
        boolean encontrado = false;
        int index = 0;
        
        while (index < noTerminales.longitud() && !encontrado) {
            if (repetido[index] == 1 && !derecha[index]) encontrado = true;
            else index++;
        }
        
        if (encontrado) simboloInicial = (String) noTerminales.localiza(index);
        else simboloInicial = noTerminales.localiza(0).toString();
    }
    
    /*  Comprueba que cada símbolo de los no terminales, no se encuentre en el
        lado derecho de las producciones. */
    public boolean[] estaDerecha(Lista producciones, Lista noTerminales) {
        boolean[] derecha = new boolean[noTerminales.longitud()];

        for (int i = 0; i < noTerminales.longitud(); i++) {
            boolean ladoDerecho = false;
            int indexProd = 0;
            
            while (!ladoDerecho && indexProd < producciones.longitud()) {
                String linea = producciones.localiza(indexProd).toString();
                Lista s = cad.dividirCadena(linea);
                int indexS = 0;
                
                while (!ladoDerecho && indexS < s.longitud()) {
                    ladoDerecho = s.localiza(indexS).equals(noTerminales.localiza(i));
                    indexS++;
                }
                indexProd++;
            }
            derecha[i] = ladoDerecho;
        }
        return derecha;
    }
    
    /*  Comprueba que cada símbolo de los no terminales no se encuentre repetido
        del lado izquierdo de las producciones. */
    public int[] estaRepetido(Lista tempNoTerminales, Lista noTerminales) {
        int[] repetido = new int[noTerminales.longitud()];

        for (int i = 0; i < noTerminales.longitud(); i++) {
            int izquierda = 0;
            
            for (int j = 0; j < tempNoTerminales.longitud(); j++) {
                String linea = tempNoTerminales.localiza(j).toString();
                Lista s = cad.dividirCadena(linea);
                for (int k = 0; k < 10; k++) {
                    if (s.localiza(k).equals(noTerminales.localiza(i)))
                        izquierda++;
                }
            }
            repetido[i] = izquierda;
        }
        return repetido;
    }
    
    /* Se comprueba si el elemento a insertar ya había sido insertado. */
    public void quitarRepetidos(Lista estructura, Object elemento) {
        if (!estructura.estaElemento(elemento)) {
            estructura.insertarF(elemento);
        }
    }
    
    /* Comprueba que el simbolo objT sea o no un simbolo terminal. */
    public boolean esNoTerminal(String objT) {
        int indexNT = 0;
        String objNT = noTerminales.localiza(indexNT).toString();
        boolean noTerminal = objNT.equals(objT);
        
        while (indexNT < noTerminales.longitud() && !noTerminal) {
            objNT = noTerminales.localiza(indexNT).toString();
            noTerminal = objT.equals(objNT);
            indexNT++;
        }
        return noTerminal;
    }
    
    /* Sobrecarga del método que comprueba si un simbolo es No Terminal */
    public boolean esNoTerminal(String objT, Lista estructura) {
        int indexNT = 0;
        String objNT = estructura.localiza(indexNT).toString();
        boolean noTerminal = objNT.equals(objT);
        
        while (indexNT < estructura.longitud() && !noTerminal) {
            objNT = estructura.localiza(indexNT).toString();
            noTerminal = objT.equals(objNT);
            indexNT++;
        }
        return noTerminal;
    }
    
    /* Comprueba que el obj sea una palabra reservada */
    public boolean esReservada(String obj) {
        int indexR = 0;
        String objR = reservadas.localiza(indexR).toString();
        boolean reservada = false;
        
        while (indexR < reservadas.longitud() && !reservada) {
            objR = reservadas.localiza(indexR).toString();
            reservada = objR.equals(obj);
            indexR++;
        }
        
        return reservada;
    }
    
    /* Se quitan los repetidos de la estructura de símbolos no terminales. */
    public void clasificarNoTerminales(Lista nT) {
        for (int i = 0; i < nT.longitud(); i++) {
            quitarRepetidos(noTerminales, nT.localiza(i));
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
    public void clasificarTerminales(Lista t, Lista nT) {
        /* comprobar que no hay simbolos repetidos */
        Lista nuevosT = new Lista();
        Lista nuevosNT = new Lista();
        for (int i = 0; i < t.longitud(); i++) {
            quitarRepetidos(nuevosT, t.localiza(i));
        }
        for (int j = 0; j < nT.longitud(); j++) {
            quitarRepetidos(nuevosNT, nT.localiza(j));
        }
        
        /*  recorrer cada simbolo del lado derecho y comparar si son o no
            simbolos no terminales. */
        for (int indexT = 0; indexT < nuevosT.longitud(); indexT++) {
            String objT = nuevosT.localiza(indexT).toString();
            if (!esNoTerminal(objT, nuevosNT)) quitarRepetidos(terminales, objT);
        }
    }
    
    /* Clasifica los símbolos terminales y los clasifica a palabras reservadas */
    public void clasificarReservadas(Lista t) {
        int categoria = 0;
        Lexico l = new Lexico(t, this);
        for (int i = 0; i < t.longitud(); i++) {
            categoria = l.scanner().getCategoria();
            System.out.println(categoria);
            if (categoria == 203) reservadas.insertarF(t.localiza(i));
        }
    }
    
    /* Devuelve la posición del símbolo si se encuentra dentro de la estructura */
    public int localizaSimbolo(String[] estructura, String obj) {
        int index = 0;
        for (int i = 0; i < estructura.length; i++) {
            if (estructura[i].equals(obj)) index = i;
        }
        return index;
    }
    
    /* Devuelve el objeto que se encuentra en la posición indicada */
    public String localizaSimbolo(String[] estructura, int index) {
        String obj = null;
        if (index <= estructura.length) {
            for (int i = 0; i < estructura.length; i++) {
                if (i == index) obj = estructura[i];
            }
        }
        return obj;
    }
    
    public String[] getProducciones() { return this.producciones.toArreglo(); }
    public String[] getNoTerminales() { return this.noTerminales.toArreglo(); }
    public String[] getTerminales() { return this.terminales.toArreglo(); }
    public String[] getLadoDerecho() { return this.ladoDerecho.toArreglo(); }
    public String[] getReservadas() { return this.reservadas.toArreglo(); }
    public String getInicial() { return this.simboloInicial; }
}
