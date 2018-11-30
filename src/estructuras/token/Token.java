package estructuras.token;

public class Token {
    private String simbolo;
    private int clasificacion;
    
    public Token(String simbolo, int clasificacion) {
        this.simbolo = simbolo;
        this.clasificacion = clasificacion;
    }
    
    public void setSimbolo(String s) { this.simbolo = s; }
    public String getSimbolo() { return this.simbolo; }
    public void setClasificacion(int c) { this.clasificacion = c; }
    public int getClasificacion() { return this.clasificacion; }
}
