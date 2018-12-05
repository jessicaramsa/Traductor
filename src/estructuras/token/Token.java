package estructuras.token;

public class Token {
    private String simbolo;
    private String nombreCategoria;
    private int categoria;
    
    public Token() {}
    
    public Token(String simbolo, String nombreCategoria, int categoria) {
        this.simbolo = simbolo;
        this.nombreCategoria = nombreCategoria;
        this.categoria = categoria;
    }
    
    public void setSimbolo(String s) { this.simbolo = s; }
    public String getSimbolo() { return this.simbolo; }
    public void setNombreCategoria(String n) { this.nombreCategoria = n; }
    public String getNombreCategoria() { return this.nombreCategoria; }
    public void setCategoria(int c) { this.categoria = c; }
    public int getCategoria() { return this.categoria; }
}
