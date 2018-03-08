package paginas;

import interpretes.metodo;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JTextField;

/**
 *
 * @author Jherson
 */
public class textEdit  extends JTextField{

    public estilos estilo;
    
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    public textEdit(String texto) {
        this.setPreferredSize(new Dimension(200, 30));
        this.setText(texto);
        
        this.estilo= new estilos();
        this.estilo.x=200;
        this.estilo.y=30;
        
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
