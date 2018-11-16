package analizador.lexico;

import java.util.ArrayList;
import java.util.Arrays;

public class Principal {
    Archivo f = new Archivo();
    ArrayList<String> palabras = new ArrayList();
    ArrayList tokenNumerico = new ArrayList();
    ArrayList tokenAlfabetico = new ArrayList();
    ArrayList tokenAlfaNumerico = new ArrayList();
    ArrayList tokenEspecial = new ArrayList();
    ArrayList errores = new ArrayList();
    String linea = "";
    String archSalida = "analizadorLexico.txt";
    char caracterEspecial[] = {42, 43, 45, 47, 61, 33, 34, 35, 36, 37, 38, 39, 40, 41, 44,
        46, 58, 59, 60, 62, 63, 64, 91, 92, 93, 94, 95, 96, 123, 124, 125};

    public Principal() {
        String entradas = f.abrirLeerArch();
        String entradasDiv[] = entradas.split(" ");

        palabras.addAll(Arrays.asList(entradasDiv));
        
        for (int i = 0; i < palabras.size(); i++) {
            recorrerPalabra(palabras.get(i));
        }
        //guardar();
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
                //generaToken(Character.toString(c[0]), 1);
                //valida(actualizarArray(c), filtrarTipo(c[0]));
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
                //filtrar(actualizarArray(c));
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
        if (index < c.length)
            linea += c[index];
        //index++;
        //tipo = igualTipo(c[index], clasf);
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
        //filtrar(actualizarArray(c));
    }
    
    /* Filtra el tipo de caracter: numerico, letra, carater especial */
    public int filtrarTipo(char c) {
        int clasf = 0;
        if (esNumerico(c)) clasf = 1;
        else if (esLetra(c)) clasf = 2;
        else if (esNumerico(c) || esLetra(c)) clasf = 3;
        else if (esEspecial(c)) clasf = 4;
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
    
    /* Actualiza el arreglo de caracteres a validar */
    public char[] actualizarArray(char[] c) {
        if ((c.length - 1) > 0) {
            char[] tempC = new char[c.length - 1];
            if (c.length > 0) {
                for (int i = 1; i < c.length; i++) {
                    tempC[i - 1] = c[i];
                }
            }
            return tempC;
        }
        return c;
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
        int cont = 0;
        for (int i = 0; i < caracterEspecial.length; i++) {
            if (c == caracterEspecial[i]) {
                cont = 1;
            }
        }
        return true;
    }
    
    /* Genera el Token */
    public void generaToken(String tk, int clasf) {
        switch (clasf) {
            case 1: tokenNumerico.add(tk); break; // numerico
            case 2: tokenAlfabetico.add(tk); break; // alfabetico
            case 3: tokenAlfaNumerico.add(tk); break; // alfanNumerico
            case 4:
                if (tk.length() > 2) {
                    errores.add(tk);
                } else {
                    tokenEspecial.add(tk);
                }
                break; // especial
            default: errores.add(tk); break; // especial
        }
    }
    
    /* Genera el error de caracteres no validos */
    public void generaError(String e) {
        errores.add(e);
    }
    
    /* Guarda los Token y errrores generados en un archivo de salida */
    private void guardar() {
        f.guardarArch(archSalida, "Token Numerico");
        for (int i = 0; i < tokenNumerico.size(); i++) {
            f.guardarArch(archSalida, tokenNumerico.get(i).toString());
        }
        f.guardarArch(archSalida, "tokenAlfabetico");
        for (int i = 0; i < tokenAlfabetico.size(); i++) {
            f.guardarArch(archSalida, tokenAlfabetico.get(i).toString());
        }
        f.guardarArch(archSalida, "TokenAlfaNumerico");
        for (int i = 0; i < tokenAlfaNumerico.size(); i++) {
            f.guardarArch(archSalida, tokenAlfaNumerico.get(i).toString());
        }
        f.guardarArch(archSalida, "Token Especial");
        for (int i = 0; i < tokenEspecial.size(); i++) {
            f.guardarArch(archSalida, tokenEspecial.get(i).toString());
        }
        
        f.guardarArch(archSalida, "Errores");
        for (int i = 0; i < errores.size(); i++) {
            f.guardarArch(archSalida, errores.get(i).toString());
        }
    }
    
    /* Imprime los token y errores generados */
    private void imprimir() {
        System.out.println("Palabras de entrada");
        for (int i = 0; i < palabras.size(); i++) {
            System.out.println("Palabra " + (i + 1) + ": " + palabras.get(i));
        }
        System.out.println();
        System.out.println("Token Numerico");
        for (int i = 0; i < tokenNumerico.size(); i++) {
            System.out.println(tokenNumerico.get(i));
        }
        System.out.println("Token Alfabetico");
        for (int i = 0; i < tokenAlfabetico.size(); i++) {
            System.out.println(tokenAlfabetico.get(i));
        }
        System.out.println("Token AlfaNumerico");
        for (int i = 0; i < tokenAlfaNumerico.size(); i++) {
            System.out.println(tokenAlfaNumerico.get(i));
        }
        System.out.println("Token Especial");
        for (int i = 0; i < tokenEspecial.size(); i++) {
            System.out.println(tokenEspecial.get(i));
        }
        
        System.out.println("Errores");
        for (int i = 0; i < errores.size(); i++) {
            System.out.println(errores.get(i));
        }
    }
}
