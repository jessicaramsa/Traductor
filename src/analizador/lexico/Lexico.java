package analizador.lexico;

import archivo.Cadena;
import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import estructuras.token.Token;
import java.io.File;

public class Lexico {
    int estado = 0, clasificacion = 0;
    Lista<Token> token = new Lista();
    int nextToken = 0;
    Lista programaALeer;
    Cadena c = new Cadena();
    /*  Clasificaciones
        200 - Error
        201 - Palabra reservada
        202 - Identificador
        203 - IntLiteral
        204 - Caracter especial
    */

    public Lexico() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrirGrafico();
        if (file.exists()) {
            programaALeer = ma.leer(file);
            if (!programaALeer.esVacia()) {
                estado = 0;
                filtrarEntrada();
                System.out.println("> Entradas");
                programaALeer.visualiza();
                System.out.println("> Tokens");
                for (int i = 0; i < token.longitud(); i++) {
                    System.out.println(token.localiza(i).getClasificacion()
                            + " - " + token.localiza(i).getSimbolo());
                }
            }
        }
    }
    
    public Lexico(Lista programa) {
        programaALeer = programa;
        if (!programaALeer.esVacia()) {
            estado = 0;
            filtrarEntrada();
            System.out.println("> Tokens");
            for (int i = 0; i < token.longitud(); i++) {
                System.out.println(token.localiza(i).getClasificacion()
                        + " - " + token.localiza(i).getSimbolo());
            }
        }
    }

    /* Filtrar el caracter que conforma la palabra y sus estados */
    public void filtrarEntrada() {
        for (int iP = 0; iP < programaALeer.longitud(); iP++) {
            String lineaPrograma = (String) programaALeer.localiza(iP);
            char[] caracteres = lineaPrograma.toCharArray();
            char ultimoChar = caracteres[caracteres.length - 1];
            String simbolo = "";
            estado = cambiarEstado(caracteres[0], 0);
            simbolo += caracteres[0];
            
            for (int indLinea = 1; indLinea < caracteres.length; indLinea++) {
                estado = cambiarEstado(caracteres[indLinea], estado);
                
                if (caracteres[indLinea] == ' ' || caracteres[indLinea] == ultimoChar) {
                    if (caracteres[indLinea] == ultimoChar)
                        simbolo += caracteres[indLinea];
                    generarToken(simbolo, estado);
                    simbolo = "";
                    estado = cambiarEstado(caracteres[indLinea], estado);
                } else {
                    if (caracteres[indLinea] == ' ' || caracteres[indLinea] == ultimoChar) {
                        generarToken(simbolo, estado);
                        simbolo = "";
                        estado = cambiarEstado(caracteres[indLinea], estado);
                    } else simbolo += caracteres[indLinea];
                }
            }
            estado = 0;
            simbolo = "";
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

    /* Genera un nuevo Token */
    public void generarToken(String simbolo, int estado) {
        switch (estado) {
            case 1: clasificacion = 203; break;
            case 2: clasificacion = 203; break;
            case 3: clasificacion = 202; break;
            case 4: clasificacion = 204; break;
            default: clasificacion = 200; break;
        }
        token.insertarF(new Token(simbolo, clasificacion));
    }
    
    /* Regresa el siguiente token al analizador sintáctico */
    public Token scanner() {
        nextToken++;
        return token.localiza(nextToken);
    }
}
