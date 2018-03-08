package paginas;

import interpretes.metodo;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Jherson
 */
public class ComboBox  extends JComboBox<opcion>{

    public estilos estilo;
    
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    public ComboBox() {
        this.setPreferredSize(new Dimension(120, 30));
        this.setBackground(Color.green);
        setearMouseClicked();
        
    }
    
     public ComboBox(LinkedList<opcion> lista) {
         
        this.estilo=new estilos();
         
        this.setPreferredSize(new Dimension(120, 30));
        this.setBackground(Color.green);
        
        DefaultComboBoxModel<opcion> modelo = new DefaultComboBoxModel<>();
        
           
        for(opcion a:lista)
            modelo.addElement(a);
        
        this.setModel(modelo);
        
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
    
    ///atributos
    
    
}
