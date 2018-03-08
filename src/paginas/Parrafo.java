/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginas;

import interpretes.metodo;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JTextArea;

/**
 *
 * @author Jherson
 */
public class Parrafo extends JTextArea{

    public estilos estilo;
   
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    public Parrafo() {
        this.estilo= new estilos();
        this.setBackground(new Color(0,0,0,0));
        setearMouseClicked();
    }
    
    public Parrafo(String texto){
        this.estilo= new estilos();
        this.setBorder(new javax.swing.border.LineBorder(new Color(0,0,0,0), 0, false));
        this.setEditable(false);
        this.setText(texto);
        this.setBackground(new Color(0,0,0,0));
        
        setearMouseClicked();
    }
    
    
     private void setearMouseClicked(){
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               
                //ejecutar metodos
                
                
            }
        });  
        
         this.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                //metodo para proertyChange
            }
        });
     }
}
