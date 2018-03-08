package interpretes;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import paginas.estilos;

/**
 *
 * @author Jherson
 */
public class PanelSazo extends JPanel {

    public estilos estilo;
    
    
    public PanelSazo() {
        this.estilo=new estilos();
        this.estilo.x=400;
        this.estilo.y=400;
        this.estilo.fondo_elemento= Color.yellow;
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(Color.yellow);
    
    }
    
    
  

}
