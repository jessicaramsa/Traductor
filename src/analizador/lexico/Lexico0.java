package analizador.lexico;

import archivo.ManejadorArchivos;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Lexico0 {
    Archivo f = new Archivo();
    ArrayList<String> palabras = new ArrayList();
    ArrayList token = new ArrayList();
    ArrayList errores = new ArrayList();
    String linea = "";

    public Lexico0() {
        ManejadorArchivos ma = new ManejadorArchivos();
        File file = ma.abrir();
        if (file.exists()) {
            String entradas = f.abrirLeerArch();
            String entradasDiv[] = entradas.split(" ");

            palabras.addAll(Arrays.asList(entradasDiv));

            for (int i = 0; i < palabras.size(); i++) {
                recorrerPalabra(palabras.get(i));
            }
            imprimir();
        }
    }
    
    public Lexico0(String linea) {
        palabras.add(linea);

        for (int i = 0; i < palabras.size(); i++) {
            recorrerPalabra(palabras.get(i));
        }
        imprimir();
    }

    /* Recorre cada caracter que compone la entrada */
    public void recorrerPalabra(String palabra) {
        char caracteres[] = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
            caracteres[i] = palabra.charAt(i);
        }
        filtrar(caracteres);
    }

    /*  Filtra la primera posicion de la entrada:
        1 - si es numerico
            - si es 0, se genera directamente el token
            - de lo contrario sigue validando
        2 - si es letra
        3 - si es caracter especial
        Si no cumple con ninguna de las anteriores: caracter no valido,
            se registra el error
    */
    public void filtrar(char[] c) {
        if (c.length > 0) {
            if (esCero(c[0])) {
                valida(c, filtrarTipo(c[0]));
            } else if(esNumerico(c[0])) {
                linea += c[0];
                valida(c, filtrarTipo(c[0]));
            } else if (esLetra(c[0])) {
                linea += c[0];
                valida(c, filtrarTipo(c[0]));
            } else if (esEspecial(c[0])) {
                linea += c[0];
                valida(c, filtrarTipo(c[0]));
            } else {
                generaError(Character.toString(c[0]));
            }
        }
    }
    
    /*  Valida que los caracteres de la entrada sean del mismo tipo,
        en cuanto detecta que no son del mismo tipo, genera el Token para los
        caracteres que anteriormente ya se habian leido y actualiza el arreglo
        de caracteres a leer a los que no han sido leido (eliminado las
        posiciones ya leidas).
        Una vez actualizado el arreglo, se regresa el resto del arreglo al
        método de filtrar el tipo de entrada (numerico, letra o especial)
    */
    public void valida(char[] c, int clasf) {
        boolean tipo = true;
        int index = 1;
        if (index < c.length) linea += c[index];
        while (tipo && index < c.length) {
            tipo = igualTipo(c[index], clasf);
            linea += c[index];
            index++;
        }
        String p = "";
        for (int i = 0; i < c.length; i++) {
            p += c[i];
        }
        generaToken(p, clasf);
        linea = "";
    }
    
    /* Filtra el tipo de caracter: numerico, letra, carater especial */
    public int filtrarTipo(char c) {
        int clasf = 0;
        if (esNumerico(c)) clasf = 1;
        else if (esLetra(c)) clasf = 2;
        else if (esNumerico(c) || esLetra(c)) clasf = 3;
        else if (esEspecial(c)) clasf = 4;
        else clasf = 5;
        return clasf;
    }
    
    /* Compara si el caracter es del mismo tipo */
    public boolean igualTipo(char c, int clasf) {
        boolean tipo = true;
        switch (clasf) {
            case 1: tipo = esNumerico(c); break; // numerico
            case 2: tipo = esLetra(c); break; // alfabetico
            case 3: tipo = esNumerico(c) || esLetra(c); break; // alfaNumerico
            case 4: tipo = esEspecial(c); break; // caracter especial
            default: tipo = false; break;
        }
        return tipo;
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
    public void generaToken(String tk, int clasf) {
        switch (clasf) {
            case 1: case 2: case 3: token.add(tk); break;
            case 4:
                if (tk.length() > 2) {
                    errores.add(tk);
                } else {
                    token.add(tk);
                }
                break; // especial
            default: errores.add(tk); break; // especial
        }
    }
    
    /* Genera el error de caracteres no validos */
    public void generaError(String e) {
        errores.add(e);
    }
    
    /* Imprime los token y errores generados */
    private void imprimir() {
        System.out.println("Palabras de entrada");
        for (int i = 0; i < palabras.size(); i++) {
            System.out.println("Palabra " + (i + 1) + ": " + palabras.get(i));
        }
        System.out.println();
        System.out.println("Tokens");
        for (int i = 0; i < token.size(); i++) {
            System.out.println(token.get(i));
        }
        
        System.out.println("\nErrores");
        if (errores.isEmpty()) {
            System.out.println("Programa aceptado");
        } else {
            for (int i = 0; i < errores.size(); i++) {
                System.out.println(errores.get(i));
            }
        }
    }
}
