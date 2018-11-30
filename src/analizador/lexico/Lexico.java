package analizador.lexico;

import archivo.Cadena;
import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import java.io.File;

public class Lexico {
    Lista palabras = new Lista();
    Lista error = new Lista();
    Cadena cad = new Cadena();
    int index = 0, estado = 0;
    String simbolo = "";

    public Lexico() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrir();
        if (file.exists()) {
            palabras = ma.leer(file);

            for (int i = 0; i < palabras.longitud(); i++) {
                //filtrar((String) palabras.localiza(i));
            }
            imprimir();
        }
    }

    public Lexico(String linea) {
        palabras.insertarF(linea);

        for (int i = 0; i < palabras.longitud(); i++) {
            //filtrar((String) palabras.localiza(i));
        }
        imprimir();
    }

    public void filtrado(String cadena) {
        char[] cadenaCaracteres = cadena.toCharArray();
        index = 0;
        boolean corta = false;
        estado = cambiarEstado(cadenaCaracteres[index], 0);
        simbolo += cadenaCaracteres[index];

        while (index < cadenaCaracteres.length && !corta) {
            estado = cambiarEstado(cadenaCaracteres[index], estado);
            simbolo += cadenaCaracteres[index];
            index++;
            corta = cadenaCaracteres[index] == ' ';
        }
        simbolo = "";
    }

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

    public String analizar(String cadena) {
        filtrado(cadena);
        switch(estado) {
            case 0: case 5: return generaError(simbolo);
            default: return generaToken(simbolo);
        }
    }

    /* Comprueba si el caracter a leer es 0 */
    public boolean esCero(char c) {
        return c == '0';
    }
    
    /* Comprueba si el caracter a leer esta dentro del rango 1-9 */
    public boolean esNumero(char c) {
        return (c >= '1' && c <= '9');
    }

    /* Comprueba si el caracter a leer es letra minúscula o mayúscula */
    public boolean esLetra(char c) {
        return (c >= 'a' && c <= 'z' ) || (c >= 'A' && c <= 'Z' );
    }

    /* Comprueba si el caracter a leer es un caracter especial */
    public boolean esEspecial(char c) {
        return (c == 33) || (c >= 36 && c <= 38) ||
                (c >= 42 && c <= 47) || (c >= 58 && c <= 63) ||
                (c >= 91 && c <= 95) || (c >= 123 && c <= 125);
    }

    /* Genera el Token */
    public String generaToken(String tk) {
        return tk;
    }
    
    /* Genera el error de caracteres no validos */
    public String generaError(String e) {
        error.insertarF(e);
        return e;
    }

    /* Imprime los token y errores generados */
    private void imprimir() {
        System.out.println("\nErrores");
        if (error.esVacia()) {
            System.out.println("\nPrograma aceptado");
        } else {
            error.visualiza();
        }
    }
}
