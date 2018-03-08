package interpretes;

import Arbol.nodo;
import java.util.LinkedList;

/**
 *
 * @author Jherson
 */
public class metodo {
    public String nombre;
    LinkedList<String> parametros;//probar sino pongo variables
    nodo sentencias;

    public metodo() {
    }

    public metodo(String nombre,nodo sent) {
        this.nombre = nombre;
        this.parametros = new LinkedList<>();
        this.sentencias=sent;
    }
    
    
    
}
