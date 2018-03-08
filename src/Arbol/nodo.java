package Arbol;

import java.util.LinkedList;

/**
 *
 * @author Jherson
 */
public class nodo {
    public String nombre;
    public String valor;
    public int linea;
    public int columna;
    public LinkedList<nodo>hijos;

    public nodo() {
    }

    public nodo(String nombre, int linea, int columna) {
        this.nombre = nombre;
        this.linea = linea;
        this.columna = columna;
        this.hijos = new LinkedList<>();
    }

    public nodo(String nombre, String valor, int linea, int columna) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.hijos = new LinkedList<>();
    }
    
    //constructor para guardar llamadas a metodos
    public nodo(String nombre, String valor, nodo hijo ,int linea, int columna) {
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        this.hijos = new LinkedList<>();
        this.hijos.add(hijo);
    }
    
    
    
    public nodo get(int val){
        return this.hijos.get(val);
    }
    
    
    public nodo(String nombre, nodo hijo){
        this.nombre=nombre;
        this.linea=hijo.linea;
        this.columna=hijo.columna;
        this.hijos=new LinkedList<>();
        this.linea=this.columna=0;
        this.valor="";
        this.hijos.add(hijo);
    }

  
    public void add(nodo hijo){
        this.hijos.addLast(hijo);
    }
    
    public nodo(String nombre,nodo val1, nodo val2){//constructor para expresiones
        this.nombre = nombre;
        this.valor =  nombre;
        this.linea = val1.linea;
        this.columna = val2.linea;
        this.hijos = new LinkedList<>();
        
        //add(new nodo(nombre,linea,columna));
        add(val1);
        add(val2);
    }
    
    
}
