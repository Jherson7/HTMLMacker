/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginas;

import Arbol.control_paginas;
import interpretes.metodo;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 *
 * @author Jherson
 */
public class enlace extends JLabel{

    public String ruta;
    public estilos estilo;
    private HashMap<String,metodo>funciones_click = new HashMap<>();
    private HashMap<String,metodo>funciones_property = new HashMap<>();
    private HashMap<String,metodo>funciones_listo = new HashMap<>();
    
    public enlace(String texto,String ruta) {//metodo para cargar las paginas al historial
        this.setFont(new Font("Arial", 1, 15));
        this.setText(texto);
        this.ruta=texto;
       
        setearMouseOptions(); 
        
        this.setBorder(new javax.swing.border.LineBorder(Color.red, 3, false));
        
    }
    
    
    public enlace(String texto){
        
        this.estilo= new estilos();
        this.setFont(new Font("Arial", 0, 15));
        this.setText(texto);
        
       
        this.ruta = "";
        setearMouseOptions();
        
    }
    
    private void setHover(){
        this.setForeground(Color.red);
    }
    
    private void setUnHover(){
        this.setForeground(Color.black);
    }
    
    
    public void setearMouseOptions(){
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(!ruta.equals("")){
                    control_paginas.agregar_hipervinculo(ruta);
                }
            }
        });
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setHover();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setUnHover();
            }
        });
        
         this.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                //metodo para proertyChange
            }
        });
    }
}
