package paginas;

import Arbol.control;
import Arbol.control_paginas;
import interpretes.metodo;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class boton extends JButton{

    private int tipo;
    public String valor;
    public String ruta;
    public estilos estilo;
    
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    
    public boton(String texto) {
        this.setText(texto);
        this.estilo= new estilos();
        agregarEventosClick();
        
    }
    
    public void setTipo(int val){
        this.tipo=val;
    }
    
    
    public void agregarFucncion(String funcion){
        
    }

    
    
    public void agregarEventosClick(){ //evento property change
        
        this.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                JOptionPane.showMessageDialog(null, "cambio algo");
            }
        });
          
        this.addActionListener(new java.awt.event.ActionListener() {//evento click
            @Override
            public void actionPerformed(ActionEvent ae) {
                //hago la pregunta if 0 == funcion
                //1 hipervinculo
                if (tipo == 1) {
                    if (!valor.equals("")) {
                        control_paginas.agregar_hipervinculo(valor);
                    }
                }else
                    control.getHtml().ejecutarFuncion(valor);
                //Arbol.ejecutar("nombre");
            }
        });
    }
}
