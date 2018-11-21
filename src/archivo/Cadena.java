package archivo;

public class Cadena {
    public char[] obtenerCaracteres(String cadena) {
        char[] array = new char[cadena.length()];
        for (int i = 0; i < cadena.length(); i++) {
            array[i] = cadena.substring(i, i + 1).charAt(0);
        }
        return array;
    }
    
    public char[] obtenerCharTrim(String cadena) {
        int blancos = 0;
        char[] array = null;
        
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.substring(i, i + 1).equals(" ")) blancos++;
        }
        
        if (blancos == 0) {
            int index = 0;
            array = new char[cadena.length() - blancos];
            for (int i = 0; i < cadena.length(); i++) {
                if (cadena.substring(i, i + 1).equals(" ")) {
                    array[index] = cadena.substring(i, i + 1).charAt(0);
                    index++;
                }
            }
        }
        return array;
    }

    public String[] dividir(String cadena, String separador) {
        String[] array = new String[cadena.length()];
        for (int i = 0; i < cadena.length(); i++) {
            if (!cadena.substring(i, i + 1).equals(separador)) {
                array[i] = cadena.substring(i, i + 1);
            }
        }
        return array;
    }

    public String unirCaracteres(char[] caracteres) {
        String nuevaCadena = "";
        for (int i = 0; i < caracteres.length; i++) nuevaCadena += caracteres[i];
        return nuevaCadena;
    }
    
    public String obtenerPosicion(String cadena, int inicio, int fin) {
        String nuevaCadena = "";
        for (int i = 0; i < 10; i++) {
            nuevaCadena += cadena.substring(inicio, fin);
        }
        return nuevaCadena;
    }
}
