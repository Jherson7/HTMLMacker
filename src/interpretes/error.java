package interpretes;

public class error {
    public String tipo;
    public String men;
    public int linea;
    public int col;

    public error() {
    }

    public error(String tipo, String men, int linea, int col) {
        this.tipo = tipo;
        this.men = men;
        this.linea = linea;
        this.col = col;
    }
    
    
}
