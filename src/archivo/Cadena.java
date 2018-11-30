package archivo;

import estructuras.lista.Lista;

public class Cadena {
    public Lista divideCadena(String cadena) {
        char[] caracteres = cadena.toCharArray();
        Lista palabras = new Lista();
        String palabra = "";
        boolean blanco = false;
        
        for (int i = 0; i < caracteres.length; i++) {
            if (caracteres[i] == ' ') {
                palabras.insertarF(palabra);
                palabra = "";
                blanco = true;
            } else {
                palabra += caracteres[i];
                blanco = false;
            }
        }
        if (!blanco) palabras.insertarF(palabra);
        return palabras;
    }
}
