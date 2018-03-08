/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginas;

import interpretes.metodo;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JSpinner;

/**
 *
 * @author Jherson
 */
public class Spinner extends JSpinner{

    public estilos estilo;
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    public Spinner(int val) {
        this.estilo= new estilos();
        this.setValue(val);
        this.setPreferredSize(new Dimension(100,30));
        setearMouseClicked();
        
    }

    public Spinner() {
        this.estilo= new estilos();
        this.setPreferredSize(new Dimension(100,30));
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
