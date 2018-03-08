package paginas;

import interpretes.metodo;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JTextArea;

public class textArea extends JTextArea {

    public estilos estilo;
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    public textArea() {
        this.estilo= new estilos();
        this.setPreferredSize(new Dimension(100, 100));
        this.setVisible(true);
        this.setLineWrap(true);
        
        this.estilo.x=200;
        this.estilo.y=200;//quemo por defecto el alto y ancho
        setearMouseClicked();
    }
    
    public textArea(String texto){
        this.estilo= new estilos();
        this.setPreferredSize(new Dimension(100, 100));
        this.setText(texto);
        this.setVisible(true);
        this.setLineWrap(true);
        
        this.estilo.x=200;
        this.estilo.y=200;//quemo por defecto el alto y ancho
        
    
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
