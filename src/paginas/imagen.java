/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginas;

import interpretes.metodo;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 *
 * @author Jherson
 */
public class imagen extends JLabel{

    public String valor;
    public estilos estilo;
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    
    public imagen(String ruta) {
        
        this.estilo= new estilos();
        
        ruta=ruta.trim();
        ruta=ruta.replace(("\""), "");
       //this.setPreferredSize(new Dimension(400, 400));
       //this.setIcon(new javax.swing.ImageIcon("C:\\Users\\Jherson\\Pictures\\Saved Pictures\\foto_formal.jpg"));
       this.setIcon(new javax.swing.ImageIcon(ruta));
       
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
