package analizador.lexico;

import archivo.Cadena;
import archivo.ManejadorArchivos;
import estructuras.lista.Lista;
import java.io.File;

public class Lexico {
    Lista palabras = new Lista();
    Lista token = new Lista();
    Lista error = new Lista();
    Cadena cad = new Cadena();

    public Lexico() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrir();
        if (file.exists()) {
            palabras = ma.leer(file);

            for (int i = 0; i < palabras.longitud(); i++) {
                filtrar((String) palabras.localiza(i));
            }
            imprimir();
        }
    }

    public Lexico(String linea) {
        palabras.insertarF(linea);

        for (int i = 0; i < palabras.longitud(); i++) {
            filtrar((String) palabras.localiza(i));
        }
        imprimir();
    }

    /* Recorre cada palabra que compone la entrada */
    public void filtrar(String palabra) {
        int estado = 0;
        char caracter[] = cad.obtenerCaracteres(palabra);

        if (esNumerico(caracter[0])) estado = 1;
        else if (esCero(caracter[0])) estado = 2;
        else if (esLetra(caracter[0])) estado = 3;
        else if (esEspecial(caracter[0])) estado = 4;
        else estado = 5;
        
        if (caracter.length > 1) {
            int index = 0;
            while (index < caracter.length && estado != 5) {
                switch(estado) {
                    case 1:
                        estado = 1;
                        if (esNumerico(caracter[index]) || esCero(caracter[index]))
                            estado = 1;
                        else estado = 5;
                        break;
                    case 2:
                        estado = 5;
                        break;
                    case 3:
                        if (esNumerico(caracter[index]) || esCero(caracter[index])
                            || esLetra(caracter[index]))
                            estado = 3;
                        else estado = 5;
                        break;
                    case 4:
                        estado = 5;
                        break;
                    default: estado = 5;
                }
                
                if (esNumerico(caracter[index])) {
                    estado = 1;
                } else if (esCero(caracter[index])) {
                    estado = 2;
                } else if (esLetra(caracter[index])) {
                    estado = 3;
                } else if (esEspecial(caracter[index])) {
                    estado = 4;
                } else estado = 5;
                index++;
            }
        }

        filtrarTipo(estado, palabra);
    }

    public void filtrarTipo(int estado, String cadena) {
        switch(estado) {
            case 0: case 5: generaError(cadena); break;
            default: generaToken(cadena); break;
        }
    }

    /* Comprueba si el caracter a leer es 0 */
    public boolean esCero(char c) {
        return c == '0';
    }
    
    /* Comprueba si el caracter a leer esta dentro del rango 1-9 */
    public boolean esNumerico(char c) {
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
    public void generaToken(String tk) {
        token.insertarF(tk);
    }
    
    /* Genera el error de caracteres no validos */
    public void generaError(String e) {
        error.insertarF(e);
    }

    /* Imprime los token y errores generados */
    private void imprimir() {
        System.out.println("Palabras de entrada");
        palabras.visualiza();
        System.out.println("\nTokens");
        token.visualiza();
        
        System.out.println("\nErrores");
        if (error.esVacia()) {
            System.out.println("0\nPrograma aceptado");
        } else {
            error.visualiza();
        }
    }
}
