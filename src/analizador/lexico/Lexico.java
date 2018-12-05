package analizador.lexico;

import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import estructuras.token.Token;
import gramatica.FiltrarSimbolos;
import java.io.File;

public class Lexico {
    int estado = 0, indA = 0, indB = 0, indLinea = 0;
    String linea;
    Lista programaALeer;
    FiltrarSimbolos g;
    /*  Clasificaciones
        200 - Error
        201 - intLiteral
        202 - Palabra reservada
        203 - Identificador
        204 - Caracter especial
    */

    public Lexico() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File programa = ma.abrirGrafico();
        if (programa.exists()) {
            programaALeer = ma.leer(programa);
            if (!programaALeer.esVacia()) {
                estado = 0;
                indA = 0;
                indB = indA;
                indLinea = 0;
            }
        }
    }
    
    public Lexico(String linea, FiltrarSimbolos fs) {
        programaALeer.insertarF(linea);
        g = fs;
        estado = 0;
        indA = 0;
        indB = indA;
        indLinea = 0;
        linea = (String) programaALeer.localiza(indLinea);
    }
    
    public Lexico(Lista programa, FiltrarSimbolos fs) {
        programaALeer = programa;
        g = fs;
        estado = 0;
        indA = 0;
        indB = indA;
        indLinea = 0;
        linea = (String) programaALeer.localiza(indLinea);
    }
    
    /* Cambiar estado de acuerdo al Autómata del Analizador Léxico */
    public Token scanner() {
        int longitud = linea.length();
        if (indA == longitud && !linea.isEmpty()) {
            indA = 0;
            indLinea++;
        }
        linea = (String) programaALeer.localiza(indLinea);
        char[] caracteres = linea.toCharArray();
        char caracter = caracteres[indA];
        indB = indA;
        estado = 0;
        while (indA < caracteres.length) {
            switch(estado) {
                case 0:
                    if (esNumero(caracter)) estado = 1;
                    else if (esCero(caracter)) estado = 2;
                    else if (esLetra(caracter)) estado = 3;
                    else if (caracter == ':') estado = 4;
                    else if (esEspecial(caracter)) estado = 5;
                    else
                        return generarToken(linea.substring(indB, indA), 200);
                    break;
                case 1:
                    if (esNumero(caracter) || esCero(caracter)) estado = 1;
                    else
                        return generarToken(linea.substring(indB, indA), 201);
                    break;
                case 2:
                    if (esNumero(caracter)) estado = 2;
                    else
                        return generarToken(linea.substring(indB, indA), 201);
                    break;
                case 3:
                    if (esLetra(caracter) || esNumero(caracter) || esCero(caracter))
                        estado = 3;
                    else
                        return generarToken(linea.substring(indB, indA), 203);
                    break;
                case 4:
                    if (caracter == '=')
                        return generarToken(linea.substring(indB, indA), 204);
                    else estado = 6;
                    break;
                case 5:
                    if (esEspecial(caracter))
                        return generarToken(linea.substring(indB, indA), 204);
                    else estado = 6;
                    break;
                default:
                    return generarToken(linea.substring(indB, indA), 200);
            }
            indA++;
        }
        return generarToken("", -1);
    }
    
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

    /* Genera un nuevo Token */
    public Token generarToken(String simbolo, int categoria) {
        estado = 0;
        indA--;
        indB = indA;
        String clasf = "";
        switch (categoria) {
            case -1:
                clasf = "findearchivo";
                break;
            case 200:
                clasf = "error";
                break;
            case 201:
                clasf = "intLiteral";
                break;
            case 203:
                if (g.esReservada(simbolo)) clasf = "reservada";
                else clasf = "id";
                break;
            case 204:
                clasf = "especial";
                break;
            default:
                clasf = "error";
                break;
        }
        return new Token(simbolo, clasf, categoria);
    }
}
