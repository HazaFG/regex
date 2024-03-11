public class Token {
    private int id;
    private int noLinea;
    private String nombre;
    private int valor;
    private int tipo;

    public Token (int id, int noLinea, String nombre, int tipo, int valor){
        this.id = id;
        this.noLinea = noLinea;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public int getNoLinea() {
        return noLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }
}
