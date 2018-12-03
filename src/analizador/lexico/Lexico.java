package analizador.lexico;

import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import estructuras.token.Token;
import java.io.File;

public class Lexico {
    String simbolo = "";
    int estado = 0, index = 0, clasificacion = 0;

    public Lexico() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrirGrafico();
        if (file.exists()) {
            Lista entradas = ma.leer(file);

            for (int i = 0; i < entradas.longitud(); i++) {
                filtrar((String) entradas.localiza(i));
            }
        }
    }
    
    public Lexico(File programaALeer) {
        if (programaALeer.exists()) {
            ManejadorArchivos ma = new ManejadorArchivos();
            Lista entrada = ma.leer(programaALeer);
        }
    }

    /* Filtrar el caracter que conforma la palabra y sus estados */
    public void filtrar(String cadena) {
        char[] caracteres = cadena.toCharArray();
        while (index < caracteres.length) {
            estado = cambiarEstado(caracteres[index], 0);
            index++;
        }
        
        switch(estado) {
            //
        }
    }

    /* Cambiar estado de acuerdo al Autómata del Analizador Léxico */
    public int cambiarEstado(char caracter, int estado) {
        switch(estado) {
            case 0:
                if (esNumero(caracter)) estado = 1;
                else if (esCero(caracter)) estado = 2;
                else if (esLetra(caracter)) estado = 3;
                else if (esEspecial(caracter)) estado = 4;
                else estado = 5;
                break;
            case 1:
                if (esNumero(caracter) || esCero(caracter)) estado = 1;
                else estado = 5;
                break;
            case 2:
                if (caracter == ' ') estado = 2;
                else estado = 5;
                break;
            case 3:
                if (esLetra(caracter) || esNumero(caracter) || esCero(caracter))
                    estado = 3;
                else estado = 5;
                break;
            case 4:
                if (esEspecial(caracter)) estado = 4;
                else estado = 5;
                break;
            case 5: estado = 5; break;
            default: estado = 5; break;
        }
        return estado;
    }
    
    /* Comprueba si el caracter a leer es un espacio en blanco */
    public boolean esBlanco(char c) { return c == ' '; }
    /* Comprueba si el caracter a leer es 0 */
    public boolean esCero(char c) { return c == '0'; }
    /* Comprueba si el caracter a leer esta dentro del rango 1-9 */
    public boolean esNumero(char c) { return (c >= '1' && c <= '9'); }

    /* Comprueba si el caracter a leer es letra minúscula o mayúscula */
    public boolean esLetra(char c) {
        return (c >= 'a' && c <= 'z' ) || (c >= 'A' && c <= 'Z' );
    }

    /* Comprueba si el caracter a leer es un caracter especial */
    public boolean esEspecial(char c) {
        return (c == '!') || (c >= '$' && c <= '&') ||
                (c >= '*' && c <= '/') || (c >= ':' && c <= '?') ||
                (c >= '[' && c <= '_') || (c >= '{' && c <= '}');
    }

    /* Regresa el siguiente token al analizador sintáctico */
    public String scanner() {
        String siguienteSimbolo = "";
        //
        return siguienteSimbolo;
    }
}
