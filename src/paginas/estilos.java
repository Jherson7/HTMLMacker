/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginas;

import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author Jherson
 */
public class estilos {
    public String id;
    //formato
    public boolean negrita;
    public boolean cursiva;
    public boolean mayuscula;
    public boolean miniscula;
    public boolean capital;
    
    //alineadoa
    public boolean izquierda;
    public boolean derecha;
    public boolean centrado;
    public boolean justificado;
    
    //letra
    public String tipo_letra;
    public double tam_letra;
    public Color  color_texto;
    
    //tamanio
    public Dimension tam_elemento;
    
    public boolean opaco;
    
    // manejo del borde
    public double tam_borde;
    public Color color_borde;
    public boolean curva;
    
    public Color fondo_elemento;
    
    public double tam_texto;
    
    public String texto;
    
    public boolean utilizado;
    
    public boolean visiblidad;
    
    //para manejar el alto y ancho
    public int x;
    public int y;
    
    //para guadar la ruta, o metodo boton o enlace
    public String click;
            
 
    
    
    public estilos() {
         this.tam_letra = 12;
        this.tipo_letra = "Arial";
        this.visiblidad = true;
        this.texto = "";
    
    }
     public estilos(String nombre) {
        this.id=nombre;
        this.tam_letra=12;
        this.tipo_letra="Arial";
        this.visiblidad=true;
        this.texto=this.click="";
    }
    
    
    
    
    
}
