/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadores;

/**
 *
 * @author Jherson
 */
public class generador_js {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         String opciones[] = new String[7]; 
        
        //Seleccionamos la opción de dirección de destino
        opciones[0] = "-destdir";
        
        //Le damos la dirección, Carpeta donde se va a generar el parser.java & el simbolosxxx.java
        opciones[1] = "src\\gramatica_js\\";
        
        //Seleccionamos la opción de nombre de archivo simbolos
        opciones[2] = "-symbols"; 
        
         //Le damos el nombre que queremos que tenga
        opciones[3] = "Simbolos_js";
        
        //Seleccionamos la opcion de clase parser
        opciones[4] = "-parser";         
        //Le damos nombre a esa clase
        opciones[5] = "Sintactico_js"; 
        
        //Le decimos donde se encuentra el archivo .cup 
        opciones[6] = "src\\gramatica_js\\Sintactico_js.cup"; 
        try 
        {
            java_cup.Main.main(opciones);
            
        } 
        catch (Exception ex){System.out.print(ex);}     
    }
    
}
