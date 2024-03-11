public class Token {
    private String nombre;
    private int valor;
    private int tipo;

    public Token (String nombre, int tipo, int valor){
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValor() {
        return valor;
    }

    public int getTipo() {
        return tipo;
    }
}
