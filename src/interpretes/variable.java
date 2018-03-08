package interpretes;

/**
 *
 * @author Jherson
 */
public class variable {
    public String tipo;
    public String nombre;
    public Object valor;

    public variable() {
    }

    public variable(String tipo, String nombre, Object valor) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.valor = valor;
    }
    
    public variable(String nombre){
        this.tipo = "undefined";
        this.nombre = nombre;
        this.valor = null;
    }
}
