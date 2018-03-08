package paginas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class TablaSazo  extends JScrollPane{

    private HashMap<String,String>funciones = new HashMap<>();
    public estilos estilo;
    
    
    public TablaSazo(LinkedList<LinkedList<JComponent>> elementos) {
        this.setPreferredSize(new Dimension(400, 400));
        //this.setLayout(new GridLayout(elementos.size(), 1));
        JPanel fondo = new JPanel(new GridLayout(elementos.size(), 1));
        fondo.setPreferredSize(new Dimension(400, 400));
        
        for (LinkedList<JComponent> lista : elementos) {
            if (lista.size() != 0) {
                JPanel nuevo = new JPanel();
               // nuevo.setBackground(Color.green);
                nuevo.setLayout(new GridLayout(1, lista.size()));
                for (JComponent x : lista) {
                    x.setBorder(new javax.swing.border.LineBorder(Color.black, 1, false));

                    //this.add(x);
                    nuevo.add(x);
                }
                //this.add(nuevo);
                fondo.add(nuevo);
            }
       }
       
       
       this.setBackground(new Color(0,0,0,0));
       this.setVisible(true);
       this.setViewportView(fondo);
       
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
