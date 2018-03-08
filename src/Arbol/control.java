/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import interpretes.error;
import interpretes.interprete_html;
import interpretes.interprete_js;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import paginas.ScrollPane;

/**
 *
 * @author Jherson
 */
public class control {
    private static nodo raiz;
    private static interprete_html analizador_html;
    private static LinkedList<error> errores;
    
    static ScrollPane html_actual;
    public static String consola="";
    public static void setRaiz(nodo root){
        raiz= root;
    }
    
    public static nodo getRaiz(){
        return raiz;
    }
//    
    public static interprete_html getHtml(){
       
        return  analizador_html;
    }
    
    public static void setearInterpreteHtml(interprete_html html){
        analizador_html=html;
    }
    
    
    public  static LinkedList<error> getLista(){
        if(errores==null)
            errores = new LinkedList<>();
        return errores;
    }
    
    public static void setear_actual(ScrollPane ventana){
        html_actual = ventana;
    }
    
    
    public static void agregarError(error nuevo){
        html_actual.errores.add(nuevo);
    }
    
    
    
    
//    public static void analizar_html(String ruta){
//        
//        String texto = retornarContenidoRuta(ruta);
//        if("".equals(texto)){
//            JOptionPane.showMessageDialog(null, "Error al abrir la ruta: "+ruta);
//            return;
//        }
//        
//        Reader reader = new StringReader(texto);
//        Lexico_html analizador_lexico =  new Lexico_html(reader);
//        Sintactico_html analizador_sintactico = new Sintactico_html(analizador_lexico);
//        
//        try 
//        {            
//          analizador_sintactico.parse();
//          control.getHtml().inicio(control.getRaiz());
////            Graficador a = new Graficador();
////            a.graficar(control.getRaiz());
////            String arbol = a.getGraph();
////            System.out.println(arbol);
//        } 
//        catch (Exception ex) 
//        {
//            System.out.println("ERROR a la hora de analizar el archivo: "+ex.getMessage());
//            System.out.println("! ============================================================ Analisis Abortado");
//            JOptionPane.showMessageDialog(null, "Ocurrio un grave problema","Ejemplo",2);
//            return;
//        }        
//        
//        System.out.println("! ============================================================ Analisis Completado");
//        JOptionPane.showMessageDialog(null, "Analisis Completo","Proyecto 2",1);
//    }
//    
//    
    
    private static String retornarContenidoRuta(String ruta){
        String linea, contenido= "";
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(ruta));
            //leeendo linea a linea
            while ((linea = bufferreader.readLine())!=null)
            {
                contenido += linea +"\n";
            }
            System.out.println(contenido);
            bufferreader.close();
        } catch (IOException ex) {
            Logger.getLogger(control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contenido;
    }
    
    
    
    
}
