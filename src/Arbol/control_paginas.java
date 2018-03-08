/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import paginas.ScrollPane;
import paginas.boton;
import paginas.enlace;
import paginas.modeloTablaErrores;
import vistas.Html;

/**
 *
 * @author Jherson
 */
public class control_paginas {
    private static ScrollPane actual;
    
    private static HashMap<String,String> historial = new HashMap<>();
    
    private  static HashMap<String,boton> paginas_favoritas = new HashMap<>();
    
    static JPanel ver_historial;
    
    
    public static void set_pagina_actual(ScrollPane pagina){
        actual=pagina;
    }
    
    public static ScrollPane get_pagina_actual(){
        return actual;
    }
    
    public static void agregar_hipervinculo(String ruta){
        ScrollPane nuevo = new ScrollPane();
        nuevo.analizar(ruta);
        Html.hipervinculo(nuevo);
    }
    
    public static void agregar_a_historial(String ruta){
        if(historial.containsKey(ruta))
            return;
        historial.put(ruta, ruta);
    }
    
    public static  LinkedList<String> retornar_historial(){
        LinkedList<String>nuevo = new LinkedList<>();
        historial.forEach((k,v) -> nuevo.add(k));
        return nuevo;
    }
    
    public static void mostrar_historial(){
        ver_historial  = new JPanel(new GridLayout(historial.size(),1));
        historial.forEach((k,v) -> ver_historial.add(new enlace(k,k)));
        //return ver_historial;
        Html.ver_historial(ver_historial);
    }
    
    public static void agregar_pagina_favorita(String ruta){
        if(paginas_favoritas.containsKey(ruta))
            return;
        int cont = paginas_favoritas.size();
        boton nuevo = new boton("Pagina "+cont);
        
        nuevo.setTipo(1);
        nuevo.valor=ruta;
        paginas_favoritas.put(ruta, nuevo);
        
    }
    
    public static LinkedList<boton> get_paginas(){
        LinkedList<boton> lista = new LinkedList<>();
        int a=0;
        paginas_favoritas.forEach((k,v)-> lista.add(v));
        return lista;
    }
    
    public static modeloTablaErrores getTablaDeErrores(){
        modeloTablaErrores nuevo = new modeloTablaErrores(actual.errores);
        return nuevo;
    }
}
